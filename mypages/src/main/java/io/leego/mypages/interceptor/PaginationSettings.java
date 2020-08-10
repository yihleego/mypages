package io.leego.mypages.interceptor;

import io.leego.mypages.dialect.Dialect;
import io.leego.mypages.dialect.SqlDialect;

/**
 * @author Yihleego
 */
public final class PaginationSettings {
    /** The instance of {@link Dialect}. */
    private Dialect dialect;
    /** The specified SQL dialect. */
    private SqlDialect sqlDialect;
    /** The column name or expression, the default value is <code>"*"</code>. */
    private String countExpr;
    /** The page field name. */
    private String pageFieldName;
    /** The size field name. */
    private String sizeFieldName;
    /** The offset field name. */
    private String offsetFieldName;
    /** The rows field name. */
    private String rowsFieldName;
    /** The count expression field name. */
    private String countExprFieldName;
    /** The count method name field name. */
    private String countMethodNameFieldName;
    /** The enable count field name. */
    private String enableCountFieldName;
    /** Whether to obtain values from parameter by reflection. */
    private boolean obtainValuesFromFields = false;
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

    public PaginationSettings() {
    }

    public PaginationSettings(Dialect dialect, SqlDialect sqlDialect, String countExpr, String pageFieldName, String sizeFieldName, String offsetFieldName, String rowsFieldName, String countExprFieldName, String countMethodNameFieldName, String enableCountFieldName, boolean obtainValuesFromFields, boolean skipQueryIfCountEqualsZero, boolean useGeneratedIfCountMethodIsMissing, int defaultPage, int defaultSize, int maxPage, int maxSize) {
        this.dialect = dialect;
        this.sqlDialect = sqlDialect;
        this.countExpr = countExpr;
        this.pageFieldName = pageFieldName;
        this.sizeFieldName = sizeFieldName;
        this.offsetFieldName = offsetFieldName;
        this.rowsFieldName = rowsFieldName;
        this.countExprFieldName = countExprFieldName;
        this.countMethodNameFieldName = countMethodNameFieldName;
        this.enableCountFieldName = enableCountFieldName;
        this.obtainValuesFromFields = obtainValuesFromFields;
        this.skipQueryIfCountEqualsZero = skipQueryIfCountEqualsZero;
        this.useGeneratedIfCountMethodIsMissing = useGeneratedIfCountMethodIsMissing;
        this.defaultPage = defaultPage;
        this.defaultSize = defaultSize;
        this.maxPage = maxPage;
        this.maxSize = maxSize;
    }

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
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

    public String getPageFieldName() {
        return pageFieldName;
    }

    public void setPageFieldName(String pageFieldName) {
        this.pageFieldName = pageFieldName;
    }

    public String getSizeFieldName() {
        return sizeFieldName;
    }

    public void setSizeFieldName(String sizeFieldName) {
        this.sizeFieldName = sizeFieldName;
    }

    public String getOffsetFieldName() {
        return offsetFieldName;
    }

    public void setOffsetFieldName(String offsetFieldName) {
        this.offsetFieldName = offsetFieldName;
    }

    public String getRowsFieldName() {
        return rowsFieldName;
    }

    public void setRowsFieldName(String rowsFieldName) {
        this.rowsFieldName = rowsFieldName;
    }

    public String getCountExprFieldName() {
        return countExprFieldName;
    }

    public void setCountExprFieldName(String countExprFieldName) {
        this.countExprFieldName = countExprFieldName;
    }

    public String getCountMethodNameFieldName() {
        return countMethodNameFieldName;
    }

    public void setCountMethodNameFieldName(String countMethodNameFieldName) {
        this.countMethodNameFieldName = countMethodNameFieldName;
    }

    public String getEnableCountFieldName() {
        return enableCountFieldName;
    }

    public void setEnableCountFieldName(String enableCountFieldName) {
        this.enableCountFieldName = enableCountFieldName;
    }

    public boolean isObtainValuesFromFields() {
        return obtainValuesFromFields;
    }

    public void setObtainValuesFromFields(boolean obtainValuesFromFields) {
        this.obtainValuesFromFields = obtainValuesFromFields;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final PaginationSettings settings = new PaginationSettings();

        public Builder dialect(Dialect dialect) {
            settings.setDialect(dialect);
            return this;
        }

        public Builder sqlDialect(SqlDialect sqlDialect) {
            settings.setSqlDialect(sqlDialect);
            return this;
        }

        public Builder countExpr(String countExpr) {
            settings.setCountExpr(countExpr);
            return this;
        }

        public Builder pageField(String pageFieldName) {
            settings.setPageFieldName(pageFieldName);
            return this;
        }

        public Builder sizeField(String sizeFieldName) {
            settings.setSizeFieldName(sizeFieldName);
            return this;
        }

        public Builder offsetField(String offsetFieldName) {
            settings.setOffsetFieldName(offsetFieldName);
            return this;
        }

        public Builder rowsField(String rowsFieldName) {
            settings.setRowsFieldName(rowsFieldName);
            return this;
        }

        public Builder countExprField(String countExprFieldName) {
            settings.setCountExprFieldName(countExprFieldName);
            return this;
        }

        public Builder countMethodNameField(String countMethodNameFieldName) {
            settings.setCountMethodNameFieldName(countMethodNameFieldName);
            return this;
        }

        public Builder enableCountField(String enableCountFieldName) {
            settings.setEnableCountFieldName(enableCountFieldName);
            return this;
        }

        public Builder skipQueryIfCountEqualsZero(boolean skipQueryIfCountEqualsZero) {
            settings.setSkipQueryIfCountEqualsZero(skipQueryIfCountEqualsZero);
            return this;
        }

        public Builder useGeneratedIfCountMethodIsMissing(boolean useGeneratedIfCountMethodIsMissing) {
            settings.setUseGeneratedIfCountMethodIsMissing(useGeneratedIfCountMethodIsMissing);
            return this;
        }

        public Builder defaultPage(int defaultPage) {
            settings.setDefaultPage(defaultPage);
            return this;
        }

        public Builder defaultSize(int defaultSize) {
            settings.setDefaultSize(defaultSize);
            return this;
        }

        public Builder maxPage(int maxPage) {
            settings.setMaxPage(maxPage);
            return this;
        }

        public Builder maxSize(int maxSize) {
            settings.setMaxSize(maxSize);
            return this;
        }

        public PaginationSettings build() {
            return settings;
        }

    }

}
