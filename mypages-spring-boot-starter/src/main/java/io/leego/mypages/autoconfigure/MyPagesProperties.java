package io.leego.mypages.autoconfigure;

import io.leego.mypages.dialect.SqlDialect;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Yihleego
 */
@ConfigurationProperties("spring.mypages")
public class MyPagesProperties {
    /** Enabled */
    private boolean enabled = true;
    /** The specified SQL dialect. */
    private SqlDialect sqlDialect;
    /** The column name or expression, the default value is <code>"*"</code>. */
    private String countExpr;
    /** The page field name. */
    private String pageField;
    /** The size field name. */
    private String sizeField;
    /** The offset field name. */
    private String offsetField;
    /** The rows field name. */
    private String rowsField;
    /** The count expression field name. */
    private String countExprField;
    /** The count method name field name. */
    private String countMethodNameField;
    /** The enable count field name. */
    private String enableCountField;
    /** Whether to skip query if total value equals zero. */
    private boolean skipQueryIfCountEqualsZero = true;
    /** Whether to use generated if the specified count method is missing. */
    private boolean useGeneratedIfCountMethodIsMissing = true;
    /** Replaces <code>page</code> with <code>defaultPage</code> if <code>page</code> is invalid. */
    private int defaultPage = -1;
    /** Replaces <code>size</code> with <code>defaultSize</code> if <code>size</code> is invalid. */
    private int defaultSize = -1;
    /** Replaces <code>page</code> with <code>maxPage</code> if <code>page</code> is invalid. */
    private int maxPage = -1;
    /** Replaces <code>size</code> with <code>maxSize</code> if <code>size</code> is invalid. */
    private int maxSize = -1;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public SqlDialect getSqlDialect() {
        return sqlDialect;
    }

    public void setSqlDialect(SqlDialect sqlDialect) {
        this.sqlDialect = sqlDialect;
    }

    public String getCountExpr() {
        return countExpr;
    }

    public void setCountExpr(String countExpr) {
        this.countExpr = countExpr;
    }

    public String getPageField() {
        return pageField;
    }

    public void setPageField(String pageField) {
        this.pageField = pageField;
    }

    public String getSizeField() {
        return sizeField;
    }

    public void setSizeField(String sizeField) {
        this.sizeField = sizeField;
    }

    public String getOffsetField() {
        return offsetField;
    }

    public void setOffsetField(String offsetField) {
        this.offsetField = offsetField;
    }

    public String getRowsField() {
        return rowsField;
    }

    public void setRowsField(String rowsField) {
        this.rowsField = rowsField;
    }

    public String getCountExprField() {
        return countExprField;
    }

    public void setCountExprField(String countExprField) {
        this.countExprField = countExprField;
    }

    public String getCountMethodNameField() {
        return countMethodNameField;
    }

    public void setCountMethodNameField(String countMethodNameField) {
        this.countMethodNameField = countMethodNameField;
    }

    public String getEnableCountField() {
        return enableCountField;
    }

    public void setEnableCountField(String enableCountField) {
        this.enableCountField = enableCountField;
    }

    public boolean isSkipQueryIfCountEqualsZero() {
        return skipQueryIfCountEqualsZero;
    }

    public void setSkipQueryIfCountEqualsZero(boolean skipQueryIfCountEqualsZero) {
        this.skipQueryIfCountEqualsZero = skipQueryIfCountEqualsZero;
    }

    public boolean isUseGeneratedIfCountMethodIsMissing() {
        return useGeneratedIfCountMethodIsMissing;
    }

    public void setUseGeneratedIfCountMethodIsMissing(boolean useGeneratedIfCountMethodIsMissing) {
        this.useGeneratedIfCountMethodIsMissing = useGeneratedIfCountMethodIsMissing;
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
}
