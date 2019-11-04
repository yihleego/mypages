package io.leego.mypages.util;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Yihleego
 */
public class PaginationLinkedList<E> extends LinkedList<E> implements PaginationList<E> {
    private static final long serialVersionUID = -2964505756498503802L;
    /** total number of data */
    private transient Long total;
    /** current page number */
    private transient Integer page;
    /** page size */
    private transient Integer size;

    public PaginationLinkedList() {
    }

    public PaginationLinkedList(Long total, Integer page, Integer size) {
        this.total = total;
        this.page = page;
        this.size = size;
    }

    public PaginationLinkedList(Collection<? extends E> c, Long total, Integer page, Integer size) {
        super(c);
        this.total = total;
        this.page = page;
        this.size = size;
    }

    @Override
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
