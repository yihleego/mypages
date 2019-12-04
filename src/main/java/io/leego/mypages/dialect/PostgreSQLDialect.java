package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;

/**
 * @author Yihleego
 */
public class PostgreSQLDialect extends AbstractDialect {

    @Override
    public String getPaginationSql(String sql) {
        return sql + " LIMIT ? OFFSET ?";
    }

    @Override
    public Object[] getPaginationParam(PaginationParam paginationParam) {
        return new Object[]{
                paginationParam.getRows(),
                paginationParam.getOffset()};
    }

}
