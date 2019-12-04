package io.leego.mypages.util;

import java.io.Serializable;

/**
 * @author Yihleego
 */
public class Search implements Serializable {
    private static final long serialVersionUID = -1134397368997770745L;
    /** One-based page index */
    protected Integer page;
    /** The size of the page to be returned */
    protected Integer size;
    /** Zero-based row index */
    protected Integer offset;
    /** The rows of the page to be returned */
    protected Integer rows;
    /** Uses specify a mapped column for counting property or field */
    protected String countColumn;

    public Search() {
    }

    public Search(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Search(Integer page, Integer size, Integer offset, Integer rows, String countColumn) {
        this.page = page;
        this.size = size;
        this.offset = offset;
        this.rows = rows;
        this.countColumn = countColumn;
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

    public String getCountColumn() {
        return countColumn;
    }

    public void setCountColumn(String countColumn) {
        this.countColumn = countColumn;
    }

}
