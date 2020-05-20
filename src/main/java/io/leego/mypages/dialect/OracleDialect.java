package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;

/**
 * @author Yihleego
 */
public class OracleDialect extends AbstractDialect {

    @Override
    public String getPagingSql(String sql) {
        return "SELECT *" +
                " FROM (SELECT " + TEMP_TABLE_ALIAS + ".*, ROWNUM AS " + ROW_NUMBER_ALIAS + " FROM (" + sql + ") " + TEMP_TABLE_ALIAS + " WHERE ROWNUM <= ?) " + PAGING_TABLE_ALIAS +
                " WHERE " + ROW_NUMBER_ALIAS + " > ?";
    }

    @Override
    public Object[] getPagingParams(PaginationParam param) {
        return new Object[]{
                param.getOffset() + param.getRows(),
                param.getOffset()};
    }

}
