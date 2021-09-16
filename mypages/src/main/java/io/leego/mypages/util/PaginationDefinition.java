package io.leego.mypages.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Yihleego
 */
public class PaginationDefinition {
    public static final PaginationDefinition INVALID = new PaginationDefinition(false);
    private int defaultPage;
    private int defaultSize;
    private int maxPage;
    private int maxSize;
    private String countExpr;
    private Field pageField;
    private Field sizeField;
    private Field offsetField;
    private Field rowsField;
    private Field countExprField;
    private Field countMethodNameField;
    private Method pageReadMethod;
    private Method sizeReadMethod;
    private Method offsetReadMethod;
    private Method rowsReadMethod;
    private Method countExprReadMethod;
    private Method countMethodNameReadMethod;
    private final boolean valid;

    public PaginationDefinition(boolean valid) {
        this.valid = valid;
    }

    public PaginationDefinition(int defaultPage, int defaultSize, int maxPage, int maxSize, String countExpr) {
        this.defaultPage = defaultPage;
        this.defaultSize = defaultSize;
        this.maxPage = maxPage;
        this.maxSize = maxSize;
        this.countExpr = countExpr;
        this.valid = true;
    }

    public PaginationDefinition setFields(Field pageField, Field sizeField, Field offsetField, Field rowsField, Field countExprField, Field countMethodNameField) {
        this.pageField = pageField;
        this.sizeField = sizeField;
        this.offsetField = offsetField;
        this.rowsField = rowsField;
        this.countExprField = countExprField;
        this.countMethodNameField = countMethodNameField;
        return this;
    }

    public PaginationDefinition setMethods(Method pageReadMethod, Method sizeReadMethod, Method offsetReadMethod, Method rowsReadMethod, Method countExprReadMethod, Method countMethodNameReadMethod) {
        this.pageReadMethod = pageReadMethod;
        this.sizeReadMethod = sizeReadMethod;
        this.offsetReadMethod = offsetReadMethod;
        this.rowsReadMethod = rowsReadMethod;
        this.countExprReadMethod = countExprReadMethod;
        this.countMethodNameReadMethod = countMethodNameReadMethod;
        return this;
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

    public Field getPageField() {
        return pageField;
    }

    public void setPageField(Field pageField) {
        this.pageField = pageField;
    }

    public Field getSizeField() {
        return sizeField;
    }

    public void setSizeField(Field sizeField) {
        this.sizeField = sizeField;
    }

    public Field getOffsetField() {
        return offsetField;
    }

    public void setOffsetField(Field offsetField) {
        this.offsetField = offsetField;
    }

    public Field getRowsField() {
        return rowsField;
    }

    public void setRowsField(Field rowsField) {
        this.rowsField = rowsField;
    }

    public Field getCountExprField() {
        return countExprField;
    }

    public void setCountExprField(Field countExprField) {
        this.countExprField = countExprField;
    }

    public Field getCountMethodNameField() {
        return countMethodNameField;
    }

    public void setCountMethodNameField(Field countMethodNameField) {
        this.countMethodNameField = countMethodNameField;
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

    public boolean isValid() {
        return valid;
    }
}
