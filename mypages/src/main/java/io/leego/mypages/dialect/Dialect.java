package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParameter;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * @author Yihleego
 */
public interface Dialect {

    String getPageSql(String sql);

    String getPageSql(String sql, PaginationParameter paginationParameter);

    String getCountSql(String sql);

    String getCountSql(String sql, String expr);

    String getCountSql(String sql, String expr, boolean keepSorting);

    String getSimpleCountSql(String sql);

    String getSimpleCountSql(String sql, String expr);

    Object handleParameter(MappedStatement ms, Object parameter, BoundSql boundSql, CacheKey cacheKey, PaginationParameter paginationParameter) throws Exception;

}
