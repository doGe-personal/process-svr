package com.process.zuul.core.config.feign.config;

import com.process.zuul.app.mapper.TenantServiceMapper;
import com.process.zuul.core.config.database.tenant.interceptor.TenantProperties;
import com.process.zuul.core.config.feign.domain.PsFeignOkHttpProperties;
import com.process.zuul.core.config.feign.interceptor.TenantFeignRouteInterceptor;
import feign.Feign;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * feign okHttp config
 *
 * @author Danfeng
 * @since 2018/12/11
 */
@Configuration
@ConditionalOnClass(Feign.class)
@ConditionalOnProperty("feign.okhttp.enabled")
@AutoConfigureBefore(FeignAutoConfiguration.class)
@EnableConfigurationProperties(PsFeignOkHttpProperties.class)
public class FeignConfiguration {

    @Bean
    public OkHttpClient okHttpClient(PsFeignOkHttpProperties okHttpProperties) {
        return new OkHttpClient.Builder()
                .connectTimeout(okHttpProperties.getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(okHttpProperties.getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(okHttpProperties.getWriteTimeout(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(okHttpProperties.isRetryOnConnectionFailure())
                .connectionPool(new ConnectionPool())
                .build();
    }

    @Bean
    public TenantFeignRouteInterceptor tenantFeignRouteInterceptor(TenantServiceMapper serviceMapper) {
        return new TenantFeignRouteInterceptor(serviceMapper);
    }

}
