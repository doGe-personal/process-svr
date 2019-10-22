package com.process.auth.core.config.global;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 客户端自动刷新系统配置信息
 *
 * @author Danfeng
 * @since 2018/11/17
 */
@Slf4j
@ConditionalOnClass(RefreshEndpoint.class)
@ConditionalOnProperty("spring.cloud.config.refreshInterval")
@AutoConfigureAfter(RefreshAutoConfiguration.class)
@Configuration
@RequiredArgsConstructor
public class PsApRefreshConfiguration implements SchedulingConfigurer {

    /**
     * 刷新的端点
     */
    private final RefreshEndpoint refreshEndpoint;

    /**
     * 间隔刷新时间
     */
    @Value("${spring.cloud.config.refreshInterval}")
    private long refreshInterval;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 依毫秒为单位返回刷新时间
        final long interval = refreshInterval * 1000;
        log.info(String.format("Scheduling config refresh task with %s second delay", refreshInterval));
        taskRegistrar.addFixedDelayTask(new IntervalTask(refreshEndpoint::refresh, interval, interval));
    }

    /**
     * 如果没有在上下文中注册，则启程序
     */
    @ConditionalOnMissingBean(ScheduledAnnotationBeanPostProcessor.class)
    @EnableScheduling
    @Configuration
    protected static class EnableSchedulingConfigProperties {

    }

}
