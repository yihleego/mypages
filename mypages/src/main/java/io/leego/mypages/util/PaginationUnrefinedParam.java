package io.leego.mypages.util;

import java.lang.reflect.Method;

/**
 * @author Yihleego
 */
public class PaginationUnrefinedParam {
    private static final PaginationUnrefinedParam INVALIDATION = new PaginationUnrefinedParam(false);
    private int defaultPage;
    private int defaultSize;
    private int maxPage;
    private int maxSize;
    private String countExpr;
    private Method pageReadMethod;
    private Method sizeReadMethod;
    private Method offsetReadMethod;
    private Method rowsReadMethod;
    private Method countExprReadMethod;
    private Method countMethodNameReadMethod;
    private boolean mapType;
    private final boolean valid;

    public PaginationUnrefinedParam(boolean valid) {
        this.valid = valid;
    }

    public PaginationUnrefinedParam(int defaultPage, int defaultSize, int maxPage, int maxSize, String countExpr, boolean mapType, boolean valid) {
        this.defaultPage = defaultPage;
        this.defaultSize = defaultSize;
        this.maxPage = maxPage;
        this.maxSize = maxSize;
        this.countExpr = countExpr;
        this.mapType = mapType;
        this.valid = valid;
    }

    public static PaginationUnrefinedParam invalidation() {
        return INVALIDATION;
    }

    public void setMethods(Method pageReadMethod, Method sizeReadMethod, Method offsetReadMethod, Method rowsReadMethod, Method countExprReadMethod, Method countMethodNameReadMethod) {
        this.pageReadMethod = pageReadMethod;
        this.sizeReadMethod = sizeReadMethod;
        this.offsetReadMethod = offsetReadMethod;
        this.rowsReadMethod = rowsReadMethod;
        this.countExprReadMethod = countExprReadMethod;
        this.countMethodNameReadMethod = countMethodNameReadMethod;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isInvalid() {
        return !valid;
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

    public String getCountExpr() {
        return countExpr;
    }

    public void setCountExpr(String countExpr) {
        this.countExpr = countExpr;
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

    public Method getCountExprReadMethod() {
        return countExprReadMethod;
    }

    public void setCountExprReadMethod(Method countExprReadMethod) {
        this.countExprReadMethod = countExprReadMethod;
    }

    public Method getCountMethodNameReadMethod() {
        return countMethodNameReadMethod;
    }

    public void setCountMethodNameReadMethod(Method countMethodNameReadMethod) {
        this.countMethodNameReadMethod = countMethodNameReadMethod;
    }

    public boolean isMapType() {
        return mapType;
    }

    public void setMapType(boolean mapType) {
        this.mapType = mapType;
    }

}
