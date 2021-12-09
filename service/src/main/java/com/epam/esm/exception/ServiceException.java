package com.epam.esm.exception;

/**
 * The type Service exception.
 */
public abstract class ServiceException extends RuntimeException {
    /**
     * Instantiates a new Service exception.
     */
    protected ServiceException() {
        super();
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param message the message
     */
    protected ServiceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    protected ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param cause the cause
     */
    protected ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Gets error message key.
     *
     * @return the error message key
     */
    public abstract String getErrorMessageKey();

    /**
     * Gets code of error.
     *
     * @return the code
     */
    public abstract int getCode();
}

