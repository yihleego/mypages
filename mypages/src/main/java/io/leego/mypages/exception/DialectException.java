package io.leego.mypages.exception;

/**
 * @author Yihleego
 */
public class DialectException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a <code>DialectException</code> with no detail message.
     */
    public DialectException() {
        super();
    }

    /**
     * Constructs a <code>DialectException</code> with the specified detail message.
     * @param message detail message
     */
    public DialectException(String message) {
        super(message);
    }

    /**
     * Constructs a <code>DialectException</code> with the specified detail message and cause.
     * @param message detail message
     * @param cause   the cause
     */
    public DialectException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a <code>DialectException</code> with the cause.
     * @param cause the cause
     */
    public DialectException(Throwable cause) {
        super(cause);
    }

}