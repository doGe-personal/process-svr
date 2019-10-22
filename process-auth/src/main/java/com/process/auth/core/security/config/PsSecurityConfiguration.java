package com.process.auth.core.security.config;

import com.process.auth.core.properties.PsZuulCorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * EnableGlobalMethodSecurity : Spring Security默认是禁用注解的，要想开启注解，加上此注释
 *
 * @author Danfeng
 * @since 2018/11/17
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties({PsZuulCorsProperties.class})
@RequiredArgsConstructor
public class PsSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public final UserDetailsService userDetailsService;
    private final PsZuulCorsProperties psZuulCorsProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @ConditionalOnProperty("ps.security.cors")
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        // 允许cookies跨域
        config.setAllowCredentials(psZuulCorsProperties.isAllowCredentials());
        // #允许向该服务器提交请求的URI，*表示全部允许
        config.addAllowedOrigin(psZuulCorsProperties.getAllowedOrigins());
        // #允许访问的头信息,*表示全部
        config.addAllowedHeader(psZuulCorsProperties.getAllowedHeader());
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(psZuulCorsProperties.getMaxAge());
        // 允许提交请求的方法，*表示全部允许
        config.addAllowedMethod(psZuulCorsProperties.getAllowedMethods());
        source.registerCorsConfiguration(psZuulCorsProperties.getPath(), config);
        return new CorsFilter(source);
    }

}
