package io.leego.mypages.dialect;

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
    UNKNOWN("unknown", null),
    ;

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
            return UNKNOWN;
        }
        return map.getOrDefault(name, UNKNOWN);
    }

}
