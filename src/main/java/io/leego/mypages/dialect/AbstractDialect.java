package io.leego.mypages.dialect;

import io.leego.mypages.util.MetaObjectUtils;
import io.leego.mypages.util.PaginationParam;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;

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

    @Override
    public String getPaginationSql(String sql, int offset, int rows) {
        return getPaginationSql(sql);
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
    public Object processParams(MappedStatement ms, Object parameter, BoundSql boundSql, CacheKey pageKey, PaginationParam paginationParam) {
        Map<Object, Object> paramMap;
        if (parameter == null) {
            paramMap = new HashMap<>();
        } else if (parameter instanceof Map) {
            paramMap = new HashMap<>((Map<?, ?>) parameter);
        } else {
            paramMap = new HashMap<>();
            boolean hasTypeHandler = ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameter.getClass());
            MetaObject metaObject = MetaObjectUtils.forObject(parameter);
            if (!hasTypeHandler) {
                for (String name : metaObject.getGetterNames()) {
                    paramMap.put(name, metaObject.getValue(name));
                }
            }
        }
        List<ParameterMapping> newParameterMappings;
        if (boundSql.getParameterMappings() instanceof ArrayList) {
            newParameterMappings = boundSql.getParameterMappings();
        } else {
            newParameterMappings = new ArrayList<>(boundSql.getParameterMappings());
        }
        Object[] params = getPaginationParam(paginationParam);
        for (int i = 0; i < params.length; i++) {
            String property = PAGINATION_PARAM + i;
            paramMap.put(property, params[i]);
            pageKey.update(params[i]);
            newParameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), property, params[i].getClass()).build());
        }
        if (boundSql.getParameterMappings() != null) {
            MetaObject metaObject = MetaObjectUtils.forObject(boundSql);
            metaObject.setValue("parameterMappings", newParameterMappings);
        }
        return paramMap;
    }

    public abstract Object[] getPaginationParam(PaginationParam paginationParam);

}
