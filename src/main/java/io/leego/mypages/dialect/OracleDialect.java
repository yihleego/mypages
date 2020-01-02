package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;

/**
 * @author Yihleego
 */
public class OracleDialect extends AbstractDialect {

    @Override
    public String getPagingSql(String sql) {
        return "SELECT * " +
                "FROM (SELECT MP_TPT.*, ROWNUM MP_ROW_ID FROM (" + sql + ") MP_TPT WHERE ROWNUM <= ?) " +
                "WHERE MP_ROW_ID > ?";
    }

    @Override
    public Object[] getPagingParams(PaginationParam param) {
        return new Object[]{
                param.getOffset() + param.getRows(),
                param.getOffset()};
    }

}
