package com.process.common.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Danfeng
 * @since 2018/12/12
 */
@Data
public class PsZuulRouter implements Serializable {

    private String id;

    private String path;

    private String serviceId;

    private String url;

    private boolean stripPrefix = true;

    private Boolean retryable;

    private String sensitiveHeaders;

    private boolean customSensitiveHeaders = false;

}
