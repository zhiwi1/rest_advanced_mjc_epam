package com.epam.esm.expression;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The annotation Has permission to find by user id.
 * The order can be founded only by USER role with current userId or by ADMIN.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("(hasPermissionByUserId(#userId) and hasRole('user')) or hasRole('admin')")
public @interface HasPermissionToFindByUserId {
}
