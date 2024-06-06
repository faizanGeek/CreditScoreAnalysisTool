package com.spring.cloudgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class GatewaySecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .authorizeExchange()
            .pathMatchers("/users/**").authenticated()
            .pathMatchers("/credit/**").authenticated()
            .anyExchange().permitAll()
            .and().csrf().disable()
            .oauth2Login(); // Example for OAuth2
        return http.build();
    }
}
