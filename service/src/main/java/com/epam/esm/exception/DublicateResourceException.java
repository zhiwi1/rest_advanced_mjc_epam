package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class DublicateResourceException extends ServiceException {

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
