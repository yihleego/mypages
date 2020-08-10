package io.leego.mypages.exception;

/**
 * @author Yihleego
 */
public class PaginationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a <code>PaginationException</code> with no detail message.
     */
    public PaginationException() {
        super();
    }

    /**
     * Constructs a <code>PaginationException</code> with the specified detail message.
     * @param message detail message
     */
    public PaginationException(String message) {
        super(message);
    }

    /**
     * Constructs a <code>PaginationException</code> with the specified detail message and cause.
     * @param message detail message
     * @param cause   the cause
     */
    public PaginationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a <code>PaginationException</code> with the cause.
     * @param cause the cause
     */
    public PaginationException(Throwable cause) {
        super(cause);
    }

}