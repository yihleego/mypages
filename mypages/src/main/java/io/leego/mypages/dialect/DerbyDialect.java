package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;

/**
 * @author Yihleego
 */
public class DerbyDialect extends AbstractDialect {

    @Override
    public String getPagingSql(String sql) {
        return sql + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    }

    @Override
    public Object[] getPagingParams(PaginationParam param) {
        return new Object[]{
                param.getOffset(),
                param.getRows()};
    }

}
