package com.process.zuul.core.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Danfeng
 * @since 2018/11/22
 */
@Data
@ConfigurationProperties(prefix = "ps.security.csrf")
public class PsZuulCsrfProperties {
    /**
     * 是否启用
     */
    private boolean enable;

}
