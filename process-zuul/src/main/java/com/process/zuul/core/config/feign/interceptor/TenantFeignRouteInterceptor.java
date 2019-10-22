package com.process.zuul.core.config.feign.interceptor;

import com.process.zuul.app.mapper.TenantServiceMapper;
import com.process.zuul.core.config.database.tenant.interceptor.TenantProperties;
import com.process.zuul.core.config.feign.domain.TenantThreadLocal;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Lynn
 * @since 2019-09-05
 */
public class TenantFeignRouteInterceptor implements RequestInterceptor {

    private TenantServiceMapper serviceMapper;

    public TenantFeignRouteInterceptor(TenantServiceMapper serviceMapper) {
        this.serviceMapper = serviceMapper;
    }

    @Override
    public void apply(RequestTemplate template) {
        String serviceName = TenantThreadLocal.get();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        String authorization = request.getHeader("Authorization");
        template.header("Authorization", authorization);

        String tenantAppId = request.getHeader("tenantAppId");
        String serviceId = serviceMapper.getCurrentTenantServiceId(serviceName, tenantAppId);

        if (!StringUtils.isEmpty(serviceId)) {
            String url;
            if (!StringUtils.startsWith(serviceId, "http")) {
                url = "http://" + serviceId;
            } else {
                url = serviceId;
            }
            if (!StringUtils.startsWith(template.url(), "http")) {
                template.insert(0, url);
            }
        }
        TenantThreadLocal.remove();
    }

}
