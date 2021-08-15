package io.leego.mypages.test.dto;

import io.leego.mypages.util.Pageable;

/**
 * @author Yihleego
 */
public class PageableParam implements Pageable {
    private Integer page;
    private Integer size;
    private Long offset;
    private Integer rows;
    private Integer status;

    public PageableParam() {
    }

    public PageableParam(Integer page, Integer size, Integer status) {
        this.page = page;
        this.size = size;
        this.status = status;
    }

    public PageableParam(Long offset, Integer rows, Integer status) {
        this.offset = offset;
        this.rows = rows;
        this.status = status;
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

    @Override
    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    @Override
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
