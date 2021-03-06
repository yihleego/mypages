package io.leego.mypages.util;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Yihleego
 */
public class PaginationHashSet<E> extends HashSet<E> implements PaginationSet<E> {
    private static final long serialVersionUID = -103532518747068742L;
    /** Total quantity */
    private Long total;
    /** One-based page index */
    private Integer page;
    /** The size of the page to be returned */
    private Integer size;

    public PaginationHashSet() {
    }

    public PaginationHashSet(Long total, Integer page, Integer size) {
        this.total = total;
        this.page = page;
        this.size = size;
    }

    public PaginationHashSet(Collection<? extends E> c, Long total, Integer page, Integer size) {
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
