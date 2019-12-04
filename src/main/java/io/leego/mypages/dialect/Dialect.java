package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * @author Yihleego
 */
public interface Dialect {

    String getPaginationSql(String sql);

    String getPaginationSql(String sql, int offset, int rows);

    String getCountSql(String sql);

    String getCountSql(String sql, String column);

    Object processParams(MappedStatement ms, Object parameter, BoundSql boundSql, CacheKey pageKey, PaginationParam param);
}
