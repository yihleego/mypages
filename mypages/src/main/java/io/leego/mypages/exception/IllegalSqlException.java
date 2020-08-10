package io.leego.mypages.exception;

/**
 * @author Yihleego
 */
public class IllegalSqlException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an <code>IllegalSqlException</code> with no detail message.
     */
    public IllegalSqlException() {
        super();
    }

    /**
     * Constructs an <code>IllegalSqlException</code> with the specified detail message.
     * @param message detail message
     */
    public IllegalSqlException(String message) {
        super(message);
    }

    /**
     * Constructs an <code>IllegalSqlException</code> with the specified detail message and cause.
     * @param message detail message
     * @param cause   the cause
     */
    public IllegalSqlException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an <code>IllegalSqlException</code> with the cause.
     * @param cause the cause
     */
    public IllegalSqlException(Throwable cause) {
        super(cause);
    }

}