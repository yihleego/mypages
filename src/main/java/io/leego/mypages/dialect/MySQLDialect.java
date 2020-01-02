package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;

/**
 * @author Yihleego
 */
public class MySQLDialect extends AbstractDialect {

    @Override
    public String getPagingSql(String sql) {
        return sql + " LIMIT ?,?";
    }

    @Override
    public Object[] getPagingParams(PaginationParam param) {
        return new Object[]{
                param.getOffset(),
                param.getRows()};
    }

}
