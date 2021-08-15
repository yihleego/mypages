package io.leego.mypages.interceptor;

import io.leego.mypages.dialect.Database;

/**
 * @author Yihleego
 */
public class PaginationSettings {
    /** The specified {@link Database}. */
    private Database database;
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
    /** The count enabled field name. */
    private String enableCountField;
    /** Whether to skip query if total value equals zero. */
    private boolean skipQueryIfCountEqualsZero = true;
    /** Whether to use generated if the specified count method is missing. */
    private boolean useGeneratedIfCountMethodIsMissing = true;
    /** Whether to use graceful count sql. */
    private boolean useGracefulCountSql = true;
    /** Whether to keep sorting. */
    private boolean keepSorting = false;
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

    public PaginationSettings(Database database) {
        this.database = database;
    }

    public PaginationSettings(
            Database database, String countExpr,
            String pageField, String sizeField, String offsetField, String rowsField,
            String countExprField, String countMethodNameField, String enableCountField,
            boolean skipQueryIfCountEqualsZero, boolean useGeneratedIfCountMethodIsMissing, boolean useGracefulCountSql, boolean keepSorting,
            int defaultPage, int defaultSize, int maxPage, int maxSize) {
        this.database = database;
        this.countExpr = countExpr;
        this.pageField = pageField;
        this.sizeField = sizeField;
        this.offsetField = offsetField;
        this.rowsField = rowsField;
        this.countExprField = countExprField;
        this.countMethodNameField = countMethodNameField;
        this.enableCountField = enableCountField;
        this.skipQueryIfCountEqualsZero = skipQueryIfCountEqualsZero;
        this.useGeneratedIfCountMethodIsMissing = useGeneratedIfCountMethodIsMissing;
        this.useGracefulCountSql = useGracefulCountSql;
        this.keepSorting = keepSorting;
        this.defaultPage = defaultPage;
        this.defaultSize = defaultSize;
        this.maxPage = maxPage;
        this.maxSize = maxSize;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
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

    public boolean isUseGracefulCountSql() {
        return useGracefulCountSql;
    }

    public void setUseGracefulCountSql(boolean useGracefulCountSql) {
        this.useGracefulCountSql = useGracefulCountSql;
    }

    public boolean isKeepSorting() {
        return keepSorting;
    }

    public void setKeepSorting(boolean keepSorting) {
        this.keepSorting = keepSorting;
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

    public static PaginationSettings empty() {
        return new PaginationSettings();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final PaginationSettings settings = new PaginationSettings();

        public Builder database(Database database) {
            settings.setDatabase(database);
            return this;
        }

        public Builder countExpr(String countExpr) {
            settings.setCountExpr(countExpr);
            return this;
        }

        public Builder pageField(String pageField) {
            settings.setPageField(pageField);
            return this;
        }

        public Builder sizeField(String sizeField) {
            settings.setSizeField(sizeField);
            return this;
        }

        public Builder offsetField(String offsetField) {
            settings.setOffsetField(offsetField);
            return this;
        }

        public Builder rowsField(String rowsField) {
            settings.setRowsField(rowsField);
            return this;
        }

        public Builder countExprField(String countExprField) {
            settings.setCountExprField(countExprField);
            return this;
        }

        public Builder countMethodNameField(String countMethodNameField) {
            settings.setCountMethodNameField(countMethodNameField);
            return this;
        }

        public Builder enableCountField(String enableCountField) {
            settings.setEnableCountField(enableCountField);
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

        public Builder useGracefulCountSql(boolean useGracefulCountSql) {
            settings.setUseGracefulCountSql(useGracefulCountSql);
            return this;
        }

        public Builder keepSorting(boolean keepSorting) {
            settings.setKeepSorting(keepSorting);
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
