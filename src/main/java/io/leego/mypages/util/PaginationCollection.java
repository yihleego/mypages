package io.leego.mypages.util;

import java.util.Collection;

/**
 * @author Yihleego
 */
public interface PaginationCollection<E> extends Collection<E> {

    /**
     * Returns total number of data.
     */
    Long getTotal();

    /**
     * Returns number of the page index.
     */
    Integer getPage();

    /**
     * Returns number of the page size.
     */
    Integer getSize();

}
