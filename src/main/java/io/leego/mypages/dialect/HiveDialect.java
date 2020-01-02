package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;

/**
 * @author Yihleego
 */
public class HiveDialect extends AbstractDialect {

    @Override
    public String getPagingSql(String sql) {
        return "SELECT * " +
                "FROM (SELECT MP_TPT.*, ROW_NUMBER() OVER() AS MP_ROWNUMBER FROM (" + sql + ") AS MP_TPT) " +
                "WHERE MP_ROWNUMBER BETWEEN ? AND ?";
    }

    @Override
    public Object[] getPagingParams(PaginationParam param) {
        return new Object[]{
                param.getOffset() + 1,
                param.getOffset() + param.getRows()};
    }

}
