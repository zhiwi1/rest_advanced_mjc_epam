package com.epam.esm.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
class GlobalExceptionHandler {
    private static final String SPACE_DELIMITER = " ";
    private final ExceptionMessageCreator exceptionMessageCreator;

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Set<ExceptionResponse> handleConstraintValidationException(
            ConstraintViolationException e, Locale locale) {
        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        return set.stream().map(violation -> violation.getInvalidValue() + SPACE_DELIMITER + exceptionMessageCreator.
                        createMessage(violation.getMessage(), locale))
                .map(message -> new ExceptionResponse(
                        ExceptionCode.INCORRECT_PARAMETER_VALUE, message))
                .collect(Collectors.toSet());

    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleEmptyResultDataAccessException(EmptyResultDataAccessException e, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(ExceptionMessageKey.INVALID_INPUT_WITH_PARAM,
                locale, e.getMessage());
        log.error(exceptionMessage);
        return new ExceptionResponse(ExceptionCode.RESOURCE_NOT_FOUND, exceptionMessage);
    }

    @ExceptionHandler(DublicateResourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleDublicateResourceException(
            DublicateResourceException e, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(e.getErrorMessageKey(),
                locale, e.getMessage());
        log.error(exceptionMessage);
        return new ExceptionResponse(e.getCode(), exceptionMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Set<ExceptionResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, Locale locale) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + SPACE_DELIMITER + exceptionMessageCreator.createMessage(error.getDefaultMessage(), locale))
                .map(message -> new ExceptionResponse(
                        ExceptionCode.INCORRECT_PARAMETER_VALUE, message))
                .collect(Collectors.toSet());
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Set<ExceptionResponse> handleResourceNotFoundException(
            ResourceNotFoundException e, Locale locale) {
        Set<String> exceptionMessage = exceptionMessageCreator.createMessage(e.getErrorMessageKey(),
                locale, (Object[]) e.getId());
        return exceptionMessage.stream()
                .map(message -> new ExceptionResponse(e.getCode(), message))
                .collect(Collectors.toSet());
    }


    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConflict(RuntimeException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ExceptionResponse handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(ExceptionMessageKey.UNSUPPORTED_MEDIA_TYPE, locale);
        log.error(exceptionMessage);
        return new ExceptionResponse(ExceptionCode.UNSUPPORTED_MEDIA_TYPE, exceptionMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleRuntimeException(RuntimeException exception, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(ExceptionMessageKey.VALUE_NOT_IN_RANGE, locale);
        log.error(exceptionMessage);
        return new ExceptionResponse(ExceptionCode.INCORRECT_PARAMETER_VALUE, exceptionMessage);
    }

}

