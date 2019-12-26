package io.leego.mypages.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Yihleego
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = -5524075755628202327L;
    /** Data list */
    protected List<T> list;
    /** One-based page index */
    protected Integer page;
    /** The size of the page to be returned */
    protected Integer size;
    /** Total quantity */
    protected Long total;
    /** Total pages */
    protected Long totalPages;
    /** Next page */
    protected Boolean next;
    /** Previous page */
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

    public Page(List<T> list, Integer page, Integer size) {
        this.list = list;
        this.page = page;
        this.size = size;
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
            return new Page<>(list, total);
        }
        if (total == null) {
            return new Page<>(list, page, size);
        }
        boolean next;
        boolean previous;
        long totalPages;
        if (page > 0 && size > 0) {
            next = page * size < total;
            previous = page != 1;
            totalPages = total % size > 0 ? total / size + 1 : total / size;
        } else {
            next = false;
            previous = false;
            totalPages = 0L;
        }
        return new Page<>(list, page, size, total, totalPages, next, previous);
    }

    public static <T> Page<T> of(List<T> list, Integer page, Integer size) {
        return new Page<>(list, page, size);
    }

    public static <T> Page<T> of(List<T> list, Long total) {
        return new Page<>(list, total);
    }

    public static <T> Page<T> empty() {
        return new Page<>(new ArrayList<>(), 0, 0, 0L, 0L, false, false);
    }


    public static <T> Page<T> of(PaginationList<T> list) {
        if (list == null) {
            return empty();
        }
        return of(list, list.getPage(), list.getSize(), list.getTotal());
    }

    public static <T> Page<T> of(PaginationSet<T> set) {
        if (set == null) {
            return empty();
        }
        return of(toList(set), set.getPage(), set.getSize(), set.getTotal());
    }

    public static <T> Page<T> of(PaginationQueue<T> queue) {
        if (queue == null) {
            return empty();
        }
        return of(toList(queue), queue.getPage(), queue.getSize(), queue.getTotal());
    }

    public static <T> Page<T> of(Collection<T> collection) {
        if (collection instanceof PaginationList) {
            return of((PaginationList<T>) collection);
        } else if (collection instanceof PaginationSet) {
            return of((PaginationSet<T>) collection);
        } else if (collection instanceof PaginationQueue) {
            return of((PaginationQueue<T>) collection);
        } else if (collection instanceof List) {
            return new Page<>((List<T>) collection);
        }
        return new Page<>(toList(collection));
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


    public static <T, S> Page<T> of(PaginationList<S> list, Function<? super S, ? extends T> mapper) {
        if (list == null) {
            return empty();
        }
        return of(mapping(list, mapper), list.getPage(), list.getSize(), list.getTotal());
    }

    public static <T, S> Page<T> of(PaginationSet<S> set, Function<? super S, ? extends T> mapper) {
        if (set == null) {
            return empty();
        }
        return of(mapping(set, mapper), set.getPage(), set.getSize(), set.getTotal());
    }

    public static <T, S> Page<T> of(PaginationQueue<S> queue, Function<? super S, ? extends T> mapper) {
        if (queue == null) {
            return empty();
        }
        return of(mapping(queue, mapper), queue.getPage(), queue.getSize(), queue.getTotal());
    }

    public static <T, S> Page<T> of(Collection<S> collection, Function<? super S, ? extends T> mapper) {
        if (collection instanceof PaginationList) {
            return of((PaginationList<S>) collection, mapper);
        } else if (collection instanceof PaginationSet) {
            return of((PaginationSet<S>) collection, mapper);
        } else if (collection instanceof PaginationQueue) {
            return of((PaginationQueue<S>) collection, mapper);
        }
        return new Page<>(mapping(collection, mapper));
    }

    public static <T, S> Page<T> of(List<S> list, Function<? super S, ? extends T> mapper) {
        if (list instanceof PaginationList) {
            return of((PaginationList<S>) list, mapper);
        }
        return new Page<>(mapping(list, mapper));
    }

    public static <T, S> Page<T> of(Set<S> set, Function<? super S, ? extends T> mapper) {
        if (set instanceof PaginationSet) {
            return of((PaginationSet<S>) set, mapper);
        }
        return new Page<>(mapping(set, mapper));
    }

    public static <T, S> Page<T> of(Queue<S> queue, Function<? super S, ? extends T> mapper) {
        if (queue instanceof PaginationQueue) {
            return of((PaginationQueue<S>) queue, mapper);
        }
        return new Page<>(mapping(queue, mapper));
    }


    private static <T, S> List<T> mapping(Collection<S> source, Function<? super S, ? extends T> mapper) {
        if (source == null || source.isEmpty()) {
            return new ArrayList<>();
        }
        return source.stream().map(mapper).collect(Collectors.toList());
    }

    private static <T> List<T> toList(Collection<T> collection) {
        if (collection == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(collection);
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
