package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Map;

/**
 * @author Yihleego
 */
public class OracleDialect extends AbstractDialect {

    @Override
    public String getPaginationSql(String sql, int offset, int rows) {
        return "SELECT * FROM ( SELECT TMP_PAGE.*, ROWNUM ROW_ID FROM ( " + sql + " ) TMP_PAGE WHERE ROWNUM <= ? ) WHERE ROW_ID > ?";
    }

    @Override
    public Object[] getPaginationParam(MappedStatement ms, Map<Object, Object> paramMap, BoundSql boundSql, CacheKey pageKey, PaginationParam paginationParam) {
        return new Object[]{paginationParam.getOffset() + paginationParam.getRows(), paginationParam.getOffset()};
    }

}
