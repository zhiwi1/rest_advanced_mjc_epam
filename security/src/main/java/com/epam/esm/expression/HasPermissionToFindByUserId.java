package com.epam.esm.expression;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Has permission to find by user id.
 * find order by user id can only role user for current user_id or admin
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("(hasPermissionByUserId(#userId) and hasRole('user')) or hasRole('admin')")
public @interface HasPermissionToFindByUserId {
}
