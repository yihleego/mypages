package io.leego.mypages.util;

import java.util.ArrayDeque;
import java.util.Collection;

/**
 * @author Yihleego
 */
public class PaginationArrayDeque<E> extends ArrayDeque<E> implements PaginationQueue<E> {
    private static final long serialVersionUID = 2924447574361737057L;
    /** Total quantity */
    private Long total;
    /** One-based page index */
    private Integer page;
    /** The size of the page to be returned */
    private Integer size;

    public PaginationArrayDeque() {
    }

    public PaginationArrayDeque(Long total, Integer page, Integer size) {
        this.total = total;
        this.page = page;
        this.size = size;
    }

    public PaginationArrayDeque(Collection<? extends E> c, Long total, Integer page, Integer size) {
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
