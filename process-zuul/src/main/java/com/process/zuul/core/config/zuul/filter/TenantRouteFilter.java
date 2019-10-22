package com.process.zuul.core.config.zuul.filter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.RIBBON_ROUTING_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.process.zuul.app.mapper.TenantServiceMapper;
import com.process.zuul.core.config.database.tenant.interceptor.TenantProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author Lynn
 * @since 2019-09-06
 */
@Slf4j
public class TenantRouteFilter extends ZuulFilter {

    private TenantProperties tenantProperties;
    private TenantServiceMapper tenantServiceMapper;
    public TenantRouteFilter(TenantServiceMapper tenantServiceMapper, TenantProperties tenantProperties) {
        this.tenantProperties = tenantProperties;
        this.tenantServiceMapper = tenantServiceMapper;
    }

    @Override
    public String filterType() {
        return ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return RIBBON_ROUTING_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return tenantProperties.isEnabled();
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        log.info("----> {}", context);
        String serviceName = context.get("serviceId").toString();
        log.info(serviceName);
        String tenantAppId = context.getRequest().getHeader("tenantAppId");
        if (StringUtils.hasText(tenantAppId)) {
            String tenantServiceId = tenantServiceMapper.getCurrentTenantServiceId(serviceName, tenantAppId);
            if(StringUtils.hasText(tenantServiceId)) {
                context.set("serviceId", tenantServiceId);
            }
        }
        return null;
    }

}
