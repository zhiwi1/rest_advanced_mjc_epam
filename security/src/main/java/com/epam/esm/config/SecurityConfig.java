package com.epam.esm.config;

import com.epam.esm.filter.SecurityUserFilter;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class SecurityConfig {
    @KeycloakConfiguration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @RequiredArgsConstructor
    @Order(2)
    public static class KeyCloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
        private final UserService userService;

        @Override
        protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
            return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
        }


        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder authManagerBuilder) {
            var keycloakAuthenticationProvider = keycloakAuthenticationProvider();
            keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
            authManagerBuilder.authenticationProvider(keycloakAuthenticationProvider);
        }

        @Bean
        public KeycloakConfigResolver keycloakConfigResolver() {
            return new KeycloakSpringBootConfigResolver();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            super.configure(http);
            http
                    .requiresChannel(channel ->
                            channel.anyRequest().requiresSecure())
                    .authorizeRequests().anyRequest().permitAll().and()
                    .csrf().disable()
                    //       .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .addFilterAfter(new SecurityUserFilter(userService), BasicAuthenticationFilter.class);
        }

    }


    @EnableWebSecurity
    @Order(1)
    public static class PrometheusSecurityConfig extends WebSecurityConfigurerAdapter {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("zhiwi1").password("$2a$12$n9ufGOo2jDfu0GqYzjo1DeFfwoIv9lYuye0vQX4lanmozCZ7iuyA2").roles("ADMIN");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests().requestMatchers(EndpointRequest.toAnyEndpoint()).authenticated().anyRequest().permitAll()
                    .and().httpBasic();
        }


    }
}

