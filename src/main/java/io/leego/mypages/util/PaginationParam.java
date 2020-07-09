package io.leego.mypages.util;

/**
 * @author Yihleego
 */
public class PaginationParam {
    private static final PaginationParam NON = new PaginationParam(false);
    /** One-based page index */
    protected Integer page;
    /** The size of the page to be returned */
    protected Integer size;
    /** Zero-based row index */
    protected Integer offset;
    /** The rows of the page to be returned */
    protected Integer rows;
    /** Uses a specified mapped column for counting property or field */
    protected String countColumn;
    /** Uses a specified method to count */
    protected String countMethodName;
    /** Whether to paging */
    protected final boolean pageable;

    public PaginationParam(boolean pageable) {
        this.pageable = pageable;
    }

    public PaginationParam(Integer page, Integer size, Integer offset, Integer rows, String countColumn, String countMethodName, boolean pageable) {
        this.page = page;
        this.size = size;
        this.offset = offset;
        this.rows = rows;
        this.countColumn = countColumn;
        this.countMethodName = countMethodName;
        this.pageable = pageable;
    }

    public static PaginationParam nonPagination() {
        return NON;
    }

    public boolean isPageable() {
        return pageable;
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

    public String getCountMethodName() {
        return countMethodName;
    }

    public void setCountMethodName(String countMethodName) {
        this.countMethodName = countMethodName;
    }

}
