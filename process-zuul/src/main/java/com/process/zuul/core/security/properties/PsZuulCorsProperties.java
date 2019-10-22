package com.process.zuul.core.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Danfeng
 * @since 2018/11/22
 */
@Data
@ConfigurationProperties(prefix = "ps.security.cors")
public class PsZuulCorsProperties {
    private boolean allowCredentials;
    private String allowedOrigins;
    private String allowedHeader;
    private String allowedMethods;
    private Long maxAge;
    private String path;
}
