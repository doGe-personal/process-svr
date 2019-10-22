package com.process.zuul.core.config.zuul.domain;

import lombok.Setter;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;

/**
 * @author Danfeng
 * @since 2018/12/13
 */
public class PsRouterLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    @Setter
    private PsRouterProvider psRouterProvider;
    private ZuulProperties properties;


    private static final String DEFAULT_ROUTE = "/**";

    public PsRouterLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
    }


    @Override
    protected LinkedHashMap<String, ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<>();
        routesMap.putAll(super.locateRoutes());
        routesMap.putAll(locateRoutesFromDB());
        return routesMap;
    }

    private LinkedHashMap<String, ZuulRoute> locateRoutesFromDB() {

        LinkedHashMap<String, ZuulRoute> routesMap  = psRouterProvider.getRoutes();

        if (routesMap.get(DEFAULT_ROUTE) != null) {
            ZuulRoute defaultRoute = routesMap.get(DEFAULT_ROUTE);
            // Move the defaultServiceId to the end
            routesMap.remove(DEFAULT_ROUTE);
            routesMap.put(DEFAULT_ROUTE, defaultRoute);
        }

        LinkedHashMap<String, ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }

        return values;
    }

    @Override
    public void refresh() {
        super.doRefresh();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
