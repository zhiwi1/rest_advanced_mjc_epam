package com.epam.esm.exception;

import lombok.Getter;

/**
 * The type Resource not found exception.
 */
@Getter
public class ResourceNotFoundException extends ServiceException {
    private final Object[] id;

    /**
     * Instantiates a new Resource not found exception.
     *
     * @param id the id
     */
    public ResourceNotFoundException(Object... id) {
        super();
        this.id = id;
    }

    @Override
    public String getErrorMessageKey() {
        return ExceptionMessageKey.RESOURCE_NOT_FOUND_KEY;
    }

    @Override
    public int getCode() {
        return ExceptionCode.RESOURCE_NOT_FOUND_BAD_REQUEST;
    }
}
