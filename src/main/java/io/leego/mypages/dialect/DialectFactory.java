package io.leego.mypages.dialect;

import io.leego.mypages.exception.SqlDialectException;

/**
 * @author Yihleego
 */
public final class DialectFactory {
    private DialectFactory() {
    }

    public static Dialect build(SqlDialect sqlDialect) {
        if (sqlDialect == null) {
            return null;
        }
        try {
            // Uses the constructor represented by the Constructor object to initialize a new instance
            Dialect dialect = sqlDialect.getDialectClass().getConstructor().newInstance();
            if (dialect instanceof AbstractDialect) {
                ((AbstractDialect) dialect).setSqlDialect(sqlDialect);
            }
            return dialect;
        } catch (Exception e) {
            throw new SqlDialectException(e);
        }
    }

    public static Dialect build(String name) {
        SqlDialect sqlDialect = SqlDialect.get(name);
        if (sqlDialect != null) {
            return build(sqlDialect);
        }
        return null;
    }

}
