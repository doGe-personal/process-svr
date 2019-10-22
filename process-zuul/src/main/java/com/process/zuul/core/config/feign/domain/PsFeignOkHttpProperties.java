package com.process.zuul.core.config.feign.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Danfeng
 * @since 2018/12/11
 */
@Data
@ConfigurationProperties("fegin.okhttp")
public class PsFeignOkHttpProperties {

    /**
     * 连接超时时间
     */
    private Integer connectTimeout = 10_000;

    /**
     * 读超时时间
     */
    private Integer readTimeout = 10_000;

    /**
     * 写超时时间
     */
    private Integer writeTimeout = 10_000;

    /**
     * 是否重连
     */
    private boolean retryOnConnectionFailure;

}
