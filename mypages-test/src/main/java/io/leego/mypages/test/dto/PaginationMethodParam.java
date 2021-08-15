package io.leego.mypages.test.dto;

import io.leego.mypages.annotation.Offset;
import io.leego.mypages.annotation.Page;
import io.leego.mypages.annotation.Pagination;
import io.leego.mypages.annotation.Rows;
import io.leego.mypages.annotation.Size;

/**
 * @author Yihleego
 */
@Pagination
public class PaginationMethodParam {
    private Integer page;
    private Integer size;
    private Long offset;
    private Integer rows;
    private Integer status;

    public PaginationMethodParam() {
    }

    public PaginationMethodParam(Integer page, Integer size, Integer status) {
        this.page = page;
        this.size = size;
        this.status = status;
    }

    public PaginationMethodParam(Long offset, Integer rows, Integer status) {
        this.offset = offset;
        this.rows = rows;
        this.status = status;
    }

    @Page
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Size
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Offset
    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    @Rows
    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
