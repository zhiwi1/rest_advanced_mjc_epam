package com.epam.esm.expression;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(
            Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)){
            return false;
        }
        System.out.println(permission.toString());
        String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();
        System.out.println(hasPrivilege(auth, targetType, permission.toString().toUpperCase()));
        return hasPrivilege(auth, targetType, permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        System.out.println(permission.toString());
        System.out.println(hasPrivilege(auth, targetType.toUpperCase(),
                permission.toString().toUpperCase()));
        return hasPrivilege(auth, targetType.toUpperCase(),
                permission.toString().toUpperCase());
    }
    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
            if (grantedAuth.getAuthority().startsWith(targetType) &&
                    grantedAuth.getAuthority().contains(permission)) {
                return true;
            }
        }
        return false;
    }
}
