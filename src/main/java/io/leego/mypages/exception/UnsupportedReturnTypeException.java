package io.leego.mypages.exception;

/**
 * @author Yihleego
 */
public class UnsupportedReturnTypeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a <code>UnsupportedReturnTypeException</code> with no detail message.
     */
    public UnsupportedReturnTypeException() {
        super();
    }

    /**
     * Constructs a <code>UnsupportedReturnTypeException</code> with the specified detail message.
     * @param message detail message
     */
    public UnsupportedReturnTypeException(String message) {
        super(message);
    }

    /**
     * Constructs a <code>UnsupportedReturnTypeException</code> with the specified detail message and cause.
     * @param message detail message
     * @param cause   the cause
     */
    public UnsupportedReturnTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a <code>UnsupportedReturnTypeException</code> with the cause.
     * @param cause the cause
     */
    public UnsupportedReturnTypeException(Throwable cause) {
        super(cause);
    }

}