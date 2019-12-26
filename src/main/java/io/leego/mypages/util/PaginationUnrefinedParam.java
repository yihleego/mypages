package io.leego.mypages.util;

import java.lang.reflect.Method;

/**
 * @author Yihleego
 */
public class PaginationUnrefinedParam {
    private boolean reasonable;
    private int defaultPage;
    private int defaultSize;
    private int maxPage;
    private int maxSize;
    private String countColumn;
    private Method pageReadMethod;
    private Method sizeReadMethod;
    private Method offsetReadMethod;
    private Method rowsReadMethod;
    private Method countColumnReadMethod;
    private boolean mapType;

    public PaginationUnrefinedParam() {
    }

    public PaginationUnrefinedParam(boolean reasonable, int defaultPage, int defaultSize, int maxPage, int maxSize, String countColumn) {
        this.reasonable = reasonable;
        this.defaultPage = defaultPage;
        this.defaultSize = defaultSize;
        this.maxPage = maxPage;
        this.maxSize = maxSize;
        this.countColumn = countColumn;
    }

    public PaginationUnrefinedParam(boolean reasonable, int defaultPage, int defaultSize, int maxPage, int maxSize, String countColumn, Method pageReadMethod, Method sizeReadMethod, Method offsetReadMethod, Method rowsReadMethod, Method countColumnReadMethod, boolean mapType) {
        this.reasonable = reasonable;
        this.defaultPage = defaultPage;
        this.defaultSize = defaultSize;
        this.maxPage = maxPage;
        this.maxSize = maxSize;
        this.countColumn = countColumn;
        this.pageReadMethod = pageReadMethod;
        this.sizeReadMethod = sizeReadMethod;
        this.offsetReadMethod = offsetReadMethod;
        this.rowsReadMethod = rowsReadMethod;
        this.countColumnReadMethod = countColumnReadMethod;
        this.mapType = mapType;
    }

    public boolean isReasonable() {
        return reasonable;
    }

    public void setReasonable(boolean reasonable) {
        this.reasonable = reasonable;
    }

    public int getDefaultPage() {
        return defaultPage;
    }

    public void setDefaultPage(int defaultPage) {
        this.defaultPage = defaultPage;
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getCountColumn() {
        return countColumn;
    }

    public void setCountColumn(String countColumn) {
        this.countColumn = countColumn;
    }

    public Method getPageReadMethod() {
        return pageReadMethod;
    }

    public void setPageReadMethod(Method pageReadMethod) {
        this.pageReadMethod = pageReadMethod;
    }

    public Method getSizeReadMethod() {
        return sizeReadMethod;
    }

    public void setSizeReadMethod(Method sizeReadMethod) {
        this.sizeReadMethod = sizeReadMethod;
    }

    public Method getOffsetReadMethod() {
        return offsetReadMethod;
    }

    public void setOffsetReadMethod(Method offsetReadMethod) {
        this.offsetReadMethod = offsetReadMethod;
    }

    public Method getRowsReadMethod() {
        return rowsReadMethod;
    }

    public void setRowsReadMethod(Method rowsReadMethod) {
        this.rowsReadMethod = rowsReadMethod;
    }

    public Method getCountColumnReadMethod() {
        return countColumnReadMethod;
    }

    public void setCountColumnReadMethod(Method countColumnReadMethod) {
        this.countColumnReadMethod = countColumnReadMethod;
    }

    public boolean isMapType() {
        return mapType;
    }

    public void setMapType(boolean mapType) {
        this.mapType = mapType;
    }
}
