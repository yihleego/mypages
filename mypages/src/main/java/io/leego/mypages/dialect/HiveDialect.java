package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParameter;

/**
 * @author Yihleego
 */
public class HiveDialect extends AbstractDialect {

    @Override
    public String getPageSql(String sql) {
        return "SELECT *" +
                " FROM (SELECT " + TEMP_TABLE_ALIAS + ".*, ROW_NUMBER() OVER() AS " + ROW_NUMBER_ALIAS + " FROM (" + sql + ") " + TEMP_TABLE_ALIAS + ") " + PAGING_TABLE_ALIAS +
                " WHERE " + ROW_NUMBER_ALIAS + " BETWEEN ? AND ?";
    }

    @Override
    public Object[] appendParameterValues(PaginationParameter param) {
        return new Object[]{
                param.getOffset() + 1,
                param.getOffset() + param.getRows()};
    }

}
