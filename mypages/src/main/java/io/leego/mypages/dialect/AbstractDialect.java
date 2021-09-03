package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParameter;
import io.leego.mypages.util.SqlUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yihleego
 */
public abstract class AbstractDialect implements Dialect {
    protected static final String ASTERISK = "*";
    protected static final String TEMP_TABLE_ALIAS = "mp_tta";
    protected static final String PAGING_TABLE_ALIAS = "mp_pta";
    protected static final String COUNTING_TABLE_ALIAS = "mp_cta";
    protected static final String ROW_NUMBER_ALIAS = "mp_trn";
    protected static final String PAGINATION_PARAMETER = "mp_pagination_parameter";
    protected static final String PARAMETER_MAPPINGS = "parameterMappings";

    public AbstractDialect() {
    }

    @Override
    public String getPageSql(String sql, PaginationParameter paginationParameter) {
        return getPageSql(sql);
    }

    @Override
    public String getCountSql(String sql) {
        return getCountSql(sql, ASTERISK, false);
    }

    @Override
    public String getCountSql(String sql, String expr) {
        return getCountSql(sql, expr, false);
    }

    @Override
    public String getCountSql(String sql, String expr, boolean keepSorting) {
        try {
            String value = SqlUtils.toCountSql(sql, expr);
            if (value != null) {
                return value;
            }
        } catch (Exception ignored) {
        }
        return getSimpleCountSql(sql, expr);
    }

    @Override
    public String getSimpleCountSql(String sql) {
        return String.format("SELECT COUNT(*) FROM (%s) %s", sql, COUNTING_TABLE_ALIAS);
    }

    @Override
    public String getSimpleCountSql(String sql, String expr) {
        if (expr == null || expr.isEmpty()) {
            return getSimpleCountSql(sql);
        }
        return String.format("SELECT COUNT(%S) FROM (%s) %s", expr, sql, COUNTING_TABLE_ALIAS);
    }

    @Override
    public Object handleParameter(MappedStatement ms, Object parameter, BoundSql boundSql, CacheKey cacheKey, PaginationParameter paginationParameter) throws Exception {
        Object[] prependedValues = prependParameterValues(paginationParameter);
        Object[] appendedValues = appendParameterValues(paginationParameter);
        int prependedLength = prependedValues == null ? 0 : prependedValues.length;
        int appendedLength = appendedValues == null ? 0 : appendedValues.length;
        if (prependedLength == 0 && appendedLength == 0) {
            return parameter;
        }
        Map<Object, Object> parameterMap;
        if (parameter == null) {
            parameterMap = new HashMap<>();
        } else if (parameter instanceof Map) {
            // Prevent unmodifiable map object from throwing exceptions.
            parameterMap = new HashMap<>((Map<?, ?>) parameter);
        } else {
            PropertyDescriptor[] descriptors = Introspector.getBeanInfo(parameter.getClass()).getPropertyDescriptors();
            parameterMap = new HashMap<>(prependedLength + appendedLength + descriptors.length);
            for (PropertyDescriptor descriptor : descriptors) {
                if (!"class".equals(descriptor.getName())) {
                    parameterMap.put(descriptor.getName(), descriptor.getReadMethod().invoke(parameter));
                }
            }
        }
        List<ParameterMapping> parameterMappings;
        if (boundSql.getParameterMappings() == null || boundSql.getParameterMappings().isEmpty()) {
            parameterMappings = new ArrayList<>(prependedLength + appendedLength);
        } else {
            // Prevent unmodifiable list object from throwing exceptions.
            parameterMappings = new ArrayList<>(boundSql.getParameterMappings());
        }
        int suffix = 0;
        // Prepend parameters.
        if (prependedValues != null) {
            for (int i = 0; i < prependedValues.length; i++) {
                Object value = prependedValues[i];
                if (value == null) {
                    continue;
                }
                // Prevent replacing original value.
                String property;
                while (parameterMap.putIfAbsent(property = PAGINATION_PARAMETER + (suffix++), value) != null) {/*ignored*/}
                cacheKey.update(value);
                parameterMappings.add(i, new ParameterMapping.Builder(ms.getConfiguration(), property, value.getClass()).build());
            }
        }
        // Append parameters.
        if (appendedValues != null) {
            for (int i = 0; i < appendedValues.length; i++) {
                Object value = appendedValues[i];
                if (value == null) {
                    continue;
                }
                // Prevent replacing original value.
                String property;
                while (parameterMap.putIfAbsent(property = PAGINATION_PARAMETER + (suffix++), value) != null) {/*ignored*/}
                cacheKey.update(value);
                parameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), property, value.getClass()).build());
            }
        }
        // Overwrite the value of the parameterMappings.
        Field field = BoundSql.class.getDeclaredField(PARAMETER_MAPPINGS);
        field.setAccessible(true);
        field.set(boundSql, parameterMappings);
        return parameterMap;
    }

    public Object[] appendParameterValues(PaginationParameter paginationParameter) {
        return null;
    }

    public Object[] prependParameterValues(PaginationParameter paginationParameter) {
        return null;
    }
}
