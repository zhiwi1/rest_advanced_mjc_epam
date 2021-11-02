package com.epam.esm.filter;

import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.util.ObjectUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.IOException;

@RequiredArgsConstructor
public class UserFilter extends GenericFilterBean {

    private final UserService userService;

    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Neo4jProperties.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!ObjectUtils.isEmpty(authentication)) {
            KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
            AccessToken idToken = principal.getKeycloakSecurityContext().getToken();
            UserDTO userDTO = UserDTO.builder().name(idToken.getPreferredUsername()).build();
            userService.create(userDTO);
        }
        chain.doFilter(request, response);
    }
}
