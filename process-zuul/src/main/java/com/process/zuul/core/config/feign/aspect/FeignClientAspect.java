package com.process.zuul.core.config.feign.aspect;

import com.process.zuul.core.config.feign.domain.TenantThreadLocal;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author Lynn
 * @since 2019-09-05
 */
@Aspect
@Component
public class FeignClientAspect {
    /**
     * 拦截 *FeignClient 结尾的接口的所有方法
     * 这里无法直接通过注解方式拦截 @FeignClient 注解的接口，因为 FeignClient 只有接口，没有实现(生成的是代理类)
     */
    @Before("execution(* com..*.*FeignClient.*(..))")
    public void keepServiceName(JoinPoint joinPoint) {
        Type type = joinPoint.getTarget().getClass().getGenericInterfaces()[0];
        Annotation annotation = ((Class)type).getAnnotation(FeignClient.class);
        if (annotation instanceof FeignClient) {
            FeignClient feignClient = (FeignClient) annotation;
            // 将服务名放入ThreadLocal中
            String serviceName = feignClient.value();
            if (StringUtils.isEmpty(serviceName)) {
                serviceName = feignClient.name();
            }
            TenantThreadLocal.set(serviceName);
        }
    }
}
