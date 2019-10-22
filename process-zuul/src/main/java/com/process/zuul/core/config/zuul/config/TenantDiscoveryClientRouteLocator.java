package com.process.zuul.core.config.zuul.config;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.springframework.util.StringUtils.hasText;

import com.process.zuul.app.entity.TenantServiceEntity;
import com.process.zuul.app.mapper.TenantServiceMapper;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.util.PatternMatchUtils;

/**
 * @author Lynn
 * @since 2019-09-05
 */
public class TenantDiscoveryClientRouteLocator extends DiscoveryClientRouteLocator {

    private DiscoveryClient discovery;

    private ZuulProperties properties;

    private TenantServiceMapper tenantServiceMapper;

    public TenantDiscoveryClientRouteLocator(String servletPath,
            DiscoveryClient discovery,
            ZuulProperties properties,
            ServiceInstance localServiceInstance) {
        super(servletPath, discovery, properties, localServiceInstance);
        this.discovery = discovery;
        this.properties = properties;
    }
    public TenantDiscoveryClientRouteLocator(String servletPath,
            DiscoveryClient discovery,
            ZuulProperties properties,
            ServiceInstance localServiceInstance, TenantServiceMapper tenantServiceMapper) {
        super(servletPath, discovery, properties, localServiceInstance);
        this.discovery = discovery;
        this.properties = properties;
        this.tenantServiceMapper = tenantServiceMapper;
    }

    @Override
    protected LinkedHashMap<String, ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulRoute> routesMap = super.locateRoutes();
        routesMap.putAll(customLocateRoutes());
        if (this.discovery != null) {
            Map<String, ZuulRoute> staticServices = new LinkedHashMap<>();
            for (ZuulRoute route : routesMap.values()) {
                String serviceId = route.getServiceId();
                if (serviceId == null) {
                    serviceId = route.getId();
                }
                if (serviceId != null) {
                    staticServices.put(serviceId, route);
                }
            }
            List<String> services = this.discovery.getServices();
            String[] ignored = this.properties.getIgnoredServices()
                    .toArray(new String[0]);
            for (String serviceId : services) {
                String key = "/" + mapRouteToService(serviceId) + "/**";
                if (staticServices.containsKey(serviceId)
                        && staticServices.get(serviceId).getUrl() == null) {
                    ZuulRoute staticRoute = staticServices.get(serviceId);
                    if (!hasText(staticRoute.getLocation())) {
                        staticRoute.setLocation(serviceId);
                    }
                }
                if (!PatternMatchUtils.simpleMatch(ignored, serviceId)
                        && !routesMap.containsKey(key)) {
                    routesMap.put(key, new ZuulRoute(key, serviceId));
                }
            }
        }
        if (routesMap.get(DEFAULT_ROUTE) != null) {
            ZuulRoute defaultRoute = routesMap.get(DEFAULT_ROUTE);
            routesMap.remove(DEFAULT_ROUTE);
            routesMap.put(DEFAULT_ROUTE, defaultRoute);
        }
        LinkedHashMap<String, ZuulRoute> values = new LinkedHashMap<>();
        for (Entry<String, ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    protected Map<String, ZuulProperties.ZuulRoute> customLocateRoutes() {
        List<TenantServiceEntity> tenantRoutes = tenantServiceMapper.findAll();
        HashMap<String, TenantServiceEntity> tenantRouteMap = new HashMap<>();
        tenantRoutes.forEach(tenantRoute -> tenantRouteMap.put(tenantRoute.getRequestPath(), tenantRoute));
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        for (ZuulProperties.ZuulRoute route : this.properties.getRoutes().values()) {
            if (tenantRouteMap.containsKey(route.getPath())) {
                TenantServiceEntity tenantRoute = tenantRouteMap.get(route.getPath());
                if (!equalsIgnoreCase(tenantRoute.getServiceId(), route.getServiceId())) {
                    routesMap.put(route.getPath(), new ZuulProperties.ZuulRoute(route.getPath(), tenantRoute.getServiceId()));
                    continue;
                }
            }
            routesMap.put(route.getPath(), route);
        }
        return routesMap;
    }

}
