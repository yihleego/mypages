package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParameter;

/**
 * @author Yihleego
 */
public class DerbyDialect extends AbstractDialect {

    @Override
    public String getPageSql(String sql) {
        return sql + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    }

    @Override
    public Object[] appendParameterValues(PaginationParameter param) {
        return new Object[]{
                param.getOffset(),
                param.getRows()};
    }

}
