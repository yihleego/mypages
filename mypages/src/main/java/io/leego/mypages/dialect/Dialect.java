package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * @author Yihleego
 */
public interface Dialect {

    String getPagingSql(String sql);

    String getPagingSql(String sql, PaginationParam param);

    String getCountSql(String sql);

    String getCountSql(String sql, String expr);

    Object assembleParameter(MappedStatement ms, Object parameter, BoundSql boundSql, CacheKey pageKey, PaginationParam param) throws Exception;
}
