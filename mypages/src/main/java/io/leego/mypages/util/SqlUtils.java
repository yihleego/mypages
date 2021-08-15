package io.leego.mypages.util;

import io.leego.mypages.sql.SQLLexer;
import io.leego.mypages.sql.SQLParser;
import io.leego.mypages.sql.SQLParser.AggregationClauseContext;
import io.leego.mypages.sql.SQLParser.ColumnReferenceContext;
import io.leego.mypages.sql.SQLParser.ConstantDefaultContext;
import io.leego.mypages.sql.SQLParser.CurrentLikeContext;
import io.leego.mypages.sql.SQLParser.DereferenceContext;
import io.leego.mypages.sql.SQLParser.ExpressionContext;
import io.leego.mypages.sql.SQLParser.HavingClauseContext;
import io.leego.mypages.sql.SQLParser.IdentifierContext;
import io.leego.mypages.sql.SQLParser.NamedExpressionContext;
import io.leego.mypages.sql.SQLParser.NamedExpressionSeqContext;
import io.leego.mypages.sql.SQLParser.OverlayContext;
import io.leego.mypages.sql.SQLParser.PredicatedContext;
import io.leego.mypages.sql.SQLParser.QueryContext;
import io.leego.mypages.sql.SQLParser.QueryOrganizationContext;
import io.leego.mypages.sql.SQLParser.QueryPrimaryContext;
import io.leego.mypages.sql.SQLParser.QueryPrimaryDefaultContext;
import io.leego.mypages.sql.SQLParser.QuerySpecificationContext;
import io.leego.mypages.sql.SQLParser.QueryTermContext;
import io.leego.mypages.sql.SQLParser.QueryTermDefaultContext;
import io.leego.mypages.sql.SQLParser.RegularQuerySpecificationContext;
import io.leego.mypages.sql.SQLParser.SelectClauseContext;
import io.leego.mypages.sql.SQLParser.SetQuantifierContext;
import io.leego.mypages.sql.SQLParser.SingleStatementContext;
import io.leego.mypages.sql.SQLParser.StarContext;
import io.leego.mypages.sql.SQLParser.StatementDefaultContext;
import io.leego.mypages.sql.SQLParser.SubqueryContext;
import io.leego.mypages.sql.SQLParser.SubstringContext;
import io.leego.mypages.sql.SQLParser.TrimContext;
import io.leego.mypages.sql.SQLParser.ValueExpressionContext;
import io.leego.mypages.sql.SQLParser.ValueExpressionDefaultContext;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Yihleego
 */
public final class SqlUtils {
    private static final String ASTERISK = "*";

    private SqlUtils() {
    }

    public static String toCountSql(String sql) {
        return toCountSql(sql, ASTERISK, false);
    }

    public static String toCountSql(String sql, boolean keepSorting) {
        return toCountSql(sql, ASTERISK, keepSorting);
    }

    public static String toCountSql(String sql, String expr) {
        return toCountSql(sql, expr, false);
    }

    public static String toCountSql(String sql, String expr, boolean keepSorting) {
        SQLParser parser = getParser(sql);
        SingleStatementContext context = parser.singleStatement();
        return convert(sql, expr, keepSorting, context);
    }

    private static String convert(String sql, String expr, boolean keepSorting, SingleStatementContext singleStatementContext) {
        if (singleStatementContext.exception != null || singleStatementContext.getChildCount() <= 0) {
            return null;
        }
        for (int i = 1; i < singleStatementContext.getChildCount(); i++) {
            if (!(singleStatementContext.getChild(i) instanceof TerminalNode) || (singleStatementContext.getChild(i) instanceof ErrorNode)) {
                return null;
            }
        }
        StatementDefaultContext statementDefaultContext = singleStatementContext.getChild(StatementDefaultContext.class, 0);
        if (statementDefaultContext == null) {
            return null;
        }
        QueryContext queryContext = statementDefaultContext.getChild(QueryContext.class, 0);
        if (queryContext == null) {
            return null;
        }
        List<Replacement> replacements = new ArrayList<>();
        if (!validateQuery(queryContext, expr, keepSorting, replacements)) {
            return null;
        }
        if (replacements.isEmpty()) {
            return sql;
        }
        StringBuilder sb = new StringBuilder(sql);
        replacements.stream()
                .sorted(Comparator.reverseOrder())
                .forEach(replacement -> {
                    if (replacement.getValue() == null) {
                        sb.delete(replacement.getBegin(), replacement.getEnd());
                    } else {
                        sb.replace(replacement.getBegin(), replacement.getEnd(), replacement.getValue());
                    }
                });
        return sb.toString();
    }

    private static boolean validateQuery(QueryContext queryContext, String expr, boolean keepSorting, List<Replacement> replacements) {
        return validateQueryTerm(queryContext.getChild(QueryTermDefaultContext.class, 0), expr, keepSorting, replacements)
                && removeQueryOrganization(queryContext.getChild(QueryOrganizationContext.class, 0), keepSorting, replacements);
    }

    private static boolean validateQueryTerm(QueryTermContext queryTermContext, String expr, boolean keepSorting, List<Replacement> replacements) {
        if (queryTermContext == null) {
            return false;
        }
        QueryPrimaryContext queryPrimaryContext = queryTermContext.getChild(QueryPrimaryDefaultContext.class, 0);
        if (queryPrimaryContext == null) {
            SubqueryContext subqueryContext = queryTermContext.getChild(SubqueryContext.class, 0);
            QueryContext innerQueryContext = subqueryContext.getChild(QueryContext.class, 0);
            return validateQuery(innerQueryContext, expr, keepSorting, replacements);
        }
        QuerySpecificationContext querySpecificationContext = queryPrimaryContext.getChild(RegularQuerySpecificationContext.class, 0);
        if (querySpecificationContext == null) {
            return false;
        }
        SelectClauseContext selectClauseContext = querySpecificationContext.getChild(SelectClauseContext.class, 0);
        AggregationClauseContext aggregationClauseContext = querySpecificationContext.getChild(AggregationClauseContext.class, 0);
        HavingClauseContext havingClauseContext = querySpecificationContext.getChild(HavingClauseContext.class, 0);
        if (selectClauseContext == null || aggregationClauseContext != null || havingClauseContext != null) {
            return false;
        }
        SetQuantifierContext setQuantifierContext = selectClauseContext.getChild(SetQuantifierContext.class, 0);
        if (setQuantifierContext != null) {
            return false;
        }
        NamedExpressionSeqContext namedExpressionSeqContext = selectClauseContext.getChild(NamedExpressionSeqContext.class, 0);
        if (namedExpressionSeqContext == null) {
            return false;
        }
        for (int i = 0; i < namedExpressionSeqContext.getChildCount(); i++) {
            ParseTree child = namedExpressionSeqContext.getChild(i);
            if (!(child instanceof NamedExpressionContext)) {
                continue;
            }
            NamedExpressionContext namedExpressionContext = (NamedExpressionContext) child;
            ExpressionContext expressionContext = namedExpressionContext.getChild(ExpressionContext.class, 0);
            PredicatedContext predicatedContext = expressionContext.getChild(PredicatedContext.class, 0);
            if (predicatedContext == null) {
                return false;
            }
            ValueExpressionContext valueExpressionContext = predicatedContext.getChild(ValueExpressionDefaultContext.class, 0);
            if (valueExpressionContext == null) {
                return false;
            }
            ParseTree primaryExpressionContext = valueExpressionContext.getChild(0);
            if (isSimpleExpression(primaryExpressionContext)) {
                continue;
            }
            return false;
        }
        if (namedExpressionSeqContext.children != null) {
            replacements.add(new Replacement(
                    namedExpressionSeqContext.getStart().getStartIndex(),
                    namedExpressionSeqContext.getStop().getStopIndex() + 1,
                    toCountFunction(expr)));
        }
        return true;
    }

    private static boolean isSimpleExpression(ParseTree primaryExpressionContext) {
        if (primaryExpressionContext instanceof IdentifierContext
                || primaryExpressionContext instanceof CurrentLikeContext
                || primaryExpressionContext instanceof ConstantDefaultContext
                || primaryExpressionContext instanceof StarContext
                || primaryExpressionContext instanceof ColumnReferenceContext
                || primaryExpressionContext instanceof SubstringContext
                || primaryExpressionContext instanceof TrimContext
                || primaryExpressionContext instanceof OverlayContext) {
            return true;
        } else if (primaryExpressionContext instanceof DereferenceContext) {
            DereferenceContext dereferenceContext = (DereferenceContext) primaryExpressionContext;
            return isSimpleExpression(dereferenceContext.base)
                    && isSimpleExpression(dereferenceContext.fieldName);
        }
        return false;
    }

    private static boolean removeQueryOrganization(QueryOrganizationContext queryOrganizationContext, boolean keepSorting, List<Replacement> replacements) {
        if (queryOrganizationContext != null && queryOrganizationContext.children != null) {
            if (keepSorting) {
                TerminalNode limit = queryOrganizationContext.getToken(SQLLexer.LIMIT, 0);
                if (limit != null) {
                    replacements.add(new Replacement(
                            limit.getSymbol().getStartIndex(),
                            queryOrganizationContext.getStop().getStopIndex() + 1));
                }
            } else {
                replacements.add(new Replacement(
                        queryOrganizationContext.getStart().getStartIndex(),
                        queryOrganizationContext.getStop().getStopIndex() + 1));
            }
        }
        return true;
    }

    private static String toCountFunction(String expr) {
        return expr == null || expr.isEmpty() || ASTERISK.equals(expr) ? "COUNT(*)" : "COUNT(" + expr + ")";
    }

    private static SQLParser getParser(String s) {
        SQLLexer lexer = new SQLLexer(CharStreams.fromString(s));
        SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.removeParseListeners();
        return parser;
    }
}
