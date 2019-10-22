package com.process.zuul.core.config.feign.domain;

/**
 * @author Lynn
 * @since 2019-09-05
 */
public class TenantThreadLocal {

    private static ThreadLocal<String> serviceNameLocal = new ThreadLocal<>();

    public static void set(String serviceName) {
        serviceNameLocal.set(serviceName);
    }

    public static String get() {
        return serviceNameLocal.get();
    }

    public static void remove() {
        serviceNameLocal.remove();
    }

}
