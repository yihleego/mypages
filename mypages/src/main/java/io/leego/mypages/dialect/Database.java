package io.leego.mypages.dialect;

import io.leego.mypages.exception.DialectException;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Yihleego
 */
public enum Database {
    DB2("DB2", DB2Dialect.class),
    DERBY("Apache Derby", DerbyDialect.class),
    // TODO FIREBIRD("Firebird", FirebirdDialect.class),
    H2("H2", H2Dialect.class),
    // TODO HANA("SAP HANA Database", HANADialect.class),
    HIVE("Apache Hive", HiveDialect.class),
    HSQLDB("HSQL Database Engine", HSQLDBDialect.class),
    INFORMIX("Informix Dynamic Server", InformixDialect.class),
    // TODO JTDS("JTDS", JTDSDialect.class),
    MARIADB("MariaDB", MariaDBDialect.class),
    MYSQL("MySQL", MySQLDialect.class),
    ORACLE("Oracle", OracleDialect.class),
    PHOENIX("Apache Phoenix", PhoenixDialect.class),
    POSTGRESQL("PostgreSQL", PostgreSQLDialect.class),
    // TODO REDSHIFT("Redshift", RedshiftDialect.class),
    SQLITE("SQLite", SQLiteDialect.class),
    SQLSERVER("Microsoft SQL Server", SQLServerDialect.class),
    // TODO TERADATA("Teradata", TeradataDialect.class),
    TIDB("TiDB", TiDBDialect.class),
    ;

    private final String name;
    private final Class<? extends Dialect> dialectClass;

    Database(String name, Class<? extends Dialect> dialectClass) {
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
            // Use the constructor represented by the Constructor object to initialize a new instance
            return dialectClass.getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new DialectException("Unsupported database type '" + this.getName() + "'", e);
        }
    }

    private static final Map<String, Database> map;

    static {
        Map<String, Database> store = new HashMap<>(32);
        for (Database e : values()) {
            String enumName = e.name().toLowerCase(Locale.ENGLISH);
            String databaseName = e.getName().toLowerCase(Locale.ENGLISH);
            store.put(enumName, e);
            if (!enumName.equals(databaseName)) {
                store.putIfAbsent(enumName, e);
            }
        }
        map = Collections.unmodifiableMap(store);
    }

    public static Database fromName(String name) {
        if (name == null) {
            return null;
        }
        return map.get(name.toLowerCase(Locale.ENGLISH));
    }

    public static Database fromJdbcUrl(String url) {
        if (url == null || !url.startsWith("jdbc:")) {
            return null;
        }
        int begin = "jdbc:".length();
        int end = url.indexOf(':', begin);
        if (end < 0) {
            return null;
        }
        return fromName(url.substring(begin, end));
    }

    public static Database fromDataSource(DataSource dataSource) {
        if (dataSource == null) {
            return null;
        }
        try {
            return Database.fromJdbcUrl(dataSource.getConnection().getMetaData().getURL());
        } catch (SQLException ignored) {
            return null;
        }
    }
}
