package io.leego.mypages.sql;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SQLParser}.
 */
public interface SQLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SQLParser#singleStatement}.
	 * @param ctx the parse tree
	 */
	void enterSingleStatement(SQLParser.SingleStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#singleStatement}.
	 * @param ctx the parse tree
	 */
	void exitSingleStatement(SQLParser.SingleStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void enterSingleExpression(SQLParser.SingleExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#singleExpression}.
	 * @param ctx the parse tree
	 */
	void exitSingleExpression(SQLParser.SingleExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#singleTableIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterSingleTableIdentifier(SQLParser.SingleTableIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#singleTableIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitSingleTableIdentifier(SQLParser.SingleTableIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#singleMultipartIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterSingleMultipartIdentifier(SQLParser.SingleMultipartIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#singleMultipartIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitSingleMultipartIdentifier(SQLParser.SingleMultipartIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#singleFunctionIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterSingleFunctionIdentifier(SQLParser.SingleFunctionIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#singleFunctionIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitSingleFunctionIdentifier(SQLParser.SingleFunctionIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#singleDataType}.
	 * @param ctx the parse tree
	 */
	void enterSingleDataType(SQLParser.SingleDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#singleDataType}.
	 * @param ctx the parse tree
	 */
	void exitSingleDataType(SQLParser.SingleDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#singleTableSchema}.
	 * @param ctx the parse tree
	 */
	void enterSingleTableSchema(SQLParser.SingleTableSchemaContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#singleTableSchema}.
	 * @param ctx the parse tree
	 */
	void exitSingleTableSchema(SQLParser.SingleTableSchemaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statementDefault}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementDefault(SQLParser.StatementDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statementDefault}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementDefault(SQLParser.StatementDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dmlStatement}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDmlStatement(SQLParser.DmlStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dmlStatement}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDmlStatement(SQLParser.DmlStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code use}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterUse(SQLParser.UseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code use}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitUse(SQLParser.UseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createNamespace}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCreateNamespace(SQLParser.CreateNamespaceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createNamespace}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCreateNamespace(SQLParser.CreateNamespaceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setNamespaceProperties}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSetNamespaceProperties(SQLParser.SetNamespacePropertiesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setNamespaceProperties}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSetNamespaceProperties(SQLParser.SetNamespacePropertiesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setNamespaceLocation}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSetNamespaceLocation(SQLParser.SetNamespaceLocationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setNamespaceLocation}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSetNamespaceLocation(SQLParser.SetNamespaceLocationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dropNamespace}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDropNamespace(SQLParser.DropNamespaceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dropNamespace}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDropNamespace(SQLParser.DropNamespaceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showNamespaces}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowNamespaces(SQLParser.ShowNamespacesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showNamespaces}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowNamespaces(SQLParser.ShowNamespacesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCreateTable(SQLParser.CreateTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCreateTable(SQLParser.CreateTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createTableLike}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCreateTableLike(SQLParser.CreateTableLikeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createTableLike}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCreateTableLike(SQLParser.CreateTableLikeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code replaceTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterReplaceTable(SQLParser.ReplaceTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code replaceTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitReplaceTable(SQLParser.ReplaceTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code analyze}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAnalyze(SQLParser.AnalyzeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code analyze}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAnalyze(SQLParser.AnalyzeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code analyzeTables}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAnalyzeTables(SQLParser.AnalyzeTablesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code analyzeTables}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAnalyzeTables(SQLParser.AnalyzeTablesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addTableColumns}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAddTableColumns(SQLParser.AddTableColumnsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addTableColumns}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAddTableColumns(SQLParser.AddTableColumnsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code renameTableColumn}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRenameTableColumn(SQLParser.RenameTableColumnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code renameTableColumn}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRenameTableColumn(SQLParser.RenameTableColumnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dropTableColumns}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDropTableColumns(SQLParser.DropTableColumnsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dropTableColumns}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDropTableColumns(SQLParser.DropTableColumnsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code renameTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRenameTable(SQLParser.RenameTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code renameTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRenameTable(SQLParser.RenameTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setTableProperties}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSetTableProperties(SQLParser.SetTablePropertiesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setTableProperties}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSetTableProperties(SQLParser.SetTablePropertiesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unsetTableProperties}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterUnsetTableProperties(SQLParser.UnsetTablePropertiesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unsetTableProperties}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitUnsetTableProperties(SQLParser.UnsetTablePropertiesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code alterTableAlterColumn}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAlterTableAlterColumn(SQLParser.AlterTableAlterColumnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code alterTableAlterColumn}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAlterTableAlterColumn(SQLParser.AlterTableAlterColumnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code hiveChangeColumn}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterHiveChangeColumn(SQLParser.HiveChangeColumnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code hiveChangeColumn}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitHiveChangeColumn(SQLParser.HiveChangeColumnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code hiveReplaceColumns}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterHiveReplaceColumns(SQLParser.HiveReplaceColumnsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code hiveReplaceColumns}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitHiveReplaceColumns(SQLParser.HiveReplaceColumnsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setTableSerDe}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSetTableSerDe(SQLParser.SetTableSerDeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setTableSerDe}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSetTableSerDe(SQLParser.SetTableSerDeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addTablePartition}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAddTablePartition(SQLParser.AddTablePartitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addTablePartition}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAddTablePartition(SQLParser.AddTablePartitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code renameTablePartition}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRenameTablePartition(SQLParser.RenameTablePartitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code renameTablePartition}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRenameTablePartition(SQLParser.RenameTablePartitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dropTablePartitions}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDropTablePartitions(SQLParser.DropTablePartitionsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dropTablePartitions}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDropTablePartitions(SQLParser.DropTablePartitionsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setTableLocation}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSetTableLocation(SQLParser.SetTableLocationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setTableLocation}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSetTableLocation(SQLParser.SetTableLocationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code recoverPartitions}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRecoverPartitions(SQLParser.RecoverPartitionsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code recoverPartitions}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRecoverPartitions(SQLParser.RecoverPartitionsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dropTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDropTable(SQLParser.DropTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dropTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDropTable(SQLParser.DropTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dropView}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDropView(SQLParser.DropViewContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dropView}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDropView(SQLParser.DropViewContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createView}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCreateView(SQLParser.CreateViewContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createView}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCreateView(SQLParser.CreateViewContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createTempViewUsing}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCreateTempViewUsing(SQLParser.CreateTempViewUsingContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createTempViewUsing}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCreateTempViewUsing(SQLParser.CreateTempViewUsingContext ctx);
	/**
	 * Enter a parse tree produced by the {@code alterViewQuery}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAlterViewQuery(SQLParser.AlterViewQueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code alterViewQuery}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAlterViewQuery(SQLParser.AlterViewQueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createFunction}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCreateFunction(SQLParser.CreateFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createFunction}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCreateFunction(SQLParser.CreateFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dropFunction}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDropFunction(SQLParser.DropFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dropFunction}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDropFunction(SQLParser.DropFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code explain}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExplain(SQLParser.ExplainContext ctx);
	/**
	 * Exit a parse tree produced by the {@code explain}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExplain(SQLParser.ExplainContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showTables}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowTables(SQLParser.ShowTablesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showTables}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowTables(SQLParser.ShowTablesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showTableExtended}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowTableExtended(SQLParser.ShowTableExtendedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showTableExtended}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowTableExtended(SQLParser.ShowTableExtendedContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showTblProperties}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowTblProperties(SQLParser.ShowTblPropertiesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showTblProperties}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowTblProperties(SQLParser.ShowTblPropertiesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showColumns}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowColumns(SQLParser.ShowColumnsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showColumns}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowColumns(SQLParser.ShowColumnsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showViews}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowViews(SQLParser.ShowViewsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showViews}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowViews(SQLParser.ShowViewsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showPartitions}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowPartitions(SQLParser.ShowPartitionsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showPartitions}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowPartitions(SQLParser.ShowPartitionsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showFunctions}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowFunctions(SQLParser.ShowFunctionsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showFunctions}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowFunctions(SQLParser.ShowFunctionsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showCreateTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowCreateTable(SQLParser.ShowCreateTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showCreateTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowCreateTable(SQLParser.ShowCreateTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code showCurrentNamespace}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterShowCurrentNamespace(SQLParser.ShowCurrentNamespaceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code showCurrentNamespace}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitShowCurrentNamespace(SQLParser.ShowCurrentNamespaceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code describeFunction}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDescribeFunction(SQLParser.DescribeFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code describeFunction}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDescribeFunction(SQLParser.DescribeFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code describeNamespace}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDescribeNamespace(SQLParser.DescribeNamespaceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code describeNamespace}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDescribeNamespace(SQLParser.DescribeNamespaceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code describeRelation}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDescribeRelation(SQLParser.DescribeRelationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code describeRelation}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDescribeRelation(SQLParser.DescribeRelationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code describeQuery}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDescribeQuery(SQLParser.DescribeQueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code describeQuery}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDescribeQuery(SQLParser.DescribeQueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code commentNamespace}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCommentNamespace(SQLParser.CommentNamespaceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code commentNamespace}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCommentNamespace(SQLParser.CommentNamespaceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code commentTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCommentTable(SQLParser.CommentTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code commentTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCommentTable(SQLParser.CommentTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code refreshTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRefreshTable(SQLParser.RefreshTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code refreshTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRefreshTable(SQLParser.RefreshTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code refreshFunction}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRefreshFunction(SQLParser.RefreshFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code refreshFunction}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRefreshFunction(SQLParser.RefreshFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code refreshResource}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRefreshResource(SQLParser.RefreshResourceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code refreshResource}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRefreshResource(SQLParser.RefreshResourceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cacheTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCacheTable(SQLParser.CacheTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cacheTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCacheTable(SQLParser.CacheTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code uncacheTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterUncacheTable(SQLParser.UncacheTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code uncacheTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitUncacheTable(SQLParser.UncacheTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code clearCache}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterClearCache(SQLParser.ClearCacheContext ctx);
	/**
	 * Exit a parse tree produced by the {@code clearCache}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitClearCache(SQLParser.ClearCacheContext ctx);
	/**
	 * Enter a parse tree produced by the {@code loadData}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterLoadData(SQLParser.LoadDataContext ctx);
	/**
	 * Exit a parse tree produced by the {@code loadData}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitLoadData(SQLParser.LoadDataContext ctx);
	/**
	 * Enter a parse tree produced by the {@code truncateTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterTruncateTable(SQLParser.TruncateTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code truncateTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitTruncateTable(SQLParser.TruncateTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code repairTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRepairTable(SQLParser.RepairTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code repairTable}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRepairTable(SQLParser.RepairTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code manageResource}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterManageResource(SQLParser.ManageResourceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code manageResource}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitManageResource(SQLParser.ManageResourceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code failNativeCommand}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterFailNativeCommand(SQLParser.FailNativeCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code failNativeCommand}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitFailNativeCommand(SQLParser.FailNativeCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setTimeZone}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSetTimeZone(SQLParser.SetTimeZoneContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setTimeZone}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSetTimeZone(SQLParser.SetTimeZoneContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setQuotedConfiguration}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSetQuotedConfiguration(SQLParser.SetQuotedConfigurationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setQuotedConfiguration}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSetQuotedConfiguration(SQLParser.SetQuotedConfigurationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setConfiguration}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSetConfiguration(SQLParser.SetConfigurationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setConfiguration}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSetConfiguration(SQLParser.SetConfigurationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code resetQuotedConfiguration}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterResetQuotedConfiguration(SQLParser.ResetQuotedConfigurationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code resetQuotedConfiguration}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitResetQuotedConfiguration(SQLParser.ResetQuotedConfigurationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code resetConfiguration}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterResetConfiguration(SQLParser.ResetConfigurationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code resetConfiguration}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitResetConfiguration(SQLParser.ResetConfigurationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#configKey}.
	 * @param ctx the parse tree
	 */
	void enterConfigKey(SQLParser.ConfigKeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#configKey}.
	 * @param ctx the parse tree
	 */
	void exitConfigKey(SQLParser.ConfigKeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#configValue}.
	 * @param ctx the parse tree
	 */
	void enterConfigValue(SQLParser.ConfigValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#configValue}.
	 * @param ctx the parse tree
	 */
	void exitConfigValue(SQLParser.ConfigValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#unsupportedHiveNativeCommands}.
	 * @param ctx the parse tree
	 */
	void enterUnsupportedHiveNativeCommands(SQLParser.UnsupportedHiveNativeCommandsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#unsupportedHiveNativeCommands}.
	 * @param ctx the parse tree
	 */
	void exitUnsupportedHiveNativeCommands(SQLParser.UnsupportedHiveNativeCommandsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#createTableHeader}.
	 * @param ctx the parse tree
	 */
	void enterCreateTableHeader(SQLParser.CreateTableHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#createTableHeader}.
	 * @param ctx the parse tree
	 */
	void exitCreateTableHeader(SQLParser.CreateTableHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#replaceTableHeader}.
	 * @param ctx the parse tree
	 */
	void enterReplaceTableHeader(SQLParser.ReplaceTableHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#replaceTableHeader}.
	 * @param ctx the parse tree
	 */
	void exitReplaceTableHeader(SQLParser.ReplaceTableHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#bucketSpec}.
	 * @param ctx the parse tree
	 */
	void enterBucketSpec(SQLParser.BucketSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#bucketSpec}.
	 * @param ctx the parse tree
	 */
	void exitBucketSpec(SQLParser.BucketSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#skewSpec}.
	 * @param ctx the parse tree
	 */
	void enterSkewSpec(SQLParser.SkewSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#skewSpec}.
	 * @param ctx the parse tree
	 */
	void exitSkewSpec(SQLParser.SkewSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#locationSpec}.
	 * @param ctx the parse tree
	 */
	void enterLocationSpec(SQLParser.LocationSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#locationSpec}.
	 * @param ctx the parse tree
	 */
	void exitLocationSpec(SQLParser.LocationSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#commentSpec}.
	 * @param ctx the parse tree
	 */
	void enterCommentSpec(SQLParser.CommentSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#commentSpec}.
	 * @param ctx the parse tree
	 */
	void exitCommentSpec(SQLParser.CommentSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(SQLParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(SQLParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code insertOverwriteTable}
	 * labeled alternative in {@link SQLParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void enterInsertOverwriteTable(SQLParser.InsertOverwriteTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code insertOverwriteTable}
	 * labeled alternative in {@link SQLParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void exitInsertOverwriteTable(SQLParser.InsertOverwriteTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code insertIntoTable}
	 * labeled alternative in {@link SQLParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void enterInsertIntoTable(SQLParser.InsertIntoTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code insertIntoTable}
	 * labeled alternative in {@link SQLParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void exitInsertIntoTable(SQLParser.InsertIntoTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code insertOverwriteHiveDir}
	 * labeled alternative in {@link SQLParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void enterInsertOverwriteHiveDir(SQLParser.InsertOverwriteHiveDirContext ctx);
	/**
	 * Exit a parse tree produced by the {@code insertOverwriteHiveDir}
	 * labeled alternative in {@link SQLParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void exitInsertOverwriteHiveDir(SQLParser.InsertOverwriteHiveDirContext ctx);
	/**
	 * Enter a parse tree produced by the {@code insertOverwriteDir}
	 * labeled alternative in {@link SQLParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void enterInsertOverwriteDir(SQLParser.InsertOverwriteDirContext ctx);
	/**
	 * Exit a parse tree produced by the {@code insertOverwriteDir}
	 * labeled alternative in {@link SQLParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void exitInsertOverwriteDir(SQLParser.InsertOverwriteDirContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#partitionSpecLocation}.
	 * @param ctx the parse tree
	 */
	void enterPartitionSpecLocation(SQLParser.PartitionSpecLocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#partitionSpecLocation}.
	 * @param ctx the parse tree
	 */
	void exitPartitionSpecLocation(SQLParser.PartitionSpecLocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#partitionSpec}.
	 * @param ctx the parse tree
	 */
	void enterPartitionSpec(SQLParser.PartitionSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#partitionSpec}.
	 * @param ctx the parse tree
	 */
	void exitPartitionSpec(SQLParser.PartitionSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#partitionVal}.
	 * @param ctx the parse tree
	 */
	void enterPartitionVal(SQLParser.PartitionValContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#partitionVal}.
	 * @param ctx the parse tree
	 */
	void exitPartitionVal(SQLParser.PartitionValContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#namespace}.
	 * @param ctx the parse tree
	 */
	void enterNamespace(SQLParser.NamespaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#namespace}.
	 * @param ctx the parse tree
	 */
	void exitNamespace(SQLParser.NamespaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#describeFuncName}.
	 * @param ctx the parse tree
	 */
	void enterDescribeFuncName(SQLParser.DescribeFuncNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#describeFuncName}.
	 * @param ctx the parse tree
	 */
	void exitDescribeFuncName(SQLParser.DescribeFuncNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#describeColName}.
	 * @param ctx the parse tree
	 */
	void enterDescribeColName(SQLParser.DescribeColNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#describeColName}.
	 * @param ctx the parse tree
	 */
	void exitDescribeColName(SQLParser.DescribeColNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#ctes}.
	 * @param ctx the parse tree
	 */
	void enterCtes(SQLParser.CtesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#ctes}.
	 * @param ctx the parse tree
	 */
	void exitCtes(SQLParser.CtesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#namedQuery}.
	 * @param ctx the parse tree
	 */
	void enterNamedQuery(SQLParser.NamedQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#namedQuery}.
	 * @param ctx the parse tree
	 */
	void exitNamedQuery(SQLParser.NamedQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#tableProvider}.
	 * @param ctx the parse tree
	 */
	void enterTableProvider(SQLParser.TableProviderContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#tableProvider}.
	 * @param ctx the parse tree
	 */
	void exitTableProvider(SQLParser.TableProviderContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#createTableClauses}.
	 * @param ctx the parse tree
	 */
	void enterCreateTableClauses(SQLParser.CreateTableClausesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#createTableClauses}.
	 * @param ctx the parse tree
	 */
	void exitCreateTableClauses(SQLParser.CreateTableClausesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#tablePropertyList}.
	 * @param ctx the parse tree
	 */
	void enterTablePropertyList(SQLParser.TablePropertyListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#tablePropertyList}.
	 * @param ctx the parse tree
	 */
	void exitTablePropertyList(SQLParser.TablePropertyListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#tableProperty}.
	 * @param ctx the parse tree
	 */
	void enterTableProperty(SQLParser.TablePropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#tableProperty}.
	 * @param ctx the parse tree
	 */
	void exitTableProperty(SQLParser.TablePropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#tablePropertyKey}.
	 * @param ctx the parse tree
	 */
	void enterTablePropertyKey(SQLParser.TablePropertyKeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#tablePropertyKey}.
	 * @param ctx the parse tree
	 */
	void exitTablePropertyKey(SQLParser.TablePropertyKeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#tablePropertyValue}.
	 * @param ctx the parse tree
	 */
	void enterTablePropertyValue(SQLParser.TablePropertyValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#tablePropertyValue}.
	 * @param ctx the parse tree
	 */
	void exitTablePropertyValue(SQLParser.TablePropertyValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#constantList}.
	 * @param ctx the parse tree
	 */
	void enterConstantList(SQLParser.ConstantListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#constantList}.
	 * @param ctx the parse tree
	 */
	void exitConstantList(SQLParser.ConstantListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#nestedConstantList}.
	 * @param ctx the parse tree
	 */
	void enterNestedConstantList(SQLParser.NestedConstantListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#nestedConstantList}.
	 * @param ctx the parse tree
	 */
	void exitNestedConstantList(SQLParser.NestedConstantListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#createFileFormat}.
	 * @param ctx the parse tree
	 */
	void enterCreateFileFormat(SQLParser.CreateFileFormatContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#createFileFormat}.
	 * @param ctx the parse tree
	 */
	void exitCreateFileFormat(SQLParser.CreateFileFormatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tableFileFormat}
	 * labeled alternative in {@link SQLParser#fileFormat}.
	 * @param ctx the parse tree
	 */
	void enterTableFileFormat(SQLParser.TableFileFormatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableFileFormat}
	 * labeled alternative in {@link SQLParser#fileFormat}.
	 * @param ctx the parse tree
	 */
	void exitTableFileFormat(SQLParser.TableFileFormatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code genericFileFormat}
	 * labeled alternative in {@link SQLParser#fileFormat}.
	 * @param ctx the parse tree
	 */
	void enterGenericFileFormat(SQLParser.GenericFileFormatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code genericFileFormat}
	 * labeled alternative in {@link SQLParser#fileFormat}.
	 * @param ctx the parse tree
	 */
	void exitGenericFileFormat(SQLParser.GenericFileFormatContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#storageHandler}.
	 * @param ctx the parse tree
	 */
	void enterStorageHandler(SQLParser.StorageHandlerContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#storageHandler}.
	 * @param ctx the parse tree
	 */
	void exitStorageHandler(SQLParser.StorageHandlerContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#resource}.
	 * @param ctx the parse tree
	 */
	void enterResource(SQLParser.ResourceContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#resource}.
	 * @param ctx the parse tree
	 */
	void exitResource(SQLParser.ResourceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code singleInsertQuery}
	 * labeled alternative in {@link SQLParser#dmlStatementNoWith}.
	 * @param ctx the parse tree
	 */
	void enterSingleInsertQuery(SQLParser.SingleInsertQueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code singleInsertQuery}
	 * labeled alternative in {@link SQLParser#dmlStatementNoWith}.
	 * @param ctx the parse tree
	 */
	void exitSingleInsertQuery(SQLParser.SingleInsertQueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiInsertQuery}
	 * labeled alternative in {@link SQLParser#dmlStatementNoWith}.
	 * @param ctx the parse tree
	 */
	void enterMultiInsertQuery(SQLParser.MultiInsertQueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiInsertQuery}
	 * labeled alternative in {@link SQLParser#dmlStatementNoWith}.
	 * @param ctx the parse tree
	 */
	void exitMultiInsertQuery(SQLParser.MultiInsertQueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code deleteFromTable}
	 * labeled alternative in {@link SQLParser#dmlStatementNoWith}.
	 * @param ctx the parse tree
	 */
	void enterDeleteFromTable(SQLParser.DeleteFromTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code deleteFromTable}
	 * labeled alternative in {@link SQLParser#dmlStatementNoWith}.
	 * @param ctx the parse tree
	 */
	void exitDeleteFromTable(SQLParser.DeleteFromTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code updateTable}
	 * labeled alternative in {@link SQLParser#dmlStatementNoWith}.
	 * @param ctx the parse tree
	 */
	void enterUpdateTable(SQLParser.UpdateTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code updateTable}
	 * labeled alternative in {@link SQLParser#dmlStatementNoWith}.
	 * @param ctx the parse tree
	 */
	void exitUpdateTable(SQLParser.UpdateTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mergeIntoTable}
	 * labeled alternative in {@link SQLParser#dmlStatementNoWith}.
	 * @param ctx the parse tree
	 */
	void enterMergeIntoTable(SQLParser.MergeIntoTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mergeIntoTable}
	 * labeled alternative in {@link SQLParser#dmlStatementNoWith}.
	 * @param ctx the parse tree
	 */
	void exitMergeIntoTable(SQLParser.MergeIntoTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#queryOrganization}.
	 * @param ctx the parse tree
	 */
	void enterQueryOrganization(SQLParser.QueryOrganizationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#queryOrganization}.
	 * @param ctx the parse tree
	 */
	void exitQueryOrganization(SQLParser.QueryOrganizationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#multiInsertQueryBody}.
	 * @param ctx the parse tree
	 */
	void enterMultiInsertQueryBody(SQLParser.MultiInsertQueryBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#multiInsertQueryBody}.
	 * @param ctx the parse tree
	 */
	void exitMultiInsertQueryBody(SQLParser.MultiInsertQueryBodyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code queryTermDefault}
	 * labeled alternative in {@link SQLParser#queryTerm}.
	 * @param ctx the parse tree
	 */
	void enterQueryTermDefault(SQLParser.QueryTermDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code queryTermDefault}
	 * labeled alternative in {@link SQLParser#queryTerm}.
	 * @param ctx the parse tree
	 */
	void exitQueryTermDefault(SQLParser.QueryTermDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setOperation}
	 * labeled alternative in {@link SQLParser#queryTerm}.
	 * @param ctx the parse tree
	 */
	void enterSetOperation(SQLParser.SetOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setOperation}
	 * labeled alternative in {@link SQLParser#queryTerm}.
	 * @param ctx the parse tree
	 */
	void exitSetOperation(SQLParser.SetOperationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code queryPrimaryDefault}
	 * labeled alternative in {@link SQLParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void enterQueryPrimaryDefault(SQLParser.QueryPrimaryDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code queryPrimaryDefault}
	 * labeled alternative in {@link SQLParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void exitQueryPrimaryDefault(SQLParser.QueryPrimaryDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fromStmt}
	 * labeled alternative in {@link SQLParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void enterFromStmt(SQLParser.FromStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fromStmt}
	 * labeled alternative in {@link SQLParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void exitFromStmt(SQLParser.FromStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code table}
	 * labeled alternative in {@link SQLParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void enterTable(SQLParser.TableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code table}
	 * labeled alternative in {@link SQLParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void exitTable(SQLParser.TableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inlineTableDefault1}
	 * labeled alternative in {@link SQLParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void enterInlineTableDefault1(SQLParser.InlineTableDefault1Context ctx);
	/**
	 * Exit a parse tree produced by the {@code inlineTableDefault1}
	 * labeled alternative in {@link SQLParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void exitInlineTableDefault1(SQLParser.InlineTableDefault1Context ctx);
	/**
	 * Enter a parse tree produced by the {@code subquery}
	 * labeled alternative in {@link SQLParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void enterSubquery(SQLParser.SubqueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subquery}
	 * labeled alternative in {@link SQLParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void exitSubquery(SQLParser.SubqueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#sortItem}.
	 * @param ctx the parse tree
	 */
	void enterSortItem(SQLParser.SortItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#sortItem}.
	 * @param ctx the parse tree
	 */
	void exitSortItem(SQLParser.SortItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#fromStatement}.
	 * @param ctx the parse tree
	 */
	void enterFromStatement(SQLParser.FromStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#fromStatement}.
	 * @param ctx the parse tree
	 */
	void exitFromStatement(SQLParser.FromStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#fromStatementBody}.
	 * @param ctx the parse tree
	 */
	void enterFromStatementBody(SQLParser.FromStatementBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#fromStatementBody}.
	 * @param ctx the parse tree
	 */
	void exitFromStatementBody(SQLParser.FromStatementBodyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code transformQuerySpecification}
	 * labeled alternative in {@link SQLParser#querySpecification}.
	 * @param ctx the parse tree
	 */
	void enterTransformQuerySpecification(SQLParser.TransformQuerySpecificationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code transformQuerySpecification}
	 * labeled alternative in {@link SQLParser#querySpecification}.
	 * @param ctx the parse tree
	 */
	void exitTransformQuerySpecification(SQLParser.TransformQuerySpecificationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code regularQuerySpecification}
	 * labeled alternative in {@link SQLParser#querySpecification}.
	 * @param ctx the parse tree
	 */
	void enterRegularQuerySpecification(SQLParser.RegularQuerySpecificationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code regularQuerySpecification}
	 * labeled alternative in {@link SQLParser#querySpecification}.
	 * @param ctx the parse tree
	 */
	void exitRegularQuerySpecification(SQLParser.RegularQuerySpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#transformClause}.
	 * @param ctx the parse tree
	 */
	void enterTransformClause(SQLParser.TransformClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#transformClause}.
	 * @param ctx the parse tree
	 */
	void exitTransformClause(SQLParser.TransformClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#selectClause}.
	 * @param ctx the parse tree
	 */
	void enterSelectClause(SQLParser.SelectClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#selectClause}.
	 * @param ctx the parse tree
	 */
	void exitSelectClause(SQLParser.SelectClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#setClause}.
	 * @param ctx the parse tree
	 */
	void enterSetClause(SQLParser.SetClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#setClause}.
	 * @param ctx the parse tree
	 */
	void exitSetClause(SQLParser.SetClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#matchedClause}.
	 * @param ctx the parse tree
	 */
	void enterMatchedClause(SQLParser.MatchedClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#matchedClause}.
	 * @param ctx the parse tree
	 */
	void exitMatchedClause(SQLParser.MatchedClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#notMatchedClause}.
	 * @param ctx the parse tree
	 */
	void enterNotMatchedClause(SQLParser.NotMatchedClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#notMatchedClause}.
	 * @param ctx the parse tree
	 */
	void exitNotMatchedClause(SQLParser.NotMatchedClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#matchedAction}.
	 * @param ctx the parse tree
	 */
	void enterMatchedAction(SQLParser.MatchedActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#matchedAction}.
	 * @param ctx the parse tree
	 */
	void exitMatchedAction(SQLParser.MatchedActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#notMatchedAction}.
	 * @param ctx the parse tree
	 */
	void enterNotMatchedAction(SQLParser.NotMatchedActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#notMatchedAction}.
	 * @param ctx the parse tree
	 */
	void exitNotMatchedAction(SQLParser.NotMatchedActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#assignmentList}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentList(SQLParser.AssignmentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#assignmentList}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentList(SQLParser.AssignmentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(SQLParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(SQLParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(SQLParser.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(SQLParser.WhereClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#havingClause}.
	 * @param ctx the parse tree
	 */
	void enterHavingClause(SQLParser.HavingClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#havingClause}.
	 * @param ctx the parse tree
	 */
	void exitHavingClause(SQLParser.HavingClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#hint}.
	 * @param ctx the parse tree
	 */
	void enterHint(SQLParser.HintContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#hint}.
	 * @param ctx the parse tree
	 */
	void exitHint(SQLParser.HintContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#hintStatement}.
	 * @param ctx the parse tree
	 */
	void enterHintStatement(SQLParser.HintStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#hintStatement}.
	 * @param ctx the parse tree
	 */
	void exitHintStatement(SQLParser.HintStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#fromClause}.
	 * @param ctx the parse tree
	 */
	void enterFromClause(SQLParser.FromClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#fromClause}.
	 * @param ctx the parse tree
	 */
	void exitFromClause(SQLParser.FromClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#aggregationClause}.
	 * @param ctx the parse tree
	 */
	void enterAggregationClause(SQLParser.AggregationClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#aggregationClause}.
	 * @param ctx the parse tree
	 */
	void exitAggregationClause(SQLParser.AggregationClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#groupByClause}.
	 * @param ctx the parse tree
	 */
	void enterGroupByClause(SQLParser.GroupByClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#groupByClause}.
	 * @param ctx the parse tree
	 */
	void exitGroupByClause(SQLParser.GroupByClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#groupingAnalytics}.
	 * @param ctx the parse tree
	 */
	void enterGroupingAnalytics(SQLParser.GroupingAnalyticsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#groupingAnalytics}.
	 * @param ctx the parse tree
	 */
	void exitGroupingAnalytics(SQLParser.GroupingAnalyticsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#groupingElement}.
	 * @param ctx the parse tree
	 */
	void enterGroupingElement(SQLParser.GroupingElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#groupingElement}.
	 * @param ctx the parse tree
	 */
	void exitGroupingElement(SQLParser.GroupingElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#groupingSet}.
	 * @param ctx the parse tree
	 */
	void enterGroupingSet(SQLParser.GroupingSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#groupingSet}.
	 * @param ctx the parse tree
	 */
	void exitGroupingSet(SQLParser.GroupingSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#pivotClause}.
	 * @param ctx the parse tree
	 */
	void enterPivotClause(SQLParser.PivotClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#pivotClause}.
	 * @param ctx the parse tree
	 */
	void exitPivotClause(SQLParser.PivotClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#pivotColumn}.
	 * @param ctx the parse tree
	 */
	void enterPivotColumn(SQLParser.PivotColumnContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#pivotColumn}.
	 * @param ctx the parse tree
	 */
	void exitPivotColumn(SQLParser.PivotColumnContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#pivotValue}.
	 * @param ctx the parse tree
	 */
	void enterPivotValue(SQLParser.PivotValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#pivotValue}.
	 * @param ctx the parse tree
	 */
	void exitPivotValue(SQLParser.PivotValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#lateralView}.
	 * @param ctx the parse tree
	 */
	void enterLateralView(SQLParser.LateralViewContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#lateralView}.
	 * @param ctx the parse tree
	 */
	void exitLateralView(SQLParser.LateralViewContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#setQuantifier}.
	 * @param ctx the parse tree
	 */
	void enterSetQuantifier(SQLParser.SetQuantifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#setQuantifier}.
	 * @param ctx the parse tree
	 */
	void exitSetQuantifier(SQLParser.SetQuantifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#relation}.
	 * @param ctx the parse tree
	 */
	void enterRelation(SQLParser.RelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#relation}.
	 * @param ctx the parse tree
	 */
	void exitRelation(SQLParser.RelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#joinRelation}.
	 * @param ctx the parse tree
	 */
	void enterJoinRelation(SQLParser.JoinRelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#joinRelation}.
	 * @param ctx the parse tree
	 */
	void exitJoinRelation(SQLParser.JoinRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#joinType}.
	 * @param ctx the parse tree
	 */
	void enterJoinType(SQLParser.JoinTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#joinType}.
	 * @param ctx the parse tree
	 */
	void exitJoinType(SQLParser.JoinTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#joinCriteria}.
	 * @param ctx the parse tree
	 */
	void enterJoinCriteria(SQLParser.JoinCriteriaContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#joinCriteria}.
	 * @param ctx the parse tree
	 */
	void exitJoinCriteria(SQLParser.JoinCriteriaContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#sample}.
	 * @param ctx the parse tree
	 */
	void enterSample(SQLParser.SampleContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#sample}.
	 * @param ctx the parse tree
	 */
	void exitSample(SQLParser.SampleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sampleByPercentile}
	 * labeled alternative in {@link SQLParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void enterSampleByPercentile(SQLParser.SampleByPercentileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sampleByPercentile}
	 * labeled alternative in {@link SQLParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void exitSampleByPercentile(SQLParser.SampleByPercentileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sampleByRows}
	 * labeled alternative in {@link SQLParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void enterSampleByRows(SQLParser.SampleByRowsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sampleByRows}
	 * labeled alternative in {@link SQLParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void exitSampleByRows(SQLParser.SampleByRowsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sampleByBucket}
	 * labeled alternative in {@link SQLParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void enterSampleByBucket(SQLParser.SampleByBucketContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sampleByBucket}
	 * labeled alternative in {@link SQLParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void exitSampleByBucket(SQLParser.SampleByBucketContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sampleByBytes}
	 * labeled alternative in {@link SQLParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void enterSampleByBytes(SQLParser.SampleByBytesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sampleByBytes}
	 * labeled alternative in {@link SQLParser#sampleMethod}.
	 * @param ctx the parse tree
	 */
	void exitSampleByBytes(SQLParser.SampleByBytesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierList(SQLParser.IdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierList(SQLParser.IdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#identifierSeq}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierSeq(SQLParser.IdentifierSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#identifierSeq}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierSeq(SQLParser.IdentifierSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#orderedIdentifierList}.
	 * @param ctx the parse tree
	 */
	void enterOrderedIdentifierList(SQLParser.OrderedIdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#orderedIdentifierList}.
	 * @param ctx the parse tree
	 */
	void exitOrderedIdentifierList(SQLParser.OrderedIdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#orderedIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterOrderedIdentifier(SQLParser.OrderedIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#orderedIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitOrderedIdentifier(SQLParser.OrderedIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#identifierCommentList}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierCommentList(SQLParser.IdentifierCommentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#identifierCommentList}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierCommentList(SQLParser.IdentifierCommentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#identifierComment}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierComment(SQLParser.IdentifierCommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#identifierComment}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierComment(SQLParser.IdentifierCommentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tableName}
	 * labeled alternative in {@link SQLParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterTableName(SQLParser.TableNameContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableName}
	 * labeled alternative in {@link SQLParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitTableName(SQLParser.TableNameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code aliasedQuery}
	 * labeled alternative in {@link SQLParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterAliasedQuery(SQLParser.AliasedQueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code aliasedQuery}
	 * labeled alternative in {@link SQLParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitAliasedQuery(SQLParser.AliasedQueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code aliasedRelation}
	 * labeled alternative in {@link SQLParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterAliasedRelation(SQLParser.AliasedRelationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code aliasedRelation}
	 * labeled alternative in {@link SQLParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitAliasedRelation(SQLParser.AliasedRelationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inlineTableDefault2}
	 * labeled alternative in {@link SQLParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterInlineTableDefault2(SQLParser.InlineTableDefault2Context ctx);
	/**
	 * Exit a parse tree produced by the {@code inlineTableDefault2}
	 * labeled alternative in {@link SQLParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitInlineTableDefault2(SQLParser.InlineTableDefault2Context ctx);
	/**
	 * Enter a parse tree produced by the {@code tableValuedFunction}
	 * labeled alternative in {@link SQLParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterTableValuedFunction(SQLParser.TableValuedFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableValuedFunction}
	 * labeled alternative in {@link SQLParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitTableValuedFunction(SQLParser.TableValuedFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#inlineTable}.
	 * @param ctx the parse tree
	 */
	void enterInlineTable(SQLParser.InlineTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#inlineTable}.
	 * @param ctx the parse tree
	 */
	void exitInlineTable(SQLParser.InlineTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#functionTable}.
	 * @param ctx the parse tree
	 */
	void enterFunctionTable(SQLParser.FunctionTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#functionTable}.
	 * @param ctx the parse tree
	 */
	void exitFunctionTable(SQLParser.FunctionTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#tableAlias}.
	 * @param ctx the parse tree
	 */
	void enterTableAlias(SQLParser.TableAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#tableAlias}.
	 * @param ctx the parse tree
	 */
	void exitTableAlias(SQLParser.TableAliasContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rowFormatSerde}
	 * labeled alternative in {@link SQLParser#rowFormat}.
	 * @param ctx the parse tree
	 */
	void enterRowFormatSerde(SQLParser.RowFormatSerdeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rowFormatSerde}
	 * labeled alternative in {@link SQLParser#rowFormat}.
	 * @param ctx the parse tree
	 */
	void exitRowFormatSerde(SQLParser.RowFormatSerdeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rowFormatDelimited}
	 * labeled alternative in {@link SQLParser#rowFormat}.
	 * @param ctx the parse tree
	 */
	void enterRowFormatDelimited(SQLParser.RowFormatDelimitedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rowFormatDelimited}
	 * labeled alternative in {@link SQLParser#rowFormat}.
	 * @param ctx the parse tree
	 */
	void exitRowFormatDelimited(SQLParser.RowFormatDelimitedContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#multipartIdentifierList}.
	 * @param ctx the parse tree
	 */
	void enterMultipartIdentifierList(SQLParser.MultipartIdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#multipartIdentifierList}.
	 * @param ctx the parse tree
	 */
	void exitMultipartIdentifierList(SQLParser.MultipartIdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#multipartIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterMultipartIdentifier(SQLParser.MultipartIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#multipartIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitMultipartIdentifier(SQLParser.MultipartIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#tableIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterTableIdentifier(SQLParser.TableIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#tableIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitTableIdentifier(SQLParser.TableIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#functionIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterFunctionIdentifier(SQLParser.FunctionIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#functionIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitFunctionIdentifier(SQLParser.FunctionIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#namedExpression}.
	 * @param ctx the parse tree
	 */
	void enterNamedExpression(SQLParser.NamedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#namedExpression}.
	 * @param ctx the parse tree
	 */
	void exitNamedExpression(SQLParser.NamedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#namedExpressionSeq}.
	 * @param ctx the parse tree
	 */
	void enterNamedExpressionSeq(SQLParser.NamedExpressionSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#namedExpressionSeq}.
	 * @param ctx the parse tree
	 */
	void exitNamedExpressionSeq(SQLParser.NamedExpressionSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#partitionFieldList}.
	 * @param ctx the parse tree
	 */
	void enterPartitionFieldList(SQLParser.PartitionFieldListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#partitionFieldList}.
	 * @param ctx the parse tree
	 */
	void exitPartitionFieldList(SQLParser.PartitionFieldListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code partitionTransform}
	 * labeled alternative in {@link SQLParser#partitionField}.
	 * @param ctx the parse tree
	 */
	void enterPartitionTransform(SQLParser.PartitionTransformContext ctx);
	/**
	 * Exit a parse tree produced by the {@code partitionTransform}
	 * labeled alternative in {@link SQLParser#partitionField}.
	 * @param ctx the parse tree
	 */
	void exitPartitionTransform(SQLParser.PartitionTransformContext ctx);
	/**
	 * Enter a parse tree produced by the {@code partitionColumn}
	 * labeled alternative in {@link SQLParser#partitionField}.
	 * @param ctx the parse tree
	 */
	void enterPartitionColumn(SQLParser.PartitionColumnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code partitionColumn}
	 * labeled alternative in {@link SQLParser#partitionField}.
	 * @param ctx the parse tree
	 */
	void exitPartitionColumn(SQLParser.PartitionColumnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identityTransform}
	 * labeled alternative in {@link SQLParser#transform}.
	 * @param ctx the parse tree
	 */
	void enterIdentityTransform(SQLParser.IdentityTransformContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identityTransform}
	 * labeled alternative in {@link SQLParser#transform}.
	 * @param ctx the parse tree
	 */
	void exitIdentityTransform(SQLParser.IdentityTransformContext ctx);
	/**
	 * Enter a parse tree produced by the {@code applyTransform}
	 * labeled alternative in {@link SQLParser#transform}.
	 * @param ctx the parse tree
	 */
	void enterApplyTransform(SQLParser.ApplyTransformContext ctx);
	/**
	 * Exit a parse tree produced by the {@code applyTransform}
	 * labeled alternative in {@link SQLParser#transform}.
	 * @param ctx the parse tree
	 */
	void exitApplyTransform(SQLParser.ApplyTransformContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#transformArgument}.
	 * @param ctx the parse tree
	 */
	void enterTransformArgument(SQLParser.TransformArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#transformArgument}.
	 * @param ctx the parse tree
	 */
	void exitTransformArgument(SQLParser.TransformArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(SQLParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(SQLParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#expressionSeq}.
	 * @param ctx the parse tree
	 */
	void enterExpressionSeq(SQLParser.ExpressionSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#expressionSeq}.
	 * @param ctx the parse tree
	 */
	void exitExpressionSeq(SQLParser.ExpressionSeqContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalNot}
	 * labeled alternative in {@link SQLParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalNot(SQLParser.LogicalNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalNot}
	 * labeled alternative in {@link SQLParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalNot(SQLParser.LogicalNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code predicated}
	 * labeled alternative in {@link SQLParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterPredicated(SQLParser.PredicatedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code predicated}
	 * labeled alternative in {@link SQLParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitPredicated(SQLParser.PredicatedContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exists}
	 * labeled alternative in {@link SQLParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterExists(SQLParser.ExistsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exists}
	 * labeled alternative in {@link SQLParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitExists(SQLParser.ExistsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalBinary}
	 * labeled alternative in {@link SQLParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalBinary(SQLParser.LogicalBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalBinary}
	 * labeled alternative in {@link SQLParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalBinary(SQLParser.LogicalBinaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicate(SQLParser.PredicateContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicate(SQLParser.PredicateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valueExpressionDefault}
	 * labeled alternative in {@link SQLParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterValueExpressionDefault(SQLParser.ValueExpressionDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valueExpressionDefault}
	 * labeled alternative in {@link SQLParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitValueExpressionDefault(SQLParser.ValueExpressionDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code comparison}
	 * labeled alternative in {@link SQLParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterComparison(SQLParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by the {@code comparison}
	 * labeled alternative in {@link SQLParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitComparison(SQLParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arithmeticBinary}
	 * labeled alternative in {@link SQLParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticBinary(SQLParser.ArithmeticBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arithmeticBinary}
	 * labeled alternative in {@link SQLParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticBinary(SQLParser.ArithmeticBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arithmeticUnary}
	 * labeled alternative in {@link SQLParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticUnary(SQLParser.ArithmeticUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arithmeticUnary}
	 * labeled alternative in {@link SQLParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticUnary(SQLParser.ArithmeticUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code struct}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterStruct(SQLParser.StructContext ctx);
	/**
	 * Exit a parse tree produced by the {@code struct}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitStruct(SQLParser.StructContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dereference}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterDereference(SQLParser.DereferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dereference}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitDereference(SQLParser.DereferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleCase}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSimpleCase(SQLParser.SimpleCaseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleCase}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSimpleCase(SQLParser.SimpleCaseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code currentLike}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterCurrentLike(SQLParser.CurrentLikeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code currentLike}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitCurrentLike(SQLParser.CurrentLikeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code columnReference}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterColumnReference(SQLParser.ColumnReferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code columnReference}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitColumnReference(SQLParser.ColumnReferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rowConstructor}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterRowConstructor(SQLParser.RowConstructorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rowConstructor}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitRowConstructor(SQLParser.RowConstructorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code last}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterLast(SQLParser.LastContext ctx);
	/**
	 * Exit a parse tree produced by the {@code last}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitLast(SQLParser.LastContext ctx);
	/**
	 * Enter a parse tree produced by the {@code star}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterStar(SQLParser.StarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code star}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitStar(SQLParser.StarContext ctx);
	/**
	 * Enter a parse tree produced by the {@code overlay}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterOverlay(SQLParser.OverlayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code overlay}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitOverlay(SQLParser.OverlayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subscript}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubscript(SQLParser.SubscriptContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subscript}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubscript(SQLParser.SubscriptContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subqueryExpression}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubqueryExpression(SQLParser.SubqueryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subqueryExpression}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubqueryExpression(SQLParser.SubqueryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code substring}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubstring(SQLParser.SubstringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code substring}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubstring(SQLParser.SubstringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cast}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterCast(SQLParser.CastContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cast}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitCast(SQLParser.CastContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constantDefault}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterConstantDefault(SQLParser.ConstantDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constantDefault}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitConstantDefault(SQLParser.ConstantDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lambda}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambda(SQLParser.LambdaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lambda}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambda(SQLParser.LambdaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenthesizedExpression}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExpression(SQLParser.ParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenthesizedExpression}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExpression(SQLParser.ParenthesizedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code extract}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterExtract(SQLParser.ExtractContext ctx);
	/**
	 * Exit a parse tree produced by the {@code extract}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitExtract(SQLParser.ExtractContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trim}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterTrim(SQLParser.TrimContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trim}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitTrim(SQLParser.TrimContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionCall}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(SQLParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionCall}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(SQLParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code searchedCase}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSearchedCase(SQLParser.SearchedCaseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code searchedCase}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSearchedCase(SQLParser.SearchedCaseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code position}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPosition(SQLParser.PositionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code position}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPosition(SQLParser.PositionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code first}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterFirst(SQLParser.FirstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code first}
	 * labeled alternative in {@link SQLParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitFirst(SQLParser.FirstContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#limitExpression}.
	 * @param ctx the parse tree
	 */
	void enterLimitExpression(SQLParser.LimitExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#limitExpression}.
	 * @param ctx the parse tree
	 */
	void exitLimitExpression(SQLParser.LimitExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullLiteral}
	 * labeled alternative in {@link SQLParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterNullLiteral(SQLParser.NullLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullLiteral}
	 * labeled alternative in {@link SQLParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitNullLiteral(SQLParser.NullLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intervalLiteral}
	 * labeled alternative in {@link SQLParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterIntervalLiteral(SQLParser.IntervalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intervalLiteral}
	 * labeled alternative in {@link SQLParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitIntervalLiteral(SQLParser.IntervalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code typeConstructor}
	 * labeled alternative in {@link SQLParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterTypeConstructor(SQLParser.TypeConstructorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code typeConstructor}
	 * labeled alternative in {@link SQLParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitTypeConstructor(SQLParser.TypeConstructorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numericLiteral}
	 * labeled alternative in {@link SQLParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteral(SQLParser.NumericLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numericLiteral}
	 * labeled alternative in {@link SQLParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteral(SQLParser.NumericLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleanLiteral}
	 * labeled alternative in {@link SQLParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(SQLParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleanLiteral}
	 * labeled alternative in {@link SQLParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(SQLParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link SQLParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(SQLParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link SQLParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(SQLParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void enterComparisonOperator(SQLParser.ComparisonOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void exitComparisonOperator(SQLParser.ComparisonOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#arithmeticOperator}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticOperator(SQLParser.ArithmeticOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#arithmeticOperator}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticOperator(SQLParser.ArithmeticOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#predicateOperator}.
	 * @param ctx the parse tree
	 */
	void enterPredicateOperator(SQLParser.PredicateOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#predicateOperator}.
	 * @param ctx the parse tree
	 */
	void exitPredicateOperator(SQLParser.PredicateOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#booleanValue}.
	 * @param ctx the parse tree
	 */
	void enterBooleanValue(SQLParser.BooleanValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#booleanValue}.
	 * @param ctx the parse tree
	 */
	void exitBooleanValue(SQLParser.BooleanValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#interval}.
	 * @param ctx the parse tree
	 */
	void enterInterval(SQLParser.IntervalContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#interval}.
	 * @param ctx the parse tree
	 */
	void exitInterval(SQLParser.IntervalContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#errorCapturingMultiUnitsInterval}.
	 * @param ctx the parse tree
	 */
	void enterErrorCapturingMultiUnitsInterval(SQLParser.ErrorCapturingMultiUnitsIntervalContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#errorCapturingMultiUnitsInterval}.
	 * @param ctx the parse tree
	 */
	void exitErrorCapturingMultiUnitsInterval(SQLParser.ErrorCapturingMultiUnitsIntervalContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#multiUnitsInterval}.
	 * @param ctx the parse tree
	 */
	void enterMultiUnitsInterval(SQLParser.MultiUnitsIntervalContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#multiUnitsInterval}.
	 * @param ctx the parse tree
	 */
	void exitMultiUnitsInterval(SQLParser.MultiUnitsIntervalContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#errorCapturingUnitToUnitInterval}.
	 * @param ctx the parse tree
	 */
	void enterErrorCapturingUnitToUnitInterval(SQLParser.ErrorCapturingUnitToUnitIntervalContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#errorCapturingUnitToUnitInterval}.
	 * @param ctx the parse tree
	 */
	void exitErrorCapturingUnitToUnitInterval(SQLParser.ErrorCapturingUnitToUnitIntervalContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#unitToUnitInterval}.
	 * @param ctx the parse tree
	 */
	void enterUnitToUnitInterval(SQLParser.UnitToUnitIntervalContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#unitToUnitInterval}.
	 * @param ctx the parse tree
	 */
	void exitUnitToUnitInterval(SQLParser.UnitToUnitIntervalContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#intervalValue}.
	 * @param ctx the parse tree
	 */
	void enterIntervalValue(SQLParser.IntervalValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#intervalValue}.
	 * @param ctx the parse tree
	 */
	void exitIntervalValue(SQLParser.IntervalValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#colPosition}.
	 * @param ctx the parse tree
	 */
	void enterColPosition(SQLParser.ColPositionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#colPosition}.
	 * @param ctx the parse tree
	 */
	void exitColPosition(SQLParser.ColPositionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code complexDataType}
	 * labeled alternative in {@link SQLParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterComplexDataType(SQLParser.ComplexDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code complexDataType}
	 * labeled alternative in {@link SQLParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitComplexDataType(SQLParser.ComplexDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code yearMonthIntervalDataType}
	 * labeled alternative in {@link SQLParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterYearMonthIntervalDataType(SQLParser.YearMonthIntervalDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code yearMonthIntervalDataType}
	 * labeled alternative in {@link SQLParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitYearMonthIntervalDataType(SQLParser.YearMonthIntervalDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dayTimeIntervalDataType}
	 * labeled alternative in {@link SQLParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterDayTimeIntervalDataType(SQLParser.DayTimeIntervalDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dayTimeIntervalDataType}
	 * labeled alternative in {@link SQLParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitDayTimeIntervalDataType(SQLParser.DayTimeIntervalDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primitiveDataType}
	 * labeled alternative in {@link SQLParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveDataType(SQLParser.PrimitiveDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primitiveDataType}
	 * labeled alternative in {@link SQLParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveDataType(SQLParser.PrimitiveDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#qualifiedColTypeWithPositionList}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedColTypeWithPositionList(SQLParser.QualifiedColTypeWithPositionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#qualifiedColTypeWithPositionList}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedColTypeWithPositionList(SQLParser.QualifiedColTypeWithPositionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#qualifiedColTypeWithPosition}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedColTypeWithPosition(SQLParser.QualifiedColTypeWithPositionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#qualifiedColTypeWithPosition}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedColTypeWithPosition(SQLParser.QualifiedColTypeWithPositionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#colTypeList}.
	 * @param ctx the parse tree
	 */
	void enterColTypeList(SQLParser.ColTypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#colTypeList}.
	 * @param ctx the parse tree
	 */
	void exitColTypeList(SQLParser.ColTypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#colType}.
	 * @param ctx the parse tree
	 */
	void enterColType(SQLParser.ColTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#colType}.
	 * @param ctx the parse tree
	 */
	void exitColType(SQLParser.ColTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#complexColTypeList}.
	 * @param ctx the parse tree
	 */
	void enterComplexColTypeList(SQLParser.ComplexColTypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#complexColTypeList}.
	 * @param ctx the parse tree
	 */
	void exitComplexColTypeList(SQLParser.ComplexColTypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#complexColType}.
	 * @param ctx the parse tree
	 */
	void enterComplexColType(SQLParser.ComplexColTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#complexColType}.
	 * @param ctx the parse tree
	 */
	void exitComplexColType(SQLParser.ComplexColTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#whenClause}.
	 * @param ctx the parse tree
	 */
	void enterWhenClause(SQLParser.WhenClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#whenClause}.
	 * @param ctx the parse tree
	 */
	void exitWhenClause(SQLParser.WhenClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#windowClause}.
	 * @param ctx the parse tree
	 */
	void enterWindowClause(SQLParser.WindowClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#windowClause}.
	 * @param ctx the parse tree
	 */
	void exitWindowClause(SQLParser.WindowClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#namedWindow}.
	 * @param ctx the parse tree
	 */
	void enterNamedWindow(SQLParser.NamedWindowContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#namedWindow}.
	 * @param ctx the parse tree
	 */
	void exitNamedWindow(SQLParser.NamedWindowContext ctx);
	/**
	 * Enter a parse tree produced by the {@code windowRef}
	 * labeled alternative in {@link SQLParser#windowSpec}.
	 * @param ctx the parse tree
	 */
	void enterWindowRef(SQLParser.WindowRefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code windowRef}
	 * labeled alternative in {@link SQLParser#windowSpec}.
	 * @param ctx the parse tree
	 */
	void exitWindowRef(SQLParser.WindowRefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code windowDef}
	 * labeled alternative in {@link SQLParser#windowSpec}.
	 * @param ctx the parse tree
	 */
	void enterWindowDef(SQLParser.WindowDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code windowDef}
	 * labeled alternative in {@link SQLParser#windowSpec}.
	 * @param ctx the parse tree
	 */
	void exitWindowDef(SQLParser.WindowDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#windowFrame}.
	 * @param ctx the parse tree
	 */
	void enterWindowFrame(SQLParser.WindowFrameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#windowFrame}.
	 * @param ctx the parse tree
	 */
	void exitWindowFrame(SQLParser.WindowFrameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#frameBound}.
	 * @param ctx the parse tree
	 */
	void enterFrameBound(SQLParser.FrameBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#frameBound}.
	 * @param ctx the parse tree
	 */
	void exitFrameBound(SQLParser.FrameBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedNameList(SQLParser.QualifiedNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedNameList(SQLParser.QualifiedNameListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#functionName}.
	 * @param ctx the parse tree
	 */
	void enterFunctionName(SQLParser.FunctionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#functionName}.
	 * @param ctx the parse tree
	 */
	void exitFunctionName(SQLParser.FunctionNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(SQLParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(SQLParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#errorCapturingIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterErrorCapturingIdentifier(SQLParser.ErrorCapturingIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#errorCapturingIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitErrorCapturingIdentifier(SQLParser.ErrorCapturingIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code errorIdent}
	 * labeled alternative in {@link SQLParser#errorCapturingIdentifierExtra}.
	 * @param ctx the parse tree
	 */
	void enterErrorIdent(SQLParser.ErrorIdentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code errorIdent}
	 * labeled alternative in {@link SQLParser#errorCapturingIdentifierExtra}.
	 * @param ctx the parse tree
	 */
	void exitErrorIdent(SQLParser.ErrorIdentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code realIdent}
	 * labeled alternative in {@link SQLParser#errorCapturingIdentifierExtra}.
	 * @param ctx the parse tree
	 */
	void enterRealIdent(SQLParser.RealIdentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code realIdent}
	 * labeled alternative in {@link SQLParser#errorCapturingIdentifierExtra}.
	 * @param ctx the parse tree
	 */
	void exitRealIdent(SQLParser.RealIdentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(SQLParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(SQLParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unquotedIdentifier}
	 * labeled alternative in {@link SQLParser#strictIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterUnquotedIdentifier(SQLParser.UnquotedIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unquotedIdentifier}
	 * labeled alternative in {@link SQLParser#strictIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitUnquotedIdentifier(SQLParser.UnquotedIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code quotedIdentifierAlternative}
	 * labeled alternative in {@link SQLParser#strictIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterQuotedIdentifierAlternative(SQLParser.QuotedIdentifierAlternativeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code quotedIdentifierAlternative}
	 * labeled alternative in {@link SQLParser#strictIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitQuotedIdentifierAlternative(SQLParser.QuotedIdentifierAlternativeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#quotedIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterQuotedIdentifier(SQLParser.QuotedIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#quotedIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitQuotedIdentifier(SQLParser.QuotedIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exponentLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void enterExponentLiteral(SQLParser.ExponentLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exponentLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void exitExponentLiteral(SQLParser.ExponentLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code decimalLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void enterDecimalLiteral(SQLParser.DecimalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code decimalLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void exitDecimalLiteral(SQLParser.DecimalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code legacyDecimalLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void enterLegacyDecimalLiteral(SQLParser.LegacyDecimalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code legacyDecimalLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void exitLegacyDecimalLiteral(SQLParser.LegacyDecimalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code integerLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(SQLParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code integerLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(SQLParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bigIntLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void enterBigIntLiteral(SQLParser.BigIntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bigIntLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void exitBigIntLiteral(SQLParser.BigIntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code smallIntLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void enterSmallIntLiteral(SQLParser.SmallIntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code smallIntLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void exitSmallIntLiteral(SQLParser.SmallIntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tinyIntLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void enterTinyIntLiteral(SQLParser.TinyIntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tinyIntLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void exitTinyIntLiteral(SQLParser.TinyIntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doubleLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void enterDoubleLiteral(SQLParser.DoubleLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doubleLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void exitDoubleLiteral(SQLParser.DoubleLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code floatLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(SQLParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code floatLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(SQLParser.FloatLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bigDecimalLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void enterBigDecimalLiteral(SQLParser.BigDecimalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bigDecimalLiteral}
	 * labeled alternative in {@link SQLParser#number}.
	 * @param ctx the parse tree
	 */
	void exitBigDecimalLiteral(SQLParser.BigDecimalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#alterColumnAction}.
	 * @param ctx the parse tree
	 */
	void enterAlterColumnAction(SQLParser.AlterColumnActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#alterColumnAction}.
	 * @param ctx the parse tree
	 */
	void exitAlterColumnAction(SQLParser.AlterColumnActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#ansiNonReserved}.
	 * @param ctx the parse tree
	 */
	void enterAnsiNonReserved(SQLParser.AnsiNonReservedContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#ansiNonReserved}.
	 * @param ctx the parse tree
	 */
	void exitAnsiNonReserved(SQLParser.AnsiNonReservedContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#strictNonReserved}.
	 * @param ctx the parse tree
	 */
	void enterStrictNonReserved(SQLParser.StrictNonReservedContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#strictNonReserved}.
	 * @param ctx the parse tree
	 */
	void exitStrictNonReserved(SQLParser.StrictNonReservedContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#nonReserved}.
	 * @param ctx the parse tree
	 */
	void enterNonReserved(SQLParser.NonReservedContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#nonReserved}.
	 * @param ctx the parse tree
	 */
	void exitNonReserved(SQLParser.NonReservedContext ctx);
}