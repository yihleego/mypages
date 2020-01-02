package io.leego.mypages.dialect;

import io.leego.mypages.util.BeanUtils;
import io.leego.mypages.util.PaginationParam;
import io.leego.mypages.util.ReflectUtils;
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
    protected static final String ASTERISK = "*";
    protected static final String PAGINATION_PARAM = "MP_PAGINATION_PARAM";
    protected static final String PARAMETER_MAPPINGS = "parameterMappings";

    @Override
    public String getPagingSql(String sql, PaginationParam param) {
        return getPagingSql(sql);
    }

    @Override
    public String getCountSql(String sql) {
        return "SELECT COUNT(" + ASTERISK + ") FROM (" + sql + ") MP_TCT";
    }

    @Override
    public String getCountSql(String sql, String column) {
        if (column == null || column.isEmpty()) {
            return getCountSql(sql);
        }
        return "SELECT COUNT(" + column + ") FROM (" + sql + ") MP_TCT";
    }

    @Override
    public Object processParams(MappedStatement ms, Object parameter, BoundSql boundSql, CacheKey pageKey, PaginationParam param) throws Exception {
        Map<Object, Object> paramMap;
        if (parameter == null) {
            // Create a new instance, if the parameter is null.
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
            // Create a new instance, if the parameter is null.
            parameterMappings = new ArrayList<>();
        } else if (boundSql.getParameterMappings().getClass() == ArrayList.class) {
            // Cast the parameter to list.
            parameterMappings = boundSql.getParameterMappings();
        } else {
            // Prevent unmodifiable list object from throwing exceptions.
            parameterMappings = new ArrayList<>(boundSql.getParameterMappings());
        }

        // Append paging parameters.
        Object[] params = getPagingParams(param);
        for (int i = 0; i < params.length; i++) {
            String property = PAGINATION_PARAM + i;
            paramMap.put(property, params[i]);
            pageKey.update(params[i]);
            parameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), property, params[i].getClass()).build());
        }
        if (parameterMappings != boundSql.getParameterMappings()) {
            // Overwrite the value of the parameterMappings, [ATTENTION] There is no "setParameterMappings" method.
            ReflectUtils.setFieldValue(boundSql, PARAMETER_MAPPINGS, parameterMappings, true);
        }
        return paramMap;
    }

    public abstract Object[] getPagingParams(PaginationParam param);

}
