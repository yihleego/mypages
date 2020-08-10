package io.leego.mypages.dialect;

import io.leego.mypages.exception.SqlDialectException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yihleego
 */
public enum SqlDialect {
    DB2("DB2", DB2Dialect.class),
    DERBY("Derby", DerbyDialect.class),
    H2("H2", PostgreSQLDialect.class),
    HIVE("Apache Hive", DB2Dialect.class),
    HSQLDB("HSQLDB", PostgreSQLDialect.class),
    INFORMIX("Informix", InformixDialect.class),
    MARIADB("MariaDB", MySQLDialect.class),
    MYSQL("MySQL", MySQLDialect.class),
    ORACLE("Oracle", OracleDialect.class),
    PHOENIX("Phoenix", PostgreSQLDialect.class),
    POSTGRESQL("PostgreSQL", PostgreSQLDialect.class),
    SQLITE("SQLite", MySQLDialect.class),
    SQLSERVER("SQL Server", DerbyDialect.class),
    TIDB("TiDB", MySQLDialect.class),
    ;

    private final String name;
    private final Class<? extends AbstractDialect> dialectClass;

    SqlDialect(String name, Class<? extends AbstractDialect> dialectClass) {
        this.name = name;
        this.dialectClass = dialectClass;
    }

    public String getName() {
        return name;
    }

    public Class<? extends Dialect> getDialectClass() {
        return dialectClass;
    }

    public Dialect newDialect() {
        try {
            // Uses the constructor represented by the Constructor object to initialize a new instance
            AbstractDialect dialect = dialectClass.getConstructor().newInstance();
            dialect.setSqlDialect(this);
            return dialect;
        } catch (Exception e) {
            throw new SqlDialectException(e);
        }
    }

    private static final Map<String, SqlDialect> map;

    static {
        Map<String, SqlDialect> store = new HashMap<>(32);
        for (SqlDialect e : values()) {
            String lowerCaseDialectName = e.getName().toLowerCase();
            String lowerCaseEnumName = e.name().toLowerCase();
            store.put(lowerCaseDialectName, e);
            if (!lowerCaseDialectName.equals(lowerCaseEnumName)) {
                store.putIfAbsent(e.name().toLowerCase(), e);
            }
        }
        map = Collections.unmodifiableMap(store);
    }

    public static SqlDialect get(String name) {
        if (name == null) {
            return null;
        }
        return map.get(name);
    }

}
