package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParameter;

/**
 * @author Yihleego
 */
public class InformixDialect extends AbstractDialect {

    @Override
    public String getPageSql(String sql) {
        return "SELECT SKIP ? FIRST ? * FROM (" + sql + ") " + PAGING_TABLE_ALIAS;
    }

    @Override
    public Object[] prependParameterValues(PaginationParameter param) {
        return new Object[]{
                param.getOffset(),
                param.getRows()};
    }

}
