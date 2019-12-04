package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;

/**
 * @author Yihleego
 */
public class DerbyDialect extends AbstractDialect {

    @Override
    public String getPaginationSql(String sql) {
        return sql + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    }

    @Override
    public Object[] getPaginationParam(PaginationParam paginationParam) {
        return new Object[]{
                paginationParam.getOffset(),
                paginationParam.getRows()};
    }

}
