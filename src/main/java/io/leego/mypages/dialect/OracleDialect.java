package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;

/**
 * @author Yihleego
 */
public class OracleDialect extends AbstractDialect {

    @Override
    public String getPaginationSql(String sql) {
        return "SELECT * " +
                "FROM (SELECT MP_TPT.*, ROWNUM MP_ROW_ID FROM (" + sql + ") MP_TPT WHERE ROWNUM <= ?) " +
                "WHERE MP_ROW_ID > ?";
    }

    @Override
    public Object[] getPaginationParam(PaginationParam paginationParam) {
        return new Object[]{
                paginationParam.getOffset() + paginationParam.getRows(),
                paginationParam.getOffset()};
    }

}
