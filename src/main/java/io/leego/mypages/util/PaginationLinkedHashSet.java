package io.leego.mypages.util;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author Yihleego
 */
public class PaginationLinkedHashSet<E> extends LinkedHashSet<E> implements PaginationSet<E> {
    private static final long serialVersionUID = -1222682925184311390L;
    /** total number of data */
    private transient Long total;
    /** current page number */
    private transient Integer page;
    /** page size */
    private transient Integer size;

    public PaginationLinkedHashSet() {
    }

    public PaginationLinkedHashSet(Long total, Integer page, Integer size) {
        this.total = total;
        this.page = page;
        this.size = size;
    }

    public PaginationLinkedHashSet(Collection<? extends E> c, Long total, Integer page, Integer size) {
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
