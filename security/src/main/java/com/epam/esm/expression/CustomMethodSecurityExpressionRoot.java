package com.epam.esm.expression;


import com.epam.esm.dto.OrderInputDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
@Slf4j
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {
    private static final String CUSTOM_PERMISSION_PARAM = "customPermission";

    private final UserService userService;

    public CustomMethodSecurityExpressionRoot(Authentication authentication, UserService userService) {
        super(authentication);
        this.userService = userService;
    }

    public boolean hasPermissionToFindByUserId(Long userId) {
        return hasPermissionInOrders(userId);
    }

    public boolean hasPermissionToCreateOrder(OrderInputDto choosedUserDto) {
        Long id = choosedUserDto.getUserId();
        return hasPermissionInOrders(id);
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
//
//    public boolean customPerm(String permissionName) {
//        if (this.authentication.getClass() == KeycloakAuthenticationToken.class) {
//            var token = (KeycloakAuthenticationToken) this.authentication;
//            var principal = (KeycloakPrincipal) token.getPrincipal();
//            Map<String, Object> otherClaims = principal.getKeycloakSecurityContext().getToken().getOtherClaims();
//            if (!ObjectUtils.isEmpty(otherClaims)) {
//                var customPermission = (String) otherClaims.get(CUSTOM_PERMISSION_PARAM);
//                if (StringUtils.isNotEmpty(customPermission)) {
//                    List<String> permissions = Stream.of(customPermission.split(";")).collect(Collectors.toList());
//                    return permissions.contains(permissionName);
//                } else {
//                    return false;
//                }
//            }
//            return false;
//        }
////        //todo authorization Exception
////        throw new RuntimeException("hello");
//       return false;
//    }

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