package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Map;

/**
 * @author Yihleego
 */
public class DB2Dialect extends AbstractDialect {

    @Override
    public String getPaginationSql(String sql, int offset, int rows) {
        return "SELECT * FROM (SELECT TMP_PAGE.*,ROWNUMBER() OVER() AS ROW_ID FROM ( "
                + sql + " ) AS TMP_PAGE) TMP_PAGE WHERE ROW_ID BETWEEN ? AND ?";
    }

    @Override
    public Object[] getPaginationParam(MappedStatement ms, Map<Object, Object> paramMap, BoundSql boundSql, CacheKey pageKey, PaginationParam paginationParam) {
        return new Object[]{paginationParam.getOffset() + 1, paginationParam.getOffset() + paginationParam.getRows()};
    }

}
