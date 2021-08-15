package io.leego.mypages.util;

/**
 * @author Yihleego
 */
public interface Pageable {

    /** Returns one-based page index. */
    Integer getPage();

    /** Returns the size of the page to be returned. */
    Integer getSize();

    /** Returns zero-based row index. */
    default Long getOffset() {
        return null;
    }

    /** Returns the rows of the page to be returned. */
    default Integer getRows() {
        return null;
    }

    /** Returns the column name or expression. */
    default String getCountExpr() {
        return null;
    }

    /** Returns the count method name. */
    default String getCountMethodName() {
        return null;
    }

}
