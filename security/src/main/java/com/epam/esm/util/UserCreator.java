package com.epam.esm.util;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import lombok.experimental.UtilityClass;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;

@UtilityClass
public class UserCreator {
    public static UserDto createUser(Authentication authentication, UserService userService) {
        var principal = (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
        AccessToken accessToken = principal.getKeycloakSecurityContext().getToken();
        var userDto = UserDto.builder().name(accessToken.getPreferredUsername()).build();
        userDto = userService.createIfNotExist(userDto);
        return userDto;
    }
}
