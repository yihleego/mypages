package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;

/**
 * @author Yihleego
 */
public class PostgreSQLDialect extends AbstractDialect {

    @Override
    public String getPagingSql(String sql) {
        return sql + " LIMIT ? OFFSET ?";
    }

    @Override
    public Object[] getPagingParams(PaginationParam param) {
        return new Object[]{
                param.getRows(),
                param.getOffset()};
    }

}
