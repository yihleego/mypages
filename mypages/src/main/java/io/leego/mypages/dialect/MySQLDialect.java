package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParameter;

/**
 * @author Yihleego
 */
public class MySQLDialect extends AbstractDialect {

    @Override
    public String getPageSql(String sql) {
        return sql + " LIMIT ?, ?";
    }

    @Override
    public Object[] appendParameterValues(PaginationParameter param) {
        return new Object[]{
                param.getOffset(),
                param.getRows()};
    }

}
