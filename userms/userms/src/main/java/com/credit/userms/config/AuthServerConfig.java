package com.credit.userms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Configuration class for authorization server settings.
 */
@Configuration
public class AuthServerConfig {

    /**
     * Defines a bean for JWT Access Token Converter.
     * This bean is used to convert JWT tokens and define a signing key.
     * @return JwtAccessTokenConverter instance configured with a signing key.
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("signing-key");  // Use a strong, secret key for JWT signing to enhance security.
        return converter;
    }

    /**
     * Defines a bean for TokenStore using JWT.
     * TokenStore is used to manage token storage and retrieval.
     * @return JwtTokenStore instance initialized with JwtAccessTokenConverter.
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());  // JwtTokenStore manages JWT tokens using the provided JwtAccessTokenConverter.
    }

    // Additional configuration for client details, token services, etc. can be added here.
}
