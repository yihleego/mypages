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
import io.leego.mypages.exception.IllegalSqlException;

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
    private static final char OPENING_PARENTHESIS = '(';
    private static final char CLOSING_PARENTHESIS = ')';
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
    private static final Map<Character, Set<String>> AGGREGATE_FUNCTION_MAP = buildAggregateMap(AGGREGATE_FUNCTION_NAMES);

    private SqlUtils() {
    }

    public static String toCountSql(String sql, String alias) {
        return toCountSql(sql, null, alias, null);
    }

    public static String toCountSql(String sql, String expr, String alias) {
        return toCountSql(sql, expr, alias, null);
    }

    public static String toCountSql(String sql, String alias, SqlDialect sqlDialect) {
        return toCountSql(sql, null, alias, sqlDialect);
    }

    public static String toCountSql(String sql, String expr, String alias, SqlDialect sqlDialect) {
        try {
            // Parse statement with db type.
            SQLStatement sqlStatement = SQLUtils.parseSingleStatement(sql, getDbType(sqlDialect));
            if (!(sqlStatement instanceof SQLSelectStatement)) {
                throw new IllegalSqlException("Unsupported non-query SQL");
            }
            SQLSelectStatement sqlSelectStatement = (SQLSelectStatement) sqlStatement;
            SQLSelectQuery sqlSelectQuery = sqlSelectStatement.getSelect().getQuery();
            if (!(sqlSelectQuery instanceof SQLSelectQueryBlock)) {
                return toSimpleCountSql(sql, expr, alias);
            }
            // Return simple count SQL if it is a distinct query.
            SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock) sqlSelectQuery;
            if (sqlSelectQueryBlock.isDistinct()) {
                return toSimpleCountSql(sql, expr, alias);
            }
            // Return simple count SQL if there is "GROUP BY".
            SQLSelectGroupByClause sqlSelectGroupByClause = sqlSelectQueryBlock.getGroupBy();
            if (sqlSelectGroupByClause != null) {
                return toSimpleCountSql(sql, expr, alias);
            }
            // Return simple count SQL if there are aggregate functions.
            List<SQLSelectItem> sqlSelectItems = sqlSelectQueryBlock.getSelectList();
            if (hasAnyAggregate(sqlSelectItems)) {
                return toSimpleCountSql(sql, expr, alias);
            }
            if (sqlSelectItems != null && !sqlSelectItems.isEmpty()) {
                // Remove select items.
                sqlSelectItems.clear();
            }
            // Remove "ORDER BY".
            sqlSelectQueryBlock.setOrderBy(null);
            // Set count function.
            SQLUtils.addSelectItem(sqlStatement, buildCountExpr(expr), null, true);
            return sqlStatement.toString();
        } catch (Exception ignored) {
            return toSimpleCountSql(sql, expr, alias);
        }
    }

    public static String toSimpleCountSql(String sql, String alias) {
        return "SELECT COUNT(" + ASTERISK + ") FROM (" + sql + ") "
                + (StringUtils.isNotBlank(alias) ? alias : DEFAULT_TABLE_ALIAS);
    }

    public static String toSimpleCountSql(String sql, String expr, String alias) {
        if (StringUtils.isBlank(expr)) {
            expr = ASTERISK;
        }
        return "SELECT COUNT(" + expr + ") FROM (" + sql + ") "
                + (StringUtils.isNotBlank(alias) ? alias : DEFAULT_TABLE_ALIAS);
    }

    public static SQLExpr buildCountExpr(String expr) {
        if (StringUtils.isBlank(expr) || expr.equals(ASTERISK)) {
            return COUNT_EXPR;
        }
        return SQLUtils.toSQLExpr("COUNT(" + expr + ")");
    }

    public static boolean hasAnyAggregate(List<SQLSelectItem> sqlSelectItems) {
        if (sqlSelectItems == null || sqlSelectItems.isEmpty()) {
            return false;
        }
        for (SQLSelectItem item : sqlSelectItems) {
            SQLExpr expr = item.getExpr();
            String exprString = expr != null ? expr.toString() : null;
            if (expr == null
                    || StringUtils.isEmpty(exprString)
                    || ASTERISK.equals(exprString)) {
                continue;
            }
            if (expr instanceof SQLAggregateExpr) {
                return true;
            }
            if (isAggregate(exprString)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAggregate(String expr) {
        if (expr.indexOf(OPENING_PARENTHESIS) == -1) {
            return false;
        }
        String lowerCaseExpr = expr.toLowerCase();
        Set<String> aggregateFunctions = AGGREGATE_FUNCTION_MAP.get(lowerCaseExpr.charAt(0));
        if (aggregateFunctions != null && !aggregateFunctions.isEmpty()) {
            for (String aggregateFunction : aggregateFunctions) {
                if (lowerCaseExpr.startsWith(aggregateFunction)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Map<Character, Set<String>> buildAggregateMap(String[] array) {
        if (array == null || array.length == 0) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(
                Arrays.stream(array)
                        .map(o -> o.toLowerCase() + OPENING_PARENTHESIS)
                        .collect(Collectors.groupingBy(o -> o.charAt(0), Collectors.toSet())));
    }

    private static String getDbType(SqlDialect sqlDialect) {
        if (sqlDialect == null) {
            return JdbcConstants.MYSQL;
        }
        switch (sqlDialect) {
            case DB2:
                return JdbcConstants.DB2;
            case DERBY:
                return JdbcConstants.DERBY;
            case H2:
                return JdbcConstants.H2;
            case HIVE:
                return JdbcConstants.HIVE;
            case HSQLDB:
                return JdbcConstants.HSQL;
            case INFORMIX:
                return JdbcConstants.INFORMIX;
            case MARIADB:
                return JdbcConstants.MARIADB;
            case MYSQL:
                return JdbcConstants.MYSQL;
            case ORACLE:
                return JdbcConstants.ORACLE;
            case PHOENIX:
                return JdbcConstants.PHOENIX;
            case POSTGRESQL:
                return JdbcConstants.POSTGRESQL;
            case SQLITE:
                return JdbcConstants.SQLITE;
            case SQLSERVER:
                return JdbcConstants.SQL_SERVER;
            case TIDB:
                return JdbcConstants.MYSQL;
            default:
                return JdbcConstants.MYSQL;
        }
    }

}
