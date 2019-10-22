package com.process.zuul.core.config.database.tenant.interceptor;

import cn.ocoop.framework.sql.TC;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author Lynn
 * @since 2019-09-04
 */
public class WebTenantInterceptor implements HandlerInterceptor {

    private TenantProperties tenantProperties;

    public WebTenantInterceptor(TenantProperties tenantProperties) {
        this.tenantProperties = tenantProperties;
    }

    @Override
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (tenantProperties.isEnabled()) {
            String tenantId = request.getHeader(tenantProperties.getTenantColumn());
            TC.set(tenantId);
        }
        return true;
    }

     @Override
     public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
             @Nullable Exception ex) throws Exception {
         if (tenantProperties.isEnabled()) {
             TC.clear();
         }
    }
}
