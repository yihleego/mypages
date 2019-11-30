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
import io.leego.mypages.util.BeanUtils;
import io.leego.mypages.util.NumberUtils;
import io.leego.mypages.util.PaginationCollectionFactory;
import io.leego.mypages.util.PaginationField;
import io.leego.mypages.util.PaginationParam;
import io.leego.mypages.util.ReflectUtils;
import io.leego.mypages.util.Search;
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
    private static final String COUNT_SUFFIX = "_COUNT";
    /** default page number */
    private int defaultPage = 1;
    /** default page size */
    private int defaultSize = 20;
    /** max page number */
    private int maxPage = 10000;
    /** max page size */
    private int maxSize = 10000;
    private String countColumn;
    private String pageFieldName;
    private String sizeFieldName;
    private String offsetFieldName;
    private String rowsFieldName;
    private String countColumnFieldName;
    private String enableCountFieldName;
    private boolean autoGetParamsFromFields;
    private boolean reasonable;
    private SqlDialect sqlDialect;
    private Dialect dialect;
    private ConcurrentMap<String, MappedStatement> countMsMap = new ConcurrentHashMap<>(64);
    private ConcurrentMap<String, PaginationField> paginationFieldMap = new ConcurrentHashMap<>();

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
            if (total == 0) {
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

    private long count(Executor executor, MappedStatement ms, BoundSql boundSql, Object parameter, ResultHandler resultHandler, String countColumn) throws Throwable {
        String countMsId = ms.getId() + COUNT_SUFFIX;
        MappedStatement countMs = countMsMap.computeIfAbsent(countMsId, id -> newCountMappedStatement(ms, id));
        CacheKey countKey = executor.createCacheKey(countMs, parameter, RowBounds.DEFAULT, boundSql);
        String originSql = boundSql.getSql();
        String countSql = dialect.getCountSql(originSql, countColumn);
        BoundSql countBoundSql = newBoundSql(ms, boundSql, countSql);
        Object result = executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
        return result != null ? (Long) ((List) result).get(0) : 0;
    }

    private <E> List<E> query(Executor executor, MappedStatement ms, BoundSql boundSql, Object parameter, ResultHandler resultHandler, CacheKey cacheKey, PaginationParam param) throws Throwable {
        parameter = dialect.processParams(ms, parameter, boundSql, cacheKey, param);
        String originSql = boundSql.getSql();
        String paginationSql = dialect.getPaginationSql(originSql, param.getOffset(), param.getRows());
        BoundSql paginationBoundSql = newBoundSql(ms, boundSql, paginationSql);
        return executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, paginationBoundSql);
    }

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

    private boolean isPaging(Object parameter, Class<?> returnType) {
        if (parameter == null || !Collection.class.isAssignableFrom(returnType)) {
            return false;
        }
        if (autoGetParamsFromFields || parameter instanceof Search) {
            return true;
        }
        Pagination pagination = parameter.getClass().getAnnotation(Pagination.class);
        DisablePagination disablePagination = parameter.getClass().getAnnotation(DisablePagination.class);
        return pagination != null && disablePagination == null;
    }

    private boolean isCounting(Object parameter) {
        DisableCount disableCount = parameter.getClass().getAnnotation(DisableCount.class);
        if (disableCount != null) {
            return false;
        }
        if (enableCountFieldName != null && !enableCountFieldName.isEmpty()) {
            Field field = ReflectUtils.getField(parameter, enableCountFieldName);
            Boolean enableCount = invokeBoolean(parameter, field);
            if (enableCount != null) {
                return enableCount;
            }
        }
        return true;
    }

    private PaginationParam analyseParameter(Object parameter) {
        PaginationField paginationField = paginationFieldMap.computeIfAbsent(
                parameter.getClass().getName(), className -> buildPaginationField(parameter));
        int defaultPage = paginationField.getDefaultPage();
        int defaultSize = paginationField.getDefaultSize();
        int maxPage = paginationField.getMaxPage();
        int maxSize = paginationField.getMaxSize();
        boolean reasonable = paginationField.isReasonable();
        String countColumn = paginationField.getCountColumn();
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
            if (search.getCountColumn() != null && !search.getCountColumn().isEmpty()) {
                countColumn = search.getCountColumn();
            }
        } else {
            page = invokeInteger(parameter, paginationField.getPageField());
            size = invokeInteger(parameter, paginationField.getSizeField());
            offset = invokeInteger(parameter, paginationField.getOffsetField());
            rows = invokeInteger(parameter, paginationField.getRowsField());
            String countColumnValue = invokeString(parameter, paginationField.getCountColumnField());
            if (countColumnValue != null && !countColumnValue.isEmpty()) {
                countColumn = countColumnValue;
            }
        }
        boolean usePageSize = page != null && size != null;
        boolean useOffsetRows = offset != null && rows != null;
        if (usePageSize) {
            if (reasonable) {
                if (page <= 0) {
                    page = defaultPage;
                }
                if (size <= 0) {
                    size = defaultSize;
                }
                if (page > maxPage) {
                    page = maxPage;
                }
                if (size > maxSize) {
                    size = maxSize;
                }
            }
            offset = (page - 1) * size;
            rows = size;
        } else if (useOffsetRows) {
            if (reasonable) {
                if (rows <= 0) {
                    rows = defaultSize;
                }
                if (offset <= 0) {
                    offset = (defaultPage - 1) * rows;
                }
                if (rows > maxSize) {
                    rows = maxSize;
                }
                if (offset / rows + 1 > maxPage) {
                    offset = (maxPage - 1) * rows;
                }
            }
            page = offset / rows + 1;
            size = rows;
        } else {
            return new PaginationParam(false);
        }
        return new PaginationParam(page, size, offset, rows, countColumn, true);
    }

    private PaginationField buildPaginationField(Object parameter) {
        int defaultPage;
        int defaultSize;
        int maxPage;
        int maxSize;
        boolean reasonable;
        String countColumn;
        Field pageField = null;
        Field sizeField = null;
        Field offsetField = null;
        Field rowsField = null;
        Field countColumnField = null;
        Pagination pagination = ReflectUtils.getAnnotation(parameter, Pagination.class);
        if (pagination == null) {
            defaultPage = this.defaultPage;
            defaultSize = this.defaultSize;
            maxPage = this.maxPage;
            maxSize = this.maxSize;
            reasonable = this.reasonable;
            countColumn = this.countColumn;
        } else {
            defaultPage = pagination.defaultPage() > 0 ? pagination.defaultPage() : this.defaultPage;
            defaultSize = pagination.defaultSize() > 0 ? pagination.defaultSize() : this.defaultSize;
            maxPage = pagination.maxPage() > 0 ? pagination.maxPage() : this.maxPage;
            maxSize = pagination.maxSize() > 0 ? pagination.maxSize() : this.maxSize;
            reasonable = pagination.reasonable() ? pagination.reasonable() : this.reasonable;
            countColumn = !pagination.countColumn().isEmpty() ? pagination.countColumn() : this.countColumn;
        }
        if (parameter instanceof Search) {
            return new PaginationField();
        }
        Field[] fields = ReflectUtils.getFields(parameter, true);
        for (Field field : fields) {
            if (autoGetParamsFromFields) {
                if (field.getName().equals(pageFieldName)) {
                    pageField = field;
                } else if (field.getName().equals(sizeFieldName)) {
                    sizeField = field;
                } else if (field.getName().equals(offsetFieldName)) {
                    offsetField = field;
                } else if (field.getName().equals(rowsFieldName)) {
                    rowsField = field;
                } else if (field.getName().equals(countColumnFieldName)) {
                    countColumnField = field;
                }
            } else {
                Annotation[] annotations = field.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Page) {
                        pageField = field;
                    } else if (annotation instanceof Size) {
                        sizeField = field;
                    } else if (annotation instanceof Offset) {
                        offsetField = field;
                    } else if (annotation instanceof Rows) {
                        rowsField = field;
                    } else if (annotation instanceof CountColumn) {
                        countColumnField = field;
                    }
                }
            }
        }
        return new PaginationField(
                defaultPage, defaultSize, maxPage, maxSize, reasonable, countColumn,
                pageField, sizeField, offsetField, rowsField, countColumnField);
    }

    private Integer invokeInteger(Object o, Field field) {
        Object value = invoke(o, field);
        if (value == null) {
            return null;
        }
        return NumberUtils.parseInteger(value);
    }

    private String invokeString(Object o, Field field) {
        Object value = invoke(o, field);
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    private Boolean invokeBoolean(Object o, Field field) {
        Object value = invoke(o, field);
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

    private Object invoke(Object o, Field field) {
        if (field == null) {
            return null;
        }
        try {
            return BeanUtils.read(o, field);
        } catch (Exception ignored) {
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

    public void setReasonable(boolean reasonable) {
        this.reasonable = reasonable;
    }

    public void setSqlDialect(SqlDialect sqlDialect) {
        this.sqlDialect = sqlDialect;
        this.dialect = sqlDialect.getDialect();
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

    public boolean isReasonable() {
        return reasonable;
    }

    public SqlDialect getSqlDialect() {
        return sqlDialect;
    }

}