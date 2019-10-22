package com.process.auth.core.security.config;

import com.process.auth.core.properties.AuthCenterProperties;
import com.process.auth.core.security.domain.PsTokenConverter;
import com.process.auth.core.security.service.PsRedisClientDetailsService;
import com.process.auth.core.security.service.RandomAuthenticationKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author DanFeng
 * @since 2018/12/26
 * 授权服务器配置
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
@EnableConfigurationProperties({AuthCenterProperties.class})
public class PsAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PsRedisClientDetailsService redisClientDetailsService;

    @Qualifier("psUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
//    @Autowired
//    private RedisAuthorizationCodeServices redisAuthorizationCodeServices;
    @Autowired
    private TokenStore tokenStore;
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private AuthCenterProperties authCenterProperties;

    private static final String TOKEN_STORE_TYPE_JWT = "jwt";

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients(); // 允许表单形式的认证
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(this.authenticationManager).userDetailsService(userDetailsService);
        endpoints.tokenStore(tokenStore);
        if (authCenterProperties.getTokenStoreType().equals(TOKEN_STORE_TYPE_JWT) && null != jwtAccessTokenConverter) {
            endpoints.accessTokenConverter(jwtAccessTokenConverter);
        }
//        endpoints.authorizationCodeServices(redisAuthorizationCodeServices);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(redisClientDetailsService);
        redisClientDetailsService.loadAllClientToCache();
    }

    /**
     * 令牌存储
     */
    @Bean
    @ConditionalOnProperty(prefix = "ps.security", name = "tokenStoreType", havingValue = "redis")
    public TokenStore tokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setAuthenticationKeyGenerator(new RandomAuthenticationKeyGenerator());
        return redisTokenStore;
    }

    @Configuration
    @RequiredArgsConstructor
    @ConditionalOnProperty(prefix = "ps.security", name = "tokenStoreType", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenConfig {

        private final AuthCenterProperties authCenterProperties;

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            PsTokenConverter converter = new PsTokenConverter();
            converter.setSigningKey(authCenterProperties.getSignKey());
            return converter;
        }

    }

}
