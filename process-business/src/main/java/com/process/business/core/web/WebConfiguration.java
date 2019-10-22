package com.process.business.core.web;

import com.process.business.core.database.tenant.interceptor.TenantProperties;
import com.process.business.core.database.tenant.interceptor.WebTenantInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Lynn
 * @since 2019-09-04
 */
@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final TenantProperties tenantProperties;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration tenantInterceptor = registry
                .addInterceptor(new WebTenantInterceptor(tenantProperties));
        tenantInterceptor.addPathPatterns("/**");
    }
}
