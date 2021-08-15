package io.leego.mypages.test.dto;

/**
 * @author Yihleego
 */
public class CustomCountParam {
    private Integer page;
    private Integer size;
    private String countMethodName;
    private Integer status;

    public CustomCountParam() {
    }

    public CustomCountParam(Integer page, Integer size, String countMethodName, Integer status) {
        this.page = page;
        this.size = size;
        this.countMethodName = countMethodName;
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

    public String getCountMethodName() {
        return countMethodName;
    }

    public void setCountMethodName(String countMethodName) {
        this.countMethodName = countMethodName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
