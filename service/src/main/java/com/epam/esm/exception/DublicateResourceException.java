package com.epam.esm.exception;

import lombok.Getter;

/**
 * The type Dublicate resource exception.
 */
@Getter
public class DublicateResourceException extends ServiceException {

    /**
     * Instantiates a new Dublicate resource exception.
     *
     * @param entityName the entity name
     */
    public DublicateResourceException(String entityName) {
        super(entityName);
    }

    @Override
    public String getErrorMessageKey() {
        return ExceptionMessageKey.DUBLICATE_RESOURCE;
    }

    @Override
    public int getCode() {
        return ExceptionCode.DUBLICATE_RESOURCE;
    }
}
