package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParameter;

/**
 * @author Yihleego
 */
public class OracleDialect extends AbstractDialect {

    @Override
    public String getPageSql(String sql) {
        return "SELECT *" +
                " FROM (SELECT " + TEMP_TABLE_ALIAS + ".*, ROWNUM AS " + ROW_NUMBER_ALIAS + " FROM (" + sql + ") " + TEMP_TABLE_ALIAS + " WHERE ROWNUM <= ?) " + PAGING_TABLE_ALIAS +
                " WHERE " + ROW_NUMBER_ALIAS + " > ?";
    }

    @Override
    public Object[] appendParameterValues(PaginationParameter param) {
        return new Object[]{
                param.getOffset() + param.getRows(),
                param.getOffset()};
    }

}
