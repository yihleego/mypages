package io.leego.mypages.test.dto;

/**
 * @author Yihleego
 */
public class CustomClassParam {
    private Integer page;
    private Integer size;
    private Long offset;
    private Integer rows;
    private Integer status;

    public CustomClassParam() {
    }

    public CustomClassParam(Integer page, Integer size, Integer status) {
        this.page = page;
        this.size = size;
        this.status = status;
    }

    public CustomClassParam(Long offset, Integer rows, Integer status) {
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
