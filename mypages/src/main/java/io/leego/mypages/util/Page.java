package io.leego.mypages.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Yihleego
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = 3214571808482585491L;
    /** Data list. */
    protected List<T> list;
    /** One-based page index. */
    protected Integer page;
    /** The size of the page to be returned. */
    protected Integer size;
    /** The quantity of data. */
    protected Long total;
    /** The quantity of data pages. */
    protected Long pages;
    /** Returns <code>true</code> if there is a next page. */
    protected Boolean next;
    /** Returns <code>true</code> if there is a previous page. */
    protected Boolean previous;
    /** Extra data. */
    protected Object extra;

    public Page() {
    }

    public Page(List<T> list) {
        this.list = list;
    }

    public Page(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public Page(List<T> list, Integer page, Integer size) {
        this.list = list;
        this.page = page;
        this.size = size;
    }

    public Page(List<T> list, Integer page, Integer size, Long total, Long pages, Boolean next, Boolean previous) {
        this.list = list;
        this.page = page;
        this.size = size;
        this.total = total;
        this.pages = pages;
        this.next = next;
        this.previous = previous;
    }

    public Page(List<T> list, Integer page, Integer size, Long total, Long pages, Boolean next, Boolean previous, Object extra) {
        this.list = list;
        this.page = page;
        this.size = size;
        this.total = total;
        this.pages = pages;
        this.next = next;
        this.previous = previous;
        this.extra = extra;
    }

    public static <T> Page<T> of(List<T> list, Integer page, Integer size, Long total, Long pages, Boolean next, Boolean previous, Object extra) {
        return new Page<>(list, page, size, total, pages, next, previous, extra);
    }

    public static <T> Page<T> of(List<T> list, Integer page, Integer size, Long total, Long pages, Boolean next, Boolean previous) {
        return new Page<>(list, page, size, total, pages, next, previous);
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
        long pages;
        if (page > 0 && size > 0) {
            next = page.longValue() * size < total;
            previous = page != 1;
            pages = total % size > 0 ? total / size + 1 : total / size;
        } else {
            next = false;
            previous = false;
            pages = 0L;
        }
        return new Page<>(list, page, size, total, pages, next, previous);
    }

    public static <T> Page<T> of(List<T> list, Integer page, Integer size) {
        return new Page<>(list, page, size);
    }

    public static <T> Page<T> of(List<T> list, Long total) {
        return new Page<>(list, total);
    }

    public static <T> Page<T> wrap(Collection<T> collection) {
        return new Page<>(toList(collection));
    }

    public static <T> Page<T> wrap(List<T> list) {
        return new Page<>(list);
    }

    public static <T> Page<T> empty() {
        return new Page<>(new ArrayList<>());
    }

    public static <T> Page<T> of(PaginationCollection<T> source) {
        if (source == null) {
            return empty();
        }
        return of(toList(source), source.getPage(), source.getSize(), source.getTotal());
    }

    public static <T> Page<T> of(Collection<T> source) {
        if (source == null) {
            return empty();
        }
        if (source instanceof PaginationCollection) {
            return of((PaginationCollection<T>) source);
        }
        return wrap(toList(source));
    }

    public static <T> Page<T> of(PaginationList<T> source) {
        if (source == null) {
            return empty();
        }
        return of(source, source.getPage(), source.getSize(), source.getTotal());
    }

    public static <T> Page<T> of(List<T> source) {
        if (source == null) {
            return empty();
        }
        if (source instanceof PaginationList) {
            return of((PaginationList<T>) source);
        }
        return wrap(source);
    }

    public static <T, S> Page<T> of(PaginationCollection<S> source, Function<? super S, ? extends T> mapper) {
        if (source == null) {
            return empty();
        }
        return of(mapping(source, mapper), source.getPage(), source.getSize(), source.getTotal());
    }

    public static <T, S> Page<T> of(Collection<S> source, Function<? super S, ? extends T> mapper) {
        if (source == null) {
            return empty();
        }
        if (source instanceof PaginationCollection) {
            return of((PaginationCollection<S>) source, mapper);
        }
        return wrap(mapping(source, mapper));
    }

    public static <T, S> Page<T> transfer(PaginationCollection<S> source, Collection<T> target) {
        if (source == null || target == null) {
            return empty();
        }
        return of(toList(target), source.getPage(), source.getSize(), source.getTotal());
    }

    public static <T, S> Page<T> transfer(Collection<S> source, Collection<T> target) {
        if (source == null || target == null) {
            return empty();
        }
        if (source instanceof PaginationCollection) {
            return transfer((PaginationCollection<S>) source, target);
        }
        return wrap(toList(target));
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
        if (collection instanceof List) {
            return (List<T>) collection;
        }
        return new ArrayList<>(collection);
    }

    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new Page<>(list == null ? null : list.stream().map(converter).collect(Collectors.toList()),
                page, size, total, pages, next, previous, extra);
    }

    public Page<T> peek(Consumer<? super T> action) {
        if (list != null) {
            list.forEach(action);
        }
        return this;
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

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
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

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public <E> E getExtra(Class<E> type) {
        return type.cast(extra);
    }

    public boolean isEmpty() {
        return list == null || list.isEmpty();
    }

    public boolean isPaged() {
        return list != null && page != null && size != null && total != null;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private List<T> list;
        private Integer page;
        private Integer size;
        private Long total;
        private Long pages;
        private Boolean next;
        private Boolean previous;
        private Object extra;

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

        public Builder<T> pages(Long pages) {
            this.pages = pages;
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

        public Builder<T> extra(Object extra) {
            this.extra = extra;
            return this;
        }

        public Page<T> build() {
            return new Page<>(list, page, size, total, pages, next, previous, extra);
        }

    }

    @Override
    public String toString() {
        return "{page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", pages=" + pages +
                ", next=" + next +
                ", previous=" + previous +
                ", extra=" + extra +
                ", list=" + list +
                '}';
    }
}
