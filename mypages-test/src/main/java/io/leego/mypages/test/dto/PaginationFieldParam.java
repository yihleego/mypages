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
public class PaginationFieldParam {
    @Page
    private Integer page;
    @Size
    private Integer size;
    @Offset
    private Long offset;
    @Rows
    private Integer rows;
    private Integer status;

    public PaginationFieldParam() {
    }

    public PaginationFieldParam(Integer page, Integer size, Integer status) {
        this.page = page;
        this.size = size;
        this.status = status;
    }

    public PaginationFieldParam(Long offset, Integer rows, Integer status) {
        this.offset = offset;
        this.rows = rows;
        this.status = status;
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

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

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
