package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;

/**
 * @author Yihleego
 */
public class HiveDialect extends AbstractDialect {

    @Override
    public String getPaginationSql(String sql) {
        return "SELECT * " +
                "FROM (SELECT MP_TPT.*, ROW_NUMBER() OVER() AS MP_ROWNUMBER FROM (" + sql + ") AS MP_TPT) " +
                "WHERE MP_ROWNUMBER BETWEEN ? AND ?";
    }

    @Override
    public Object[] getPaginationParam(PaginationParam paginationParam) {
        return new Object[]{
                paginationParam.getOffset() + 1,
                paginationParam.getOffset() + paginationParam.getRows()};
    }

}
