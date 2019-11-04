package io.leego.mypages.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * @author Yihleego
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = -5524075755628202327L;
    /** list */
    protected List<T> list;
    /** page number one-based page index */
    protected Integer page;
    /** the size of the page to be returned */
    protected Integer size;
    /** total quantity */
    protected Long total;
    /** total pages */
    protected Long totalPages;
    /** has next page */
    protected Boolean next;
    /** has previous page */
    protected Boolean previous;

    public Page(List<T> list, Integer page, Integer size, Long total, Long totalPages, Boolean next, Boolean previous) {
        this.list = list;
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = totalPages;
        this.next = next;
        this.previous = previous;
    }

    public Page(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public Page(List<T> list) {
        this.list = list;
    }

    public static <T> Page<T> of(List<T> list, Integer page, Integer size, Long total, Long totalPages, Boolean next, Boolean previous) {
        return new Page<>(list, page, size, total, totalPages, next, previous);
    }

    public static <T> Page<T> of(List<T> list, Integer page, Integer size, Long total) {
        if (page == null || size == null) {
            return of(list, total);
        }
        boolean next = page * size < total;
        boolean previous = page > 1;
        long totalPages = total % size > 0 ? total / size + 1 : total / size;
        return new Page<>(list, page, size, total, totalPages, next, previous);
    }

    public static <T> Page<T> of(List<T> list, Long total) {
        return new Page<>(list, total);
    }

    public static <T> Page<T> of(Collection<T> collection) {
        if (collection instanceof PaginationCollection) {
            if (collection instanceof PaginationList) {
                return of((PaginationList<T>) collection);
            } else if (collection instanceof PaginationSet) {
                return of((PaginationSet<T>) collection);
            } else if (collection instanceof PaginationQueue) {
                return of((PaginationQueue<T>) collection);
            }
        }
        return new Page<>(toList(collection));
    }

    public static <T> Page<T> of(PaginationList<T> list) {
        return of(list, list.getPage(), list.getSize(), list.getTotal());
    }

    public static <T> Page<T> of(PaginationSet<T> set) {
        return of(toList(set), set.getPage(), set.getSize(), set.getTotal());
    }

    public static <T> Page<T> of(PaginationQueue<T> queue) {
        return of(toList(queue), queue.getPage(), queue.getSize(), queue.getTotal());
    }

    public static <T> Page<T> of(List<T> list) {
        if (list instanceof PaginationList) {
            return of((PaginationList<T>) list);
        }
        return new Page<>(list);
    }

    public static <T> Page<T> of(Set<T> set) {
        if (set instanceof PaginationSet) {
            return of((PaginationSet<T>) set);
        }
        return new Page<>(toList(set));
    }

    public static <T> Page<T> of(Queue<T> queue) {
        if (queue instanceof PaginationQueue) {
            return of((PaginationQueue<T>) queue);
        }
        return new Page<>(toList(queue));
    }

    private static <T> List<T> toList(Collection<T> collection) {
        if (collection == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(collection);
    }

    public static <T> Page<T> empty() {
        return new Page<>(Collections.emptyList(), 0, 0, 0L, 0L, false, false);
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Boolean getNext() {
        return next;
    }

    public void setNext(Boolean next) {
        this.next = next;
    }

    public Boolean getPrevious() {
        return previous;
    }

    public void setPrevious(Boolean previous) {
        this.previous = previous;
    }

    public static class Builder<T> {
        private List<T> list;
        private Integer page;
        private Integer size;
        private Long total;
        private Long totalPages;
        private Boolean next;
        private Boolean previous;

        public Builder<T> list(List<T> list) {
            this.list = list;
            return this;
        }

        public Builder<T> page(Integer page) {
            this.page = page;
            return this;
        }

        public Builder<T> size(Integer size) {
            this.size = size;
            return this;
        }

        public Builder<T> total(Long total) {
            this.total = total;
            return this;
        }

        public Builder<T> totalPages(Long totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public Builder<T> next(Boolean next) {
            this.next = next;
            return this;
        }

        public Builder<T> previous(Boolean previous) {
            this.previous = previous;
            return this;
        }

        public Page<T> build() {
            return new Page<>(list, page, size, total, totalPages, next, previous);
        }

    }
}
