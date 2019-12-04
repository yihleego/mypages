package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;

/**
 * @author Yihleego
 */
public class MySQLDialect extends AbstractDialect {

    @Override
    public String getPaginationSql(String sql) {
        return sql + " LIMIT ?,?";
    }

    @Override
    public Object[] getPaginationParam(PaginationParam paginationParam) {
        return new Object[]{
                paginationParam.getOffset(),
                paginationParam.getRows()};
    }

}
