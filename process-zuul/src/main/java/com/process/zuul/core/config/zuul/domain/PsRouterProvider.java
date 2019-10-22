package com.process.zuul.core.config.zuul.domain;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Danfeng
 * @since 2018/12/12
 */
public interface PsRouterProvider {

    /**
     * 获取忽略的path
     *
     * @return 集合
     */
    List<String> getIgnoredPaths();

    /**
     * 获取路由
     *
     * @return
     */
    LinkedHashMap<String, ZuulProperties.ZuulRoute> getRoutes();

}
