package com.epam.esm.expression;

import org.apache.commons.codec.binary.StringUtils;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomMethodSecurityExpression extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    public CustomMethodSecurityExpression(Authentication authentication) {
        super(authentication);
    }

    public boolean customPerm(String permissionName) {
        if (this.authentication.getClass() == KeycloakAuthenticationToken.class) {

            var token = (KeycloakAuthenticationToken) this.authentication;
            var principal = (KeycloakPrincipal) token.getPrincipal();
            Map<String, Object> otherClaims = principal.getKeycloakSecurityContext().getToken().getOtherClaims();

            if (otherClaims != null) {
                String customPermission = (String) otherClaims.get("customPermission");
                //todo StringUtils
                if (customPermission != null) {
                    if (!customPermission.isBlank()) {
                        System.out.println(customPermission);
                        List<String> permissions = Stream.of(customPermission.split(";")).collect(Collectors.toList());
                        return permissions.contains(permissionName);
                    }
                } else return false;
            }

            return false;
        }
        return false;
    }

    @Override
    public void setFilterObject(Object filterObject) {

    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object returnObject) {

    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }
}