package com.epam.esm.expression;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation Has permission to create order.
 * create order can only role user for current user id or admin
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("(hasPermissionByUserId(#orderInputDto.userId) and hasRole('user')) or hasRole('admin')")
public @interface HasPermissionToCreateOrder {
}
