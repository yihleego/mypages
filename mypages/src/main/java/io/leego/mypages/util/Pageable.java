package io.leego.mypages.util;

import java.io.Serializable;

/**
 * @author Yihleego
 */
public class Pageable implements Serializable {
    private static final long serialVersionUID = -1134397368997770745L;
    /** One-based page index */
    protected Integer page;
    /** The size of the page to be returned */
    protected Integer size;
    /** Zero-based row index */
    protected Integer offset;
    /** The rows of the page to be returned */
    protected Integer rows;
    /** The column name or expression, the default value is <code>"*"</code>. */
    protected String countExpr;
    /** The count method name. */
    protected String countMethodName;

    public Pageable() {
    }

    public Pageable(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Pageable(Integer page, Integer size, Integer offset, Integer rows) {
        this.page = page;
        this.size = size;
        this.offset = offset;
        this.rows = rows;
    }

    public Pageable(Integer page, Integer size, Integer offset, Integer rows, String countExpr, String countMethodName) {
        this.page = page;
        this.size = size;
        this.offset = offset;
        this.rows = rows;
        this.countExpr = countExpr;
        this.countMethodName = countMethodName;
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

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getCountExpr() {
        return countExpr;
    }

    public void setCountExpr(String countExpr) {
        this.countExpr = countExpr;
    }

    public String getCountMethodName() {
        return countMethodName;
    }

    public void setCountMethodName(String countMethodName) {
        this.countMethodName = countMethodName;
    }
}
