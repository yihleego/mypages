package io.leego.mypages.dialect;

import io.leego.mypages.util.PaginationParam;

/**
 * @author Yihleego
 */
public class InformixDialect extends AbstractDialect {

    @Override
    public String getPagingSql(String sql) {
        return "SELECT SKIP ? FIRST ? * FROM (" + sql + ")";
    }

    @Override
    public Object[] getPagingParams(PaginationParam param) {
        return new Object[]{
                param.getOffset(),
                param.getRows()};
    }

}
