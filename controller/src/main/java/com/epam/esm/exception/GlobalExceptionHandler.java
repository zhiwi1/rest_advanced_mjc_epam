package com.epam.esm.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
        return createExceptionResponse(set, locale);
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

//todo n+1
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(ExceptionMessageKey.INVALID_INPUT, locale);
        log.error(exceptionMessage);
        return new ExceptionResponse(ExceptionCode.INCORRECT_PARAMETER_VALUE, exceptionMessage);
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleMethodArgumentTypeMismatchException(NumberFormatException exception, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(ExceptionMessageKey.RESOURCE_NOT_FOUND, locale);
        log.error(exceptionMessage);
        return new ExceptionResponse(ExceptionCode.NOT_FOUND, exceptionMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleRuntimeException(RuntimeException exception, Locale locale) {
        String exceptionMessage = exceptionMessageCreator.createMessage(ExceptionMessageKey.INVALID_INPUT, locale);
        log.error(exceptionMessage);
        return new ExceptionResponse(ExceptionCode.INCORRECT_PARAMETER_VALUE, exceptionMessage);
    }

    private Set<ExceptionResponse> createExceptionResponse(Set<ConstraintViolation<?>> set, Locale locale) {
        return set.stream().map(violation -> violation.getInvalidValue() + SPACE_DELIMITER + exceptionMessageCreator.
                        createMessage(violation.getMessage(), locale))
                .map(message -> new ExceptionResponse(
                        ExceptionCode.INCORRECT_PARAMETER_VALUE, message))
                .collect(Collectors.toSet());
    }
}
