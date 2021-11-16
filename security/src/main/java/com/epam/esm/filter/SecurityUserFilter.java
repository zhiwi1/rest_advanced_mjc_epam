package com.epam.esm.filter;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.authorization.Permission;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class SecurityUserFilter extends GenericFilterBean {
    private final UserService userService;

    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!ObjectUtils.isEmpty(authentication)) {
            var principal = (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
            AccessToken accessToken = principal.getKeycloakSecurityContext().getToken();
            var userDto = UserDto.builder().name(accessToken.getPreferredUsername()).build();
            if(userDto!=null){
                userDto=userService.createIfNotExist(userDto);
            }
         principal.getKeycloakSecurityContext().getToken().getId();
//            Optional.ofNullable(userDto).ifPresent(userService::createIfNotExist);
            System.out.println(userDto);
            Permission permission = new Permission();
            permission.setResourceId(userDto.getId().toString());


//            CustomAuthentication customAuthentication = new CustomAuthentication(authentication);
//            customAuthentication.setDetails(userDto.getId());
//
//            System.out.println("filter");
//            SecurityContextHolder.getContext().setAuthentication(customAuthentication);
        }
        chain.doFilter(request, response);
    }
}

