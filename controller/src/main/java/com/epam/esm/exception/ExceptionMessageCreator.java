package com.epam.esm.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Exception message creator. Creator can get i18n message for response
 */
@Component
public class ExceptionMessageCreator {

    private final MessageSource messageSource;

    /**
     * Instantiates a new Exception message creator.
     *
     * @param messageSource the message source
     */
    @Autowired
    public ExceptionMessageCreator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Create message string.
     *
     * @param exceptionMessageKey the exception message key
     * @param locale              the locale
     * @param arg                 the arg
     * @return the string
     */
    public String createMessage(String exceptionMessageKey, Locale locale, Object arg) {
        return messageSource.getMessage(exceptionMessageKey, new Object[]{arg}, locale);
    }

    /**
     * Create message.
     *
     * @param exceptionMessageKey the exception message key
     * @param locale              the locale
     * @param args                the args
     * @return the set
     */
    public Set<String> createMessage(String exceptionMessageKey, Locale locale, Object... args) {
        return Arrays.stream(args)
                .map(arg -> messageSource.getMessage(exceptionMessageKey, new Object[]{arg}, locale))
                .collect(Collectors.toSet());

    }

    /**
     * Create message string.
     *
     * @param exceptionMessageKey the exception message key
     * @param locale              the locale
     * @return the string
     */
    public String createMessage(String exceptionMessageKey, Locale locale) {
        return messageSource.getMessage(exceptionMessageKey,new Object[]{}, locale);
    }

}