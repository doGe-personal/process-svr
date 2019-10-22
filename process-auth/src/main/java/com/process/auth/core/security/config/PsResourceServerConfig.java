package com.process.auth.core.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Danfeng
 * @since 2018/12/14
 */
@Configuration
@EnableResourceServer
public class PsResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatcher(new AuthRequestMatcher()).authorizeRequests()
                .antMatchers(com.cloud.common.constants.PermitAllUrl.permitAllUrl()).permitAll()
                .anyRequest().authenticated();
    }

    private static class AuthRequestMatcher implements RequestMatcher {
        @Override
        public boolean matches(HttpServletRequest request) {
            // 请求参数中包含access_token参数
            if (request.getParameter(OAuth2AccessToken.ACCESS_TOKEN) != null) {
                return true;
            }
            // 头部的Authorization值以Bearer开头
            String auth = request.getHeader("Authorization");
            if (auth != null) {
                return auth.startsWith(OAuth2AccessToken.BEARER_TYPE);
            }

            return false;
        }
    }

}
