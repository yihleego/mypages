package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParameter;

/**
 * @author Yihleego
 */
public class H2Dialect extends AbstractDialect {

    @Override
    public String getPageSql(String sql) {
        return sql + " LIMIT ? OFFSET ?";
    }

    @Override
    public Object[] appendParameterValues(PaginationParameter param) {
        return new Object[]{
                param.getRows(),
                param.getOffset()};
    }

}
