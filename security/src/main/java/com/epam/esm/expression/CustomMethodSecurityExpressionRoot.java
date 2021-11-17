package com.epam.esm.expression;


import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;

@Slf4j
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private final UserService userService;

    public CustomMethodSecurityExpressionRoot(Authentication authentication, UserService userService) {
        super(authentication);
        this.userService = userService;
    }

    public boolean hasPermissionByUserId(Long userId) {
        return hasPermissionInOrders(userId);
    }

    private boolean hasPermissionInOrders(Long userId) {
        log.info(userId.toString());
        if (!ObjectUtils.isEmpty(authentication)) {
            var principal = (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
            AccessToken accessToken = principal.getKeycloakSecurityContext().getToken();
            var userDto = UserDto.builder().name(accessToken.getPreferredUsername()).build();
            if (userDto != null) {
                userDto = userService.createIfNotExist(userDto);
            }
            return userId.equals(userDto.getId());
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