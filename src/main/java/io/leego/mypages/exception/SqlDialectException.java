package io.leego.mypages.exception;

/**
 * @author Yihleego
 */
public class SqlDialectException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a <code>SqlDialectException</code> with no detail message.
     */
    public SqlDialectException() {
        super();
    }

    /**
     * Constructs a <code>SqlDialectException</code> with the specified detail message.
     * @param message detail message
     */
    public SqlDialectException(String message) {
        super(message);
    }

    /**
     * Constructs a <code>SqlDialectException</code> with the specified detail message and cause.
     * @param message detail message
     * @param cause   the cause
     */
    public SqlDialectException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a <code>SqlDialectException</code> with the cause.
     * @param cause the cause
     */
    public SqlDialectException(Throwable cause) {
        super(cause);
    }

}