package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;

/**
 * @author Yihleego
 */
public class InformixDialect extends AbstractDialect {

    @Override
    public String getPaginationSql(String sql) {
        return "SELECT SKIP ? FIRST ? * FROM (" + sql + ")";
    }

    @Override
    public Object[] getPaginationParam(PaginationParam paginationParam) {
        return new Object[]{
                paginationParam.getOffset(),
                paginationParam.getRows()};
    }

}
