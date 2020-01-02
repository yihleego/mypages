package io.leego.mypages.dialect;

import io.leego.mypages.exception.SqlDialectException;

/**
 * @author Yihleego
 */
public enum SqlDialect {
    MYSQL("MySQL", MySQLDialect.class),
    MARIADB("MariaDB", MySQLDialect.class),
    SQLITE("SQLite", MySQLDialect.class),
    TIDB("TiDB", MySQLDialect.class),

    POSTGRESQL("PostgreSQL", PostgreSQLDialect.class),
    HSQLDB("HSQLDB", PostgreSQLDialect.class),
    H2("H2", PostgreSQLDialect.class),
    PHOENIX("Phoenix", PostgreSQLDialect.class),

    ORACLE("Oracle", OracleDialect.class),
    DB2("DB2", DB2Dialect.class),
    INFORMIX("Informix", InformixDialect.class),
    DERBY("Derby", DerbyDialect.class),
    SQL_SERVER("SQL Server", DerbyDialect.class),
    HIVE("Apache Hive", HiveDialect.class);

    private final String name;
    private final Class<? extends Dialect> dialectClass;

    SqlDialect(String name, Class<? extends Dialect> dialectClass) {
        this.name = name;
        this.dialectClass = dialectClass;
    }

    public String getName() {
        return name;
    }

    public Class<? extends Dialect> getDialectClass() {
        return dialectClass;
    }

    public Dialect getDialect() {
        try {
            // Uses the constructor represented by the Constructor object to initialize a new instance
            return dialectClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new SqlDialectException(e);
        }
    }

}
