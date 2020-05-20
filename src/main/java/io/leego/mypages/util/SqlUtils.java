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
import io.leego.mypages.dialect.SqlDialect;
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
    public static final String TEMP_COUNT_TABLE_ALIAS = "MP_TCT";
    private static final SQLExpr COUNT_EXPR = SQLUtils.toSQLExpr("COUNT(*)");
    /** Aggregate functions */
    private static final String[] AGGREGATE_FUNCTIONS = {
            "avg",
            "binary_checksum", "bit_and", "bit_or", "bit_xor",
            "checksum", "checksum_agg", "count", "count_big",
            "first",
            "group_concat", "grouping",
            "last",
            "max", "median", "min",
            "rank",
            "std", "stddev", "stddev_pop", "stddev_samp", "stdev", "stdevp", "sum",
            "var", "var_pop", "var_samp", "variance", "varp",};
    private static final Map<Character, Set<String>> AGGREGATE_FUNCTION_MAP;

    static {
        AGGREGATE_FUNCTION_MAP = Collections.unmodifiableMap(
                Arrays.stream(AGGREGATE_FUNCTIONS)
                        .map(o -> o.toLowerCase() + "(")
                        .collect(Collectors.groupingBy(o -> o.charAt(0), Collectors.toSet())));
    }

    private SqlUtils() {
    }

    public static String toCountSql(String sql) {
        return toCountSql(sql, null);
    }

    public static String toCountSql(String sql, String column) {
        try {
            // Ignore the SQL dialect.
            SQLStatement sqlStatement = SQLUtils.parseSingleStatement(sql, JdbcConstants.MYSQL);
            if (!(sqlStatement instanceof SQLSelectStatement)) {
                throw new PaginationException("Unsupported non-query SQL");
            }
            SQLSelectStatement sqlSelectStatement = (SQLSelectStatement) sqlStatement;
            SQLSelectQuery sqlSelectQuery = sqlSelectStatement.getSelect().getQuery();
            if (!(sqlSelectQuery instanceof SQLSelectQueryBlock)) {
                return toSimpleCountSql(sql, column);
            }
            // Return simple count SQL if it is a distinct query.
            SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock) sqlSelectQuery;
            if (sqlSelectQueryBlock.isDistinct()) {
                return toSimpleCountSql(sql, column);
            }
            // Return simple count SQL if there is "GROUP BY".
            SQLSelectGroupByClause sqlSelectGroupByClause = sqlSelectQueryBlock.getGroupBy();
            if (sqlSelectGroupByClause != null) {
                return toSimpleCountSql(sql, column);
            }
            // Return simple count SQL if there are aggregate functions.
            List<SQLSelectItem> sqlSelectItems = sqlSelectQueryBlock.getSelectList();
            if (hasAnyAggregate(sqlSelectItems)) {
                return toSimpleCountSql(sql, column);
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
            return toSimpleCountSql(sql, column);
        }
    }

    public static String toSimpleCountSql(String sql) {
        return "SELECT COUNT(" + ASTERISK + ") FROM (" + sql + ") " + TEMP_COUNT_TABLE_ALIAS;
    }

    public static String toSimpleCountSql(String sql, String column) {
        if (column == null || column.isEmpty()) {
            column = ASTERISK;
        }
        return "SELECT COUNT(" + column + ") FROM (" + sql + ") " + TEMP_COUNT_TABLE_ALIAS;
    }

    public static SQLExpr buildCountExpr(String column) {
        if (column == null || column.isEmpty()) {
            return COUNT_EXPR;
        }
        return SQLUtils.toSQLExpr("COUNT(" + column + ")");
    }

    public static String getDbType(SqlDialect sqlDialect) {
        if (sqlDialect == null) {
            return JdbcConstants.MYSQL;
        }
        switch (sqlDialect) {
            case MYSQL:
                return JdbcConstants.MYSQL;
            case MARIADB:
                return JdbcConstants.MARIADB;
            case SQLITE:
                return JdbcConstants.SQLITE;
            case TIDB:
                return JdbcConstants.MYSQL;
            case POSTGRESQL:
                return JdbcConstants.POSTGRESQL;
            case HSQLDB:
                return JdbcConstants.HSQL;
            case H2:
                return JdbcConstants.H2;
            case PHOENIX:
                return JdbcConstants.PHOENIX;
            case ORACLE:
                return JdbcConstants.ORACLE;
            case DB2:
                return JdbcConstants.DB2;
            case INFORMIX:
                return JdbcConstants.INFORMIX;
            case DERBY:
                return JdbcConstants.DERBY;
            case SQLSERVER:
                return JdbcConstants.SQL_SERVER;
            case HIVE:
                return JdbcConstants.HIVE;
            default:
                return JdbcConstants.MYSQL;
        }
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
