package io.leego.mypages.dialect;

import io.leego.mypages.util.BeanUtils;
import io.leego.mypages.util.PaginationParam;
import io.leego.mypages.util.ReflectUtils;
import io.leego.mypages.util.SqlUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yihleego
 */
public abstract class AbstractDialect implements Dialect {
    protected static final String TEMP_TABLE_ALIAS = "MP_TTA";
    protected static final String PAGING_TABLE_ALIAS = "MP_PTA";
    protected static final String COUNTING_TABLE_ALIAS = "MP_CTA";
    protected static final String ROW_NUMBER_ALIAS = "MP_IGNORED_RN";
    protected static final String PAGINATION_PARAM = "MP_PAGINATION_PARAM";
    protected static final String PARAMETER_MAPPINGS = "parameterMappings";
    protected SqlDialect sqlDialect;

    public AbstractDialect() {
    }

    public AbstractDialect(SqlDialect sqlDialect) {
        this.sqlDialect = sqlDialect;
    }

    @Override
    public String getPagingSql(String sql, PaginationParam param) {
        return getPagingSql(sql);
    }

    @Override
    public String getCountSql(String sql) {
        return SqlUtils.toCountSql(sql, COUNTING_TABLE_ALIAS, sqlDialect);
    }

    @Override
    public String getCountSql(String sql, String column) {
        return SqlUtils.toCountSql(sql, column, COUNTING_TABLE_ALIAS, sqlDialect);
    }

    @Override
    public Object processParams(MappedStatement ms, Object parameter, BoundSql boundSql, CacheKey pageKey, PaginationParam param) throws Exception {
        Object[] params = getPagingParams(param);
        if (params == null || params.length == 0) {
            return parameter;
        }
        Map<Object, Object> paramMap;
        if (parameter == null) {
            paramMap = new HashMap<>();
        } else if (parameter instanceof Map) {
            // Prevent unmodifiable map object from throwing exceptions.
            paramMap = new HashMap<>((Map<?, ?>) parameter);
        } else {
            boolean hasTypeHandler = ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameter.getClass());
            paramMap = hasTypeHandler ? new HashMap<>() : BeanUtils.readAll(parameter);
        }
        List<ParameterMapping> parameterMappings;
        if (boundSql.getParameterMappings() == null) {
            parameterMappings = new ArrayList<>();
        } else if (boundSql.getParameterMappings().getClass() == ArrayList.class) {
            parameterMappings = boundSql.getParameterMappings();
        } else {
            // Prevent unmodifiable list object from throwing exceptions.
            parameterMappings = new ArrayList<>(boundSql.getParameterMappings());
        }
        // Append paging parameters.
        int suffix = 0;
        for (Object value : params) {
            String property = PAGINATION_PARAM + (suffix++);
            // Prevent replacing original value.
            while (paramMap.containsKey(property)) {
                property = PAGINATION_PARAM + (suffix++);
            }
            paramMap.put(property, value);
            pageKey.update(value);
            parameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), property, value.getClass()).build());
        }
        if (parameterMappings != boundSql.getParameterMappings()) {
            // Overwrite the value of the parameterMappings.
            ReflectUtils.setFieldValue(boundSql, PARAMETER_MAPPINGS, parameterMappings, true);
        }
        return paramMap;
    }

    public abstract Object[] getPagingParams(PaginationParam param);

    public SqlDialect getSqlDialect() {
        return sqlDialect;
    }

    protected void setSqlDialect(SqlDialect sqlDialect) {
        this.sqlDialect = sqlDialect;
    }

}
