package com.process.auth.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


/**
 * @author Danfeng
 * @since 2018/11/17
 */
@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "ps.security")
public class AuthCenterProperties {
    private String tokenStoreType;

    private String signKey;
}
