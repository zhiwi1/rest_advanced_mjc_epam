package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.Locale;


@SpringBootConfiguration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private WebConfigParam webConfigParam;

    @Bean
    public LocaleResolver localeResolver() {
        final var resolver = new AcceptHeaderLocaleResolver();
        resolver.setSupportedLocales(Arrays.asList(new Locale(webConfigParam.getDefaultLocale()),
                new Locale(webConfigParam.getSecondLocale()))
        );
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }

    @Bean
    public MessageSource messageSource() {
        final var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(webConfigParam.getFilename());
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding(webConfigParam.getEncoding());
        return messageSource;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}