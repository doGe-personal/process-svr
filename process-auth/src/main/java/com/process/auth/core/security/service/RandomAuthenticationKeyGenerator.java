package com.process.auth.core.security.service;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

import java.util.UUID;

/**
 * @author DanFeng
 * @see org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator
 * @see org.springframework.security.oauth2.provider.token.TokenStore
 */
public class RandomAuthenticationKeyGenerator implements AuthenticationKeyGenerator {

    @Override
    public String extractKey(OAuth2Authentication authentication) {
        return UUID.randomUUID().toString();
    }

}
