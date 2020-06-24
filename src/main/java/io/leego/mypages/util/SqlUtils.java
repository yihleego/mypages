package io.leego.mypages.util;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLAggregateExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectGroupByClause;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.util.JdbcConstants;
import io.leego.mypages.exception.PaginationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Yihleego
 */
public final class SqlUtils {
    public static final String ASTERISK = "*";
    private static final String DEFAULT_TABLE_ALIAS = "DTA";
    private static final SQLExpr COUNT_EXPR = SQLUtils.toSQLExpr("COUNT(*)");
    /** Aggregate functions */
    private static final String[] AGGREGATE_FUNCTION_NAMES = {
            "avg",
            "binary_checksum", "bit_and", "bit_or", "bit_xor",
            "checksum", "checksum_agg", "count", "count_big",
            "first",
            "group_concat", "grouping",
            "last",
            "max", "median", "min",
            "rank",
            "std", "stddev", "stddev_pop", "stddev_samp", "stdev", "stdevp", "sum",
            "var", "var_pop", "var_samp", "variance", "varp",
    };
    private static final Map<Character, Set<String>> AGGREGATE_FUNCTION_MAP;

    static {
        AGGREGATE_FUNCTION_MAP = Collections.unmodifiableMap(
                Arrays.stream(AGGREGATE_FUNCTION_NAMES)
                        .map(o -> o.toLowerCase() + "(")
                        .collect(Collectors.groupingBy(o -> o.charAt(0), Collectors.toSet())));
    }

    private SqlUtils() {
    }

    public static String toCountSql(String sql, String alias) {
        return toCountSql(sql, null, alias);
    }

    public static String toCountSql(String sql, String column, String alias) {
        try {
            // Ignore the SQL dialect.
            SQLStatement sqlStatement = SQLUtils.parseSingleStatement(sql, JdbcConstants.MYSQL);
            if (!(sqlStatement instanceof SQLSelectStatement)) {
                throw new PaginationException("Unsupported non-query SQL");
            }
            SQLSelectStatement sqlSelectStatement = (SQLSelectStatement) sqlStatement;
            SQLSelectQuery sqlSelectQuery = sqlSelectStatement.getSelect().getQuery();
            if (!(sqlSelectQuery instanceof SQLSelectQueryBlock)) {
                return toSimpleCountSql(sql, column, alias);
            }
            // Return simple count SQL if it is a distinct query.
            SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock) sqlSelectQuery;
            if (sqlSelectQueryBlock.isDistinct()) {
                return toSimpleCountSql(sql, column, alias);
            }
            // Return simple count SQL if there is "GROUP BY".
            SQLSelectGroupByClause sqlSelectGroupByClause = sqlSelectQueryBlock.getGroupBy();
            if (sqlSelectGroupByClause != null) {
                return toSimpleCountSql(sql, column, alias);
            }
            // Return simple count SQL if there are aggregate functions.
            List<SQLSelectItem> sqlSelectItems = sqlSelectQueryBlock.getSelectList();
            if (hasAnyAggregate(sqlSelectItems)) {
                return toSimpleCountSql(sql, column, alias);
            }
            if (sqlSelectItems != null && !sqlSelectItems.isEmpty()) {
                // Remove select items.
                sqlSelectItems.clear();
            }
            // Remove "ORDER BY".
            sqlSelectQueryBlock.setOrderBy(null);
            // Set count function.
            SQLUtils.addSelectItem(sqlStatement, buildCountExpr(column), null, true);
            return sqlStatement.toString();
        } catch (Exception ignored) {
            return toSimpleCountSql(sql, column, alias);
        }
    }

    public static String toSimpleCountSql(String sql, String alias) {
        return "SELECT COUNT(" + ASTERISK + ") FROM (" + sql + ") "
                + (StringUtils.isNotBlank(alias) ? alias : DEFAULT_TABLE_ALIAS);
    }

    public static String toSimpleCountSql(String sql, String column, String alias) {
        if (StringUtils.isBlank(column)) {
            column = ASTERISK;
        }
        return "SELECT COUNT(" + column + ") FROM (" + sql + ") "
                + (StringUtils.isNotBlank(alias) ? alias : DEFAULT_TABLE_ALIAS);
    }

    public static SQLExpr buildCountExpr(String column) {
        if (StringUtils.isBlank(column) || column.equals(ASTERISK)) {
            return COUNT_EXPR;
        }
        return SQLUtils.toSQLExpr("COUNT(" + column + ")");
    }

    public static boolean hasAnyAggregate(List<SQLSelectItem> sqlSelectItems) {
        if (sqlSelectItems == null || sqlSelectItems.isEmpty()) {
            return false;
        }
        for (SQLSelectItem item : sqlSelectItems) {
            SQLExpr sqlExpr = item.getExpr();
            if (sqlExpr == null || ASTERISK.equals(sqlExpr.toString())) {
                continue;
            }
            if (sqlExpr instanceof SQLAggregateExpr) {
                return true;
            }
            String expr = sqlExpr.toString().toLowerCase();
            Set<String> aggregateFunctions = AGGREGATE_FUNCTION_MAP.get(expr.charAt(0));
            if (aggregateFunctions != null && !aggregateFunctions.isEmpty()) {
                for (String aggregateFunction : aggregateFunctions) {
                    if (expr.startsWith(aggregateFunction)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
