package com.epam.esm.expression;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@Slf4j
public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(
            Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            log.error("Authentication = {}, targetDomainObject = {}, permission = {} \nError: check your input", auth, targetDomainObject, permission);
            return false;
        }
        String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();
        return hasPrivilege(auth, targetType, permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            log.error("Authentication = {}, targetType = {}, permission = {} \nError: check your input", auth, targetType, permission);
            return false;
        }
        return hasPrivilege(auth, targetType.toUpperCase(),
                permission.toString().toUpperCase());
    }

    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        return auth.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().startsWith(targetType) &&
                grantedAuthority.getAuthority().contains(permission));
    }
}
