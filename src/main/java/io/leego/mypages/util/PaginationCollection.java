package io.leego.mypages.util;

import java.util.Collection;

/**
 * @author Yihleego
 */
public interface PaginationCollection<E> extends Collection<E> {

    Long getTotal();

    Integer getPage();

    Integer getSize();

}
