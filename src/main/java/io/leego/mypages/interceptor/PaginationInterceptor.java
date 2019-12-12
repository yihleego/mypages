package io.leego.mypages.interceptor;

import io.leego.mypages.annotation.CountColumn;
import io.leego.mypages.annotation.DisableCount;
import io.leego.mypages.annotation.DisablePagination;
import io.leego.mypages.annotation.Offset;
import io.leego.mypages.annotation.Page;
import io.leego.mypages.annotation.Pagination;
import io.leego.mypages.annotation.Rows;
import io.leego.mypages.annotation.Size;
import io.leego.mypages.dialect.Dialect;
import io.leego.mypages.dialect.SqlDialect;
import io.leego.mypages.exception.PaginationException;
import io.leego.mypages.util.BeanUtils;
import io.leego.mypages.util.PaginationCollectionFactory;
import io.leego.mypages.util.PaginationParam;
import io.leego.mypages.util.PaginationUnrefinedParam;
import io.leego.mypages.util.ReflectUtils;
import io.leego.mypages.util.Search;
import io.leego.mypages.util.StringUtils;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Yihleego
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})})
public class PaginationInterceptor implements Interceptor {
    private static final String COUNT_SUFFIX = "#COUNT";
    /** Default page (optional) */
    private int defaultPage = 1;
    /** Default size (optional) */
    private int defaultSize = 10;
    /** Max page (optional) */
    private int maxPage = -1;
    /** Max size (optional) */
    private int maxSize = -1;
    /** Count column (optional) */
    private String countColumn;
    /** Parameter page field name (optional) */
    private String pageFieldName;
    /** Parameter size field name (optional) */
    private String sizeFieldName;
    /** Parameter offset field name (optional) */
    private String offsetFieldName;
    /** Parameter rows field name (optional) */
    private String rowsFieldName;
    /** Parameter count column field name (optional) */
    private String countColumnFieldName;
    /** Parameter enable count field name (optional) */
    private String enableCountFieldName;
    /** Whether to reflect <code>page, size, offset, rows</code> from parameter (optional) */
    private boolean autoGetParamsFromFields = false;
    /** Whether to skip query if total value equals zero (optional) */
    private boolean skipQueryIfCountEqualsZero = true;
    /** Whether to enable reasonable (optional) */
    private boolean reasonable = false;
    /** {@link SqlDialect} (optional) */
    private SqlDialect sqlDialect;
    /** The SQL dialect {@link Dialect} (required) */
    private Dialect dialect;
    private ConcurrentMap<String, MappedStatement> countMsMap = new ConcurrentHashMap<>(64);
    private ConcurrentMap<String, PaginationUnrefinedParam> unrefinedParamMap = new ConcurrentHashMap<>(64);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        Object parameter = args[1];
        Class<?> returnType = invocation.getMethod().getReturnType();
        if (!isPaging(parameter, returnType)) {
            return invocation.proceed();
        }
        PaginationParam param = analyseParameter(parameter);
        if (!param.isPageable()) {
            return invocation.proceed();
        }
        if (dialect == null) {
            throw new PaginationException("SQL dialect is not configured");
        }
        Executor executor = (Executor) invocation.getTarget();
        MappedStatement ms = (MappedStatement) args[0];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];
        BoundSql boundSql;
        CacheKey cacheKey;
        if (args.length == 4) {
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            boundSql = (BoundSql) args[5];
            cacheKey = (CacheKey) args[4];
        }
        Long total = null;
        if (isCounting(parameter)) {
            total = count(executor, ms, boundSql, parameter, resultHandler, param.getCountColumn());
            if (total == 0 && skipQueryIfCountEqualsZero) {
                return PaginationCollectionFactory.build(returnType, total, param.getPage(), param.getSize());
            }
        }
        List<?> result = query(executor, ms, boundSql, parameter, resultHandler, cacheKey, param);
        return PaginationCollectionFactory.build(returnType, result, total, param.getPage(), param.getSize());
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    /**
     * Returns count quantity
     */
    private long count(Executor executor, MappedStatement ms, BoundSql boundSql, Object parameter, ResultHandler resultHandler, String countColumn) throws SQLException {
        String countMsId = ms.getId() + COUNT_SUFFIX;
        MappedStatement countMs = countMsMap.computeIfAbsent(countMsId, id -> newCountMappedStatement(ms, id));
        CacheKey countKey = executor.createCacheKey(countMs, parameter, RowBounds.DEFAULT, boundSql);
        String originSql = boundSql.getSql();
        String countSql = dialect.getCountSql(originSql, countColumn);
        BoundSql countBoundSql = newBoundSql(ms, boundSql, countSql);
        Object result = executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
        return result != null ? parseLong(((List) result).get(0)) : 0;
    }

    /**
     * Returns paging query results
     */
    private <E> List<E> query(Executor executor, MappedStatement ms, BoundSql boundSql, Object parameter, ResultHandler resultHandler, CacheKey cacheKey, PaginationParam param) throws SQLException {
        parameter = dialect.processParams(ms, parameter, boundSql, cacheKey, param);
        String originSql = boundSql.getSql();
        String paginationSql = dialect.getPaginationSql(originSql, param.getOffset(), param.getRows());
        BoundSql paginationBoundSql = newBoundSql(ms, boundSql, paginationSql);
        return executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, paginationBoundSql);
    }

    /**
     * Returns a new instanced object of {@link MappedStatement}
     * @param ms   The origin {@link MappedStatement}
     * @param msId New id
     * @return a new instanced object of {@link MappedStatement}
     */
    private MappedStatement newCountMappedStatement(MappedStatement ms, String msId) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), msId, ms.getSqlSource(), ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        List<ResultMap> resultMaps = new ArrayList<>();
        ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), Long.class, Collections.emptyList()).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        return builder.build();
    }

    /**
     * Returns a new instanced object of {@link BoundSql}
     * @param ms       The origin {@link MappedStatement}
     * @param boundSql The origin {@link BoundSql}
     * @param sql      SQL string
     * @return a new instanced object of {@link BoundSql}
     */
    private BoundSql newBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    /**
     * Returns <code>true</code> when following:<p>
     * 1.<code>returnType</code> is assignable from {@link Collection}.<p>
     * 2.Annotation {@link DisablePagination} is absent.<p>
     * 3.{@link #autoGetParamsFromFields} equals <code>true</code> or
     * <code>parameter</code> extends {@link Search},
     * Annotation {@link Pagination} is present.
     * @param parameter  The parameter
     * @param returnType Return type
     */
    private boolean isPaging(Object parameter, Class<?> returnType) {
        if (parameter == null
                || !Collection.class.isAssignableFrom(returnType)
                || parameter.getClass().getAnnotation(DisablePagination.class) != null) {
            return false;
        }
        return autoGetParamsFromFields
                || parameter instanceof Search
                || parameter.getClass().getAnnotation(Pagination.class) != null;
    }

    /**
     * Returns <code>true</code> when {@link DisableCount} is absent
     * and enable count equals <code>true</code>
     * @param parameter The parameter
     */
    private boolean isCounting(Object parameter) {
        if (parameter.getClass().getAnnotation(DisableCount.class) != null) {
            return false;
        }
        if (StringUtils.isEmpty(enableCountFieldName)) {
            return true;
        }
        try {
            Boolean value = parseBoolean(BeanUtils.read(parameter, enableCountFieldName));
            return value != null ? value : false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns {@link PaginationParam} from parameter.
     * @param parameter The parameter
     * @return {@link PaginationParam}
     */
    private PaginationParam analyseParameter(Object parameter) {
        PaginationUnrefinedParam unrefinedParam = unrefinedParamMap.computeIfAbsent(
                parameter.getClass().getName(), className -> buildPaginationUnrefinedParam(parameter));
        int defaultPage = unrefinedParam.getDefaultPage();
        int defaultSize = unrefinedParam.getDefaultSize();
        int maxPage = unrefinedParam.getMaxPage();
        int maxSize = unrefinedParam.getMaxSize();
        boolean reasonable = unrefinedParam.isReasonable();
        String countColumn;
        Integer page;
        Integer size;
        Integer offset;
        Integer rows;
        if (parameter instanceof Search) {
            Search search = (Search) parameter;
            page = search.getPage();
            size = search.getSize();
            offset = search.getOffset();
            rows = search.getRows();
            countColumn = search.getCountColumn();
        } else if (unrefinedParam.isMapType()) {
            Map parameterMap = (Map) parameter;
            page = parseInteger(getMapValue(parameterMap, this.pageFieldName));
            size = parseInteger(getMapValue(parameterMap, this.sizeFieldName));
            offset = parseInteger(getMapValue(parameterMap, this.offsetFieldName));
            rows = parseInteger(getMapValue(parameterMap, this.rowsFieldName));
            countColumn = parseString(getMapValue(parameterMap, this.countColumnFieldName));
        } else {
            page = invokeInteger(parameter, unrefinedParam.getPageReadMethod());
            size = invokeInteger(parameter, unrefinedParam.getSizeReadMethod());
            offset = invokeInteger(parameter, unrefinedParam.getOffsetReadMethod());
            rows = invokeInteger(parameter, unrefinedParam.getRowsReadMethod());
            countColumn = invokeString(parameter, unrefinedParam.getCountColumnReadMethod());
        }
        if (StringUtils.isEmpty(countColumn)
                && StringUtils.isNotEmpty(unrefinedParam.getCountColumn())) {
            countColumn = unrefinedParam.getCountColumn();
        }
        boolean usePageSize = page != null && size != null;
        boolean useOffsetRows = offset != null && rows != null;
        if (usePageSize) {
            if (reasonable) {
                if (page <= 0 && defaultPage > 0) {
                    page = defaultPage;
                }
                if (size <= 0 && defaultSize > 0) {
                    size = defaultSize;
                }
                if (page > maxPage && maxPage > 0) {
                    page = maxPage;
                }
                if (size > maxSize && maxSize > 0) {
                    size = maxSize;
                }
            }
            offset = (page - 1) * size;
            rows = size;
        } else if (useOffsetRows) {
            if (reasonable) {
                if (rows <= 0 && defaultSize > 0) {
                    rows = defaultSize;
                }
                if (offset <= 0 && defaultPage > 0) {
                    offset = (defaultPage - 1) * rows;
                }
                if (rows > maxSize && maxSize > 0) {
                    rows = maxSize;
                }
                if (offset / rows + 1 > maxPage && maxPage > 0) {
                    offset = (maxPage - 1) * rows;
                }
            }
            // Converts [offset, rows] to [page, size] if rows can be divided by offset
            if (rows > 0 && offset % rows == 0) {
                page = offset / rows + 1;
                size = rows;
            } else {
                page = null;
                size = null;
            }
        } else {
            return new PaginationParam(false);
        }
        return new PaginationParam(page, size, offset, rows, countColumn, true);
    }

    /**
     * Returns {@link PaginationUnrefinedParam} from parameter.
     * @param parameter The parameter
     * @return {@link PaginationUnrefinedParam}
     */
    private PaginationUnrefinedParam buildPaginationUnrefinedParam(Object parameter) {
        boolean reasonable;
        int defaultPage;
        int defaultSize;
        int maxPage;
        int maxSize;
        String countColumn;
        Method pageReadMethod = null;
        Method sizeReadMethod = null;
        Method offsetReadMethod = null;
        Method rowsReadMethod = null;
        Method countColumnReadMethod = null;
        boolean mapType;
        Pagination pagination = ReflectUtils.getAnnotation(parameter, Pagination.class);
        if (pagination != null) {
            reasonable = pagination.reasonable() ? pagination.reasonable() : this.reasonable;
            defaultPage = pagination.defaultPage() > 0 ? pagination.defaultPage() : this.defaultPage;
            defaultSize = pagination.defaultSize() > 0 ? pagination.defaultSize() : this.defaultSize;
            maxPage = pagination.maxPage() > 0 ? pagination.maxPage() : this.maxPage;
            maxSize = pagination.maxSize() > 0 ? pagination.maxSize() : this.maxSize;
            countColumn = StringUtils.isNotEmpty(pagination.countColumn()) ? pagination.countColumn() : this.countColumn;
        } else {
            reasonable = this.reasonable;
            defaultPage = this.defaultPage;
            defaultSize = this.defaultSize;
            maxPage = this.maxPage;
            maxSize = this.maxSize;
            countColumn = this.countColumn;
        }
        if (parameter instanceof Search) {
            return new PaginationUnrefinedParam(
                    reasonable,
                    defaultPage,
                    defaultSize,
                    maxPage,
                    maxSize,
                    countColumn);
        }
        if (this.autoGetParamsFromFields) {
            PropertyDescriptor[] pds;
            try {
                pds = BeanUtils.getPropertyDescriptors(parameter.getClass());
            } catch (IntrospectionException ignored) {
                pds = null;
            }
            if (pds != null && pds.length > 0) {
                for (PropertyDescriptor pd : pds) {
                    if (Objects.equals(this.pageFieldName, pd.getName())) {
                        pageReadMethod = pd.getReadMethod();
                    } else if (Objects.equals(this.sizeFieldName, pd.getName())) {
                        sizeReadMethod = pd.getReadMethod();
                    } else if (Objects.equals(this.offsetFieldName, pd.getName())) {
                        offsetReadMethod = pd.getReadMethod();
                    } else if (Objects.equals(this.rowsFieldName, pd.getName())) {
                        rowsReadMethod = pd.getReadMethod();
                    } else if (Objects.equals(this.countColumnFieldName, pd.getName())) {
                        countColumnReadMethod = pd.getReadMethod();
                    }
                }
            }
        } else {
            // Gets read method by field annotations.
            Field[] fields = ReflectUtils.getFields(parameter);
            for (Field field : fields) {
                if (ReflectUtils.isStatic(field)) {
                    continue;
                }
                Annotation[] annotations = field.getDeclaredAnnotations();
                if (annotations.length == 0) {
                    continue;
                }
                for (Annotation annotation : annotations) {
                    try {
                        if (annotation instanceof Page) {
                            pageReadMethod = BeanUtils.getReadMethod(field);
                        } else if (annotation instanceof Size) {
                            sizeReadMethod = BeanUtils.getReadMethod(field);
                        } else if (annotation instanceof Offset) {
                            offsetReadMethod = BeanUtils.getReadMethod(field);
                        } else if (annotation instanceof Rows) {
                            rowsReadMethod = BeanUtils.getReadMethod(field);
                        } else if (annotation instanceof CountColumn) {
                            countColumnReadMethod = BeanUtils.getReadMethod(field);
                        }
                    } catch (IntrospectionException ignored) {
                    }
                }
            }
            // Gets read method by method annotations.
            Method[] methods = ReflectUtils.getMethods(parameter);
            for (Method method : methods) {
                if (ReflectUtils.isStatic(method)) {
                    continue;
                }
                Annotation[] annotations = method.getDeclaredAnnotations();
                if (annotations.length == 0) {
                    continue;
                }
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Page) {
                        pageReadMethod = method;
                    } else if (annotation instanceof Size) {
                        sizeReadMethod = method;
                    } else if (annotation instanceof Offset) {
                        offsetReadMethod = method;
                    } else if (annotation instanceof Rows) {
                        rowsReadMethod = method;
                    } else if (annotation instanceof CountColumn) {
                        countColumnReadMethod = method;
                    }
                }
            }
        }
        // There may be a map object or an object that implements Map
        mapType = parameter instanceof Map
                && this.autoGetParamsFromFields
                && (pageReadMethod == null || sizeReadMethod == null)
                && (offsetReadMethod == null || rowsReadMethod == null);
        return new PaginationUnrefinedParam(
                reasonable,
                defaultPage,
                defaultSize,
                maxPage,
                maxSize,
                countColumn,
                pageReadMethod,
                sizeReadMethod,
                offsetReadMethod,
                rowsReadMethod,
                countColumnReadMethod,
                mapType);
    }

    /**
     * Returns {@link Integer} value by invoking method.
     * And try to convert to {@link Integer} value
     * if it is a {@link String} value.
     * @param o      target
     * @param method {@link Method}
     * @return {@link Integer} value
     */
    private Integer invokeInteger(Object o, Method method) {
        Object value = invoke(o, method);
        return parseInteger(value);
    }

    /**
     * Returns {@link String} value by invoking method.
     * @param o      target
     * @param method {@link Method}
     * @return {@link String} value
     */
    private String invokeString(Object o, Method method) {
        Object value = invoke(o, method);
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    /**
     * Invokes method.
     * @param o      target
     * @param method {@link Method}
     * @return {@link Boolean} value
     */
    private Object invoke(Object o, Method method) {
        if (o == null || method == null) {
            return null;
        }
        try {
            return BeanUtils.read(o, method);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     * @param map It may be an object of {@link org.apache.ibatis.binding.MapperMethod.ParamMap}
     * @param key The key whose associated value is to be returned
     * @return The value to which the specified key is mapped
     */
    private Object getMapValue(Map map, Object key) {
        try {
            // The map may be an object of {@link MapperMethod.ParamMap}
            return map.get(key);
        } catch (BindingException ignored) {
            return null;
        }
    }

    /**
     * Parses the object argument as a string.
     */
    private String parseString(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        return String.valueOf(value);
    }

    /**
     * Parses the object argument as a boolean.
     */
    private Boolean parseBoolean(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (boolean) value;
        } else if (value instanceof Number) {
            // Returns false if the value equals 0.
            return ((Number) value).intValue() != 0;
        } else if (value instanceof String) {
            // Returns true if the text equals "true" (ignore case).
            return Boolean.parseBoolean((String) value);
        }
        return null;
    }

    /**
     * Parses the object argument as a signed decimal integer.
     */
    private Integer parseInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Parses the object argument as a signed decimal long.
     */
    private Long parseLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }


    public PaginationInterceptor defaultPage(int defaultPage) {
        this.defaultPage = defaultPage;
        return this;
    }

    public PaginationInterceptor defaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
        return this;
    }

    public PaginationInterceptor maxPage(int maxPage) {
        this.maxPage = maxPage;
        return this;
    }

    public PaginationInterceptor maxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    public PaginationInterceptor countColumn(String countColumn) {
        this.countColumn = countColumn;
        return this;
    }

    public PaginationInterceptor pagingFields(String pageFieldName, String sizeFieldName) {
        Objects.requireNonNull(pageFieldName);
        Objects.requireNonNull(sizeFieldName);
        this.pageFieldName = pageFieldName;
        this.sizeFieldName = sizeFieldName;
        this.autoGetParamsFromFields = true;
        return this;
    }

    public PaginationInterceptor offsetRowsFields(String offsetFieldName, String rowsFieldName) {
        Objects.requireNonNull(offsetFieldName);
        Objects.requireNonNull(rowsFieldName);
        this.offsetFieldName = offsetFieldName;
        this.rowsFieldName = rowsFieldName;
        this.autoGetParamsFromFields = true;
        return this;
    }

    public PaginationInterceptor countColumnField(String countColumnFieldName) {
        this.countColumnFieldName = countColumnFieldName;
        return this;
    }

    public PaginationInterceptor enableCountField(String enableCountFieldName) {
        this.enableCountFieldName = enableCountFieldName;
        return this;
    }

    public PaginationInterceptor skipQueryIfCountEqualsZero(boolean skipQueryIfCountEqualsZero) {
        this.skipQueryIfCountEqualsZero = skipQueryIfCountEqualsZero;
        return this;
    }

    public PaginationInterceptor reasonable(boolean reasonable) {
        this.reasonable = reasonable;
        return this;
    }

    public PaginationInterceptor sqlDialect(SqlDialect sqlDialect) {
        Objects.requireNonNull(sqlDialect);
        this.sqlDialect = sqlDialect;
        this.dialect = sqlDialect.getDialect();
        return this;
    }

    public PaginationInterceptor dialect(Dialect dialect) {
        Objects.requireNonNull(dialect);
        this.dialect = dialect;
        return this;
    }


    public void setDefaultPage(int defaultPage) {
        this.defaultPage = defaultPage;
    }

    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void setCountColumn(String countColumn) {
        this.countColumn = countColumn;
    }

    public void setPageFieldName(String pageFieldName) {
        this.pageFieldName = pageFieldName;
    }

    public void setSizeFieldName(String sizeFieldName) {
        this.sizeFieldName = sizeFieldName;
    }

    public void setOffsetFieldName(String offsetFieldName) {
        this.offsetFieldName = offsetFieldName;
    }

    public void setRowsFieldName(String rowsFieldName) {
        this.rowsFieldName = rowsFieldName;
    }

    public void setCountColumnFieldName(String countColumnFieldName) {
        this.countColumnFieldName = countColumnFieldName;
    }

    public void setEnableCountFieldName(String enableCountFieldName) {
        this.enableCountFieldName = enableCountFieldName;
    }

    public void setSkipQueryIfCountEqualsZero(boolean skipQueryIfCountEqualsZero) {
        this.skipQueryIfCountEqualsZero = skipQueryIfCountEqualsZero;
    }

    public void setReasonable(boolean reasonable) {
        this.reasonable = reasonable;
    }

    public void setSqlDialect(SqlDialect sqlDialect) {
        this.sqlDialect = sqlDialect;
        this.dialect = sqlDialect.getDialect();
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }


    public int getDefaultPage() {
        return defaultPage;
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public String getCountColumn() {
        return countColumn;
    }

    public String getPageFieldName() {
        return pageFieldName;
    }

    public String getSizeFieldName() {
        return sizeFieldName;
    }

    public String getOffsetFieldName() {
        return offsetFieldName;
    }

    public String getRowsFieldName() {
        return rowsFieldName;
    }

    public String getCountColumnFieldName() {
        return countColumnFieldName;
    }

    public String getEnableCountFieldName() {
        return enableCountFieldName;
    }

    public boolean isSkipQueryIfCountEqualsZero() {
        return skipQueryIfCountEqualsZero;
    }

    public boolean isReasonable() {
        return reasonable;
    }

    public SqlDialect getSqlDialect() {
        return sqlDialect;
    }

}