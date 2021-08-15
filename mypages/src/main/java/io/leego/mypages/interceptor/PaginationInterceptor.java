package io.leego.mypages.interceptor;

import io.leego.mypages.annotation.CountExpr;
import io.leego.mypages.annotation.CountMethodName;
import io.leego.mypages.annotation.DisableCount;
import io.leego.mypages.annotation.DisablePagination;
import io.leego.mypages.annotation.Offset;
import io.leego.mypages.annotation.Page;
import io.leego.mypages.annotation.Pagination;
import io.leego.mypages.annotation.Rows;
import io.leego.mypages.annotation.Size;
import io.leego.mypages.dialect.Database;
import io.leego.mypages.dialect.Dialect;
import io.leego.mypages.exception.PaginationException;
import io.leego.mypages.util.Pageable;
import io.leego.mypages.util.PaginationCollectionFactory;
import io.leego.mypages.util.PaginationDefinition;
import io.leego.mypages.util.PaginationParameter;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    private static final ConcurrentMap<String, MappedStatement> countMsCacheMap = new ConcurrentHashMap<>(64);
    private static final ConcurrentMap<String, PaginationDefinition> definitionCacheMap = new ConcurrentHashMap<>(64);
    private final Dialect dialect;
    private final PaginationSettings settings;

    public PaginationInterceptor(Database database) {
        if (database == null) {
            throw new IllegalArgumentException("Please specify a database type.");
        }
        this.dialect = database.newDialect();
        this.settings = PaginationSettings.empty();
    }

    public PaginationInterceptor(PaginationSettings settings) {
        if (settings.getDatabase() == null) {
            throw new IllegalArgumentException("Please specify a database type.");
        }
        this.dialect = settings.getDatabase().newDialect();
        this.settings = settings;
    }

    public PaginationInterceptor(Dialect dialect) {
        if (dialect == null) {
            throw new IllegalArgumentException("Please specify a sql dialect.");
        }
        this.dialect = dialect;
        this.settings = PaginationSettings.empty();
    }

    public PaginationInterceptor(Dialect dialect, PaginationSettings settings) {
        if (dialect == null) {
            throw new IllegalArgumentException("Please specify a sql dialect.");
        }
        this.dialect = dialect;
        this.settings = settings;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        Method method = invocation.getMethod();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        Class<?> returnType = method.getReturnType();
        if (!isPageable(parameter, returnType)) {
            return invocation.proceed();
        }
        PaginationParameter paginationParameter = analyseParameter(parameter, ms.getId());
        if (paginationParameter == null) {
            return invocation.proceed();
        }
        Executor executor = (Executor) invocation.getTarget();
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler<?> resultHandler = (ResultHandler<?>) args[3];
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
        if (isCountable(parameter)) {
            total = count(executor, ms, boundSql, parameter, resultHandler, paginationParameter.getCountExpr(), paginationParameter.getCountMethodName());
            if (total == 0 && settings.isSkipQueryIfCountEqualsZero()) {
                return PaginationCollectionFactory.build(returnType, total, paginationParameter.getPage(), paginationParameter.getSize());
            }
        }
        List<?> result = query(executor, ms, boundSql, parameter, rowBounds, resultHandler, cacheKey, paginationParameter);
        return PaginationCollectionFactory.build(returnType, result, total, paginationParameter.getPage(), paginationParameter.getSize());
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    /**
     * Returns count quantity.
     */
    private Long count(Executor executor, MappedStatement ms, BoundSql boundSql, Object parameter, ResultHandler<?> resultHandler, String countExpr, String countMsId) throws SQLException {
        Object value;
        if (isEmpty(countMsId)) {
            value = countByGenerated(executor, ms, boundSql, parameter, resultHandler, countExpr);
        } else {
            value = countBySpecified(executor, ms, boundSql, parameter, resultHandler, countExpr, countMsId);
        }
        return value != null ? parseLong(((List<?>) value).get(0)) : 0L;
    }

    /**
     * Returns count quantity by the generated SQL.
     */
    private Object countByGenerated(Executor executor, MappedStatement ms, BoundSql boundSql, Object parameter, ResultHandler<?> resultHandler, String countExpr) throws SQLException {
        MappedStatement countMs = countMsCacheMap.computeIfAbsent(buildCountMsKey(ms), id -> createCountMappedStatement(ms, id));
        CacheKey cacheKey = executor.createCacheKey(countMs, parameter, RowBounds.DEFAULT, boundSql);
        String originSql = boundSql.getSql();
        String countSql = settings.isUseGracefulCountSql()
                ? dialect.getCountSql(originSql, countExpr, settings.isKeepSorting())
                : dialect.getSimpleCountSql(originSql, countExpr);
        BoundSql countBoundSql = createBoundSql(ms, boundSql, countSql);
        return executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, countBoundSql);
    }

    /**
     * Returns count quantity by the specified SQL.
     */
    private Object countBySpecified(Executor executor, MappedStatement ms, BoundSql boundSql, Object parameter, ResultHandler<?> resultHandler, String countExpr, String countMsId) throws SQLException {
        MappedStatement countMs = null;
        Throwable cause = null;
        try {
            countMs = ms.getConfiguration().getMappedStatement(countMsId, false);
        } catch (Throwable t) {
            cause = t;
        }
        if (countMs != null) {
            CacheKey cacheKey = executor.createCacheKey(countMs, parameter, RowBounds.DEFAULT, ms.getBoundSql(parameter));
            BoundSql countBoundSql = countMs.getBoundSql(parameter);
            return executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, countBoundSql);
        }
        if (settings.isUseGeneratedIfCountMethodIsMissing()) {
            return countByGenerated(executor, ms, boundSql, parameter, resultHandler, countExpr);
        }
        throw new PaginationException("Counting method named \"" + countMsId + "\" is missing.", cause);
    }

    /**
     * Returns paging query results.
     */
    private <E> List<E> query(Executor executor, MappedStatement ms, BoundSql boundSql, Object parameter, RowBounds rowBounds, ResultHandler<?> resultHandler, CacheKey cacheKey, PaginationParameter paginationParameter) throws Exception {
        Object assembledParameter = dialect.handleParameter(ms, parameter, boundSql, cacheKey, paginationParameter);
        String originSql = boundSql.getSql();
        String paginationSql = dialect.getPageSql(originSql, paginationParameter);
        BoundSql paginationBoundSql = createBoundSql(ms, boundSql, paginationSql);
        return executor.query(ms, assembledParameter, rowBounds, resultHandler, cacheKey, paginationBoundSql);
    }

    /**
     * Returns a new instanced object of {@link MappedStatement}.
     * @param ms   The origin {@link MappedStatement}
     * @param msId New id
     * @return a new instanced object of {@link MappedStatement}
     */
    private MappedStatement createCountMappedStatement(MappedStatement ms, String msId) {
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
     * Returns a new instanced object of {@link BoundSql}.
     * @param ms       The origin {@link MappedStatement}
     * @param boundSql The origin {@link BoundSql}
     * @param sql      SQL string
     * @return a new instanced object of {@link BoundSql}
     */
    private BoundSql createBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
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
     * Returns <code>true</code> if pagination has been enabled.
     * @param parameter  The parameter
     * @param returnType Return type
     */
    private boolean isPageable(Object parameter, Class<?> returnType) {
        return parameter != null
                && Collection.class.isAssignableFrom(returnType)
                && parameter.getClass().getAnnotation(DisablePagination.class) == null;
    }

    /**
     * Returns <code>true</code> when {@link DisableCount} is absent
     * and enable count equals <code>true</code>.
     * @param parameter The parameter
     */
    private boolean isCountable(Object parameter) {
        if (parameter.getClass().getAnnotation(DisableCount.class) != null) {
            return false;
        }
        if (!isEmpty(settings.getEnableCountField())) {
            try {
                Field field = getField(parameter.getClass(), settings.getEnableCountField());
                Boolean b = parseBoolean(getFieldValue(parameter, field));
                return b != null && b;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns {@link PaginationParameter} from parameter.
     * @param parameter The parameter
     * @return {@link PaginationParameter}
     */
    private PaginationParameter analyseParameter(Object parameter, String msId) {
        PaginationDefinition definition = definitionCacheMap.computeIfAbsent(
                buildDefinitionParamKey(parameter, msId),
                ignored -> buildPaginationDefinition(parameter));
        if (!definition.isValid()) {
            return null;
        }
        return isEmpty(definition.getNestedMapKey())
                ? buildPaginationParameter(parameter, definition)
                : buildPaginationParameter(((Map<?, ?>) parameter).get(definition.getNestedMapKey()), definition);
    }

    /**
     * Builds {@link PaginationParameter} from parameter.
     * @param parameter  The parameter
     * @param definition {@link PaginationDefinition}
     * @return {@link PaginationParameter}
     */
    private PaginationParameter buildPaginationParameter(Object parameter, PaginationDefinition definition) {
        int defaultPage = definition.getDefaultPage();
        int defaultSize = definition.getDefaultSize();
        int maxPage = definition.getMaxPage();
        int maxSize = definition.getMaxSize();
        String countExpr;
        String countMethodName;
        Integer page;
        Integer size;
        Long offset;
        Integer rows;
        boolean usePageSize;
        boolean useOffsetRows;
        if (parameter instanceof Pageable) {
            Pageable pageable = (Pageable) parameter;
            page = pageable.getPage();
            size = pageable.getSize();
            offset = pageable.getOffset();
            rows = pageable.getRows();
            countExpr = pageable.getCountExpr();
            countMethodName = pageable.getCountMethodName();
        } else if (parameter instanceof Map) {
            Map<?, ?> parameterMap = (Map<?, ?>) parameter;
            page = parseInteger(getMapValue(parameterMap, settings.getPageField()));
            size = parseInteger(getMapValue(parameterMap, settings.getSizeField()));
            offset = parseLong(getMapValue(parameterMap, settings.getOffsetField()));
            rows = parseInteger(getMapValue(parameterMap, settings.getRowsField()));
            countExpr = parseString(getMapValue(parameterMap, settings.getCountExprField()));
            countMethodName = parseString(getMapValue(parameterMap, settings.getCountMethodNameField()));
        } else {
            page = parseInteger(getValue(parameter, definition.getPageField(), definition.getPageReadMethod()));
            size = parseInteger(getValue(parameter, definition.getSizeField(), definition.getSizeReadMethod()));
            offset = parseLong(getValue(parameter, definition.getOffsetField(), definition.getOffsetReadMethod()));
            rows = parseInteger(getValue(parameter, definition.getRowsField(), definition.getRowsReadMethod()));
            countExpr = parseString(getValue(parameter, definition.getCountExprField(), definition.getCountExprReadMethod()));
            countMethodName = parseString(getValue(parameter, definition.getCountMethodNameField(), definition.getCountMethodNameReadMethod()));
        }
        if (isEmpty(countExpr) && !isEmpty(definition.getCountExpr())) {
            countExpr = definition.getCountExpr();
        }
        usePageSize = page != null && size != null;
        useOffsetRows = offset != null && rows != null;
        if (usePageSize) {
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
            offset = (page - 1L) * size;
            rows = size;
        } else if (useOffsetRows) {
            if (rows <= 0 && defaultSize > 0) {
                rows = defaultSize;
            }
            if (offset <= 0 && defaultPage > 0) {
                offset = (defaultPage - 1L) * rows;
            }
            if (rows > maxSize && maxSize > 0) {
                rows = maxSize;
            }
            if (offset / rows + 1 > maxPage && maxPage > 0) {
                offset = (maxPage - 1L) * rows;
            }
            // Convert [offset, rows] to [page, size] if rows can be divided by offset.
            if (rows > 0 && offset % rows == 0) {
                page = (int) (offset / rows + 1);
                size = rows;
            } else {
                page = null;
                size = null;
            }
        } else {
            return null;
        }
        return new PaginationParameter(page, size, offset, rows, countExpr, countMethodName);
    }

    /**
     * Builds {@link PaginationDefinition} from parameter.
     * @param parameter The parameter
     * @return {@link PaginationDefinition}
     */
    private PaginationDefinition buildPaginationDefinition(Object parameter) {
        if (parameter instanceof Map) {
            // Support for annotation '@Param' with a single object.
            Map<?, ?> parameterMap = (Map<?, ?>) parameter;
            Map.Entry<?, ?> o = null;
            if (parameterMap.size() == 1) {
                Map.Entry<?, ?>[] entries = parameterMap.entrySet().toArray(new Map.Entry<?, ?>[0]);
                if (entries[0].getValue() != null
                        && !(entries[0].getValue() instanceof Number)
                        && !(entries[0].getValue() instanceof CharSequence)) {
                    o = entries[0];
                }
            } else if (parameterMap.size() == 2) {
                Map.Entry<?, ?>[] entries = parameterMap.entrySet().toArray(new Map.Entry<?, ?>[0]);
                if (entries[0].getValue() != null
                        && entries[0].getValue() == entries[1].getValue()
                        && !(entries[0].getValue() instanceof Number)
                        && !(entries[0].getValue() instanceof CharSequence)) {
                    o = entries[0];
                }
            }
            if (o != null) {
                PaginationDefinition definition = createPaginationDefinition(o.getValue());
                if (definition.isValid()) {
                    definition.setNestedMapKey(String.valueOf(o.getKey()));
                }
                return definition;
            }
        }
        return createPaginationDefinition(parameter);
    }

    /**
     * Creates {@link PaginationDefinition} from parameter.
     * @param parameter The parameter
     * @return {@link PaginationDefinition}
     */
    private PaginationDefinition createPaginationDefinition(Object parameter) {
        Pagination pagination = parameter.getClass().getAnnotation(Pagination.class);
        // It might be an instance of 'Pageable' or 'Map'.
        if (parameter instanceof Pageable || parameter instanceof Map) {
            return createPaginationDefinitionWithDefaultValue(pagination);
        }
        Field pageField = null;
        Field sizeField = null;
        Field offsetField = null;
        Field rowsField = null;
        Field countExprField = null;
        Field countMethodNameField = null;
        Method pageReadMethod = null;
        Method sizeReadMethod = null;
        Method offsetReadMethod = null;
        Method rowsReadMethod = null;
        Method countExprReadMethod = null;
        Method countMethodNameReadMethod = null;
        // Obtain the fields and methods that should be used to read the property value.
        List<Field> fields = getFields(parameter.getClass());
        List<Method> methods = getMethods(parameter.getClass());
        if (pagination != null) {
            for (Field field : fields) {
                if (field.getAnnotation(Page.class) != null) {
                    pageField = field;
                } else if (field.getAnnotation(Size.class) != null) {
                    sizeField = field;
                } else if (field.getAnnotation(Offset.class) != null) {
                    offsetField = field;
                } else if (field.getAnnotation(Rows.class) != null) {
                    rowsField = field;
                } else if (field.getAnnotation(CountExpr.class) != null) {
                    countExprField = field;
                } else if (field.getAnnotation(CountMethodName.class) != null) {
                    countMethodNameField = field;
                }
            }
            for (Method method : methods) {
                if (pageReadMethod == null && method.getAnnotation(Page.class) != null) {
                    pageReadMethod = method;
                } else if (sizeReadMethod == null && method.getAnnotation(Size.class) != null) {
                    sizeReadMethod = method;
                } else if (offsetReadMethod == null && method.getAnnotation(Offset.class) != null) {
                    offsetReadMethod = method;
                } else if (rowsReadMethod == null && method.getAnnotation(Rows.class) != null) {
                    rowsReadMethod = method;
                } else if (countExprReadMethod == null && method.getAnnotation(CountExpr.class) != null) {
                    countExprReadMethod = method;
                } else if (countMethodNameReadMethod == null && method.getAnnotation(CountMethodName.class) != null) {
                    countMethodNameReadMethod = method;
                }
            }
        }
        if ((settings.getPageField() != null && settings.getSizeField() != null) || (settings.getOffsetField() != null && settings.getRowsField() != null)) {
            for (Field field : fields) {
                if (pageField == null && field.getName().equals(settings.getPageField())) {
                    pageField = field;
                } else if (sizeField == null && field.getName().equals(settings.getSizeField())) {
                    sizeField = field;
                } else if (offsetField == null && field.getName().equals(settings.getOffsetField())) {
                    offsetField = field;
                } else if (rowsField == null && field.getName().equals(settings.getRowsField())) {
                    rowsField = field;
                } else if (countExprField == null && field.getName().equals(settings.getCountExprField())) {
                    countExprField = field;
                } else if (countMethodNameField == null && field.getName().equals(settings.getCountMethodNameField())) {
                    countMethodNameField = field;
                }
            }
        }
        if (pageField == null && sizeField == null && offsetField == null && rowsField == null
                && pageReadMethod == null && sizeReadMethod == null && offsetReadMethod == null && rowsReadMethod == null) {
            return PaginationDefinition.INVALID;
        }
        return createPaginationDefinitionWithDefaultValue(pagination)
                .setFields(pageField, sizeField, offsetField, rowsField, countExprField, countMethodNameField)
                .setMethods(pageReadMethod, sizeReadMethod, offsetReadMethod, rowsReadMethod, countExprReadMethod, countMethodNameReadMethod);
    }

    /**
     * Creates {@link PaginationDefinition} from parameter with default value.
     * @param pagination {@link Pagination}
     * @return {@link PaginationDefinition}
     */
    private PaginationDefinition createPaginationDefinitionWithDefaultValue(Pagination pagination) {
        if (pagination != null) {
            return new PaginationDefinition(
                    pagination.defaultPage() > 0 ? pagination.defaultPage() : settings.getDefaultPage(),
                    pagination.defaultSize() > 0 ? pagination.defaultSize() : settings.getDefaultSize(),
                    pagination.maxPage() > 0 ? pagination.maxPage() : settings.getMaxPage(),
                    pagination.maxSize() > 0 ? pagination.maxSize() : settings.getMaxSize(),
                    !isEmpty(pagination.countExpr()) ? pagination.countExpr() : settings.getCountExpr());
        } else {
            return new PaginationDefinition(
                    settings.getDefaultPage(),
                    settings.getDefaultSize(),
                    settings.getMaxPage(),
                    settings.getMaxSize(),
                    settings.getCountExpr());
        }
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     * @param map It might be an object of {@link org.apache.ibatis.binding.MapperMethod.ParamMap}
     * @param key The key whose associated value is to be returned
     * @return The value to which the specified key is mapped
     */
    private Object getMapValue(Map<?, ?> map, Object key) {
        if (map == null || key == null) {
            return null;
        }
        // DO NOT use the 'get' method, It might be an instance of MapperMethod$ParamMap.
        // It might throw a ‘key not found’ exception, if this map contains no mapping for the key.
        return map.getOrDefault(key, null);
    }

    private Object getFieldValue(Object o, Field filed) {
        if (o == null || filed == null) {
            return null;
        }
        try {
            if (!Modifier.isPublic(filed.getModifiers())) {
                filed.setAccessible(true);
            }
            return filed.get(o);
        } catch (Exception ignored) {
            return null;
        }
    }

    private Object invokeMethod(Object o, Method method) {
        if (o == null || method == null) {
            return null;
        }
        try {
            if (!Modifier.isPublic(method.getModifiers())) {
                method.setAccessible(true);
            }
            return method.invoke(o);
        } catch (Exception ignored) {
            return null;
        }
    }

    private Object getValue(Object o, Field field, Method method) {
        Object value = getFieldValue(o, field);
        if (value != null) {
            return value;
        }
        return invokeMethod(o, method);
    }

    private Integer parseInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        return null;
    }

    private Long parseLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            return Long.parseLong((String) value);
        }
        return null;
    }

    private String parseString(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    private Boolean parseBoolean(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (boolean) value;
        } else if (value instanceof Number) {
            // Return false if the value equals 0.
            return ((Number) value).intValue() != 0;
        } else if (value instanceof String) {
            String s = (String) value;
            // Return true if the text equals "true".
            if ("true".equalsIgnoreCase(s)) {
                return true;
            }
            // Return false if the text equals "false".
            if ("false".equalsIgnoreCase(s)) {
                return false;
            }
        }
        return null;
    }

    private String buildCountMsKey(MappedStatement ms) {
        return ms.getId() + "__COUNT";
    }

    private String buildDefinitionParamKey(Object parameter, String msId) {
        return msId + "#" + parameter.getClass().getName();
    }

    private boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    private Field getField(Class<?> clazz, String name) {
        Class<?> c = clazz;
        while (c != null && c != Object.class) {
            try {
                return clazz.getDeclaredField(name);
            } catch (NoSuchFieldException ignored) {
            }
            c = c.getSuperclass();
        }
        return null;
    }

    private List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Class<?> c = clazz;
        while (c != null && c != Object.class) {
            Collections.addAll(fields, c.getDeclaredFields());
            c = c.getSuperclass();
        }
        return fields;
    }

    private List<Method> getMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        Class<?> c = clazz;
        while (c != null && c != Object.class) {
            Collections.addAll(methods, c.getDeclaredMethods());
            c = c.getSuperclass();
        }
        return methods;
    }

    public Dialect getDialect() {
        return dialect;
    }

    public PaginationSettings getSettings() {
        return settings;
    }
}