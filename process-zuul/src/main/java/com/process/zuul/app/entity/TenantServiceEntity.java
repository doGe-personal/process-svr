package com.process.zuul.app.entity;

import lombok.Data;

/**
 * @author Lynn
 * @since 2019-09-04
 */
@Data
public class TenantServiceEntity {

    private Long id;
    private String tenantId;
    private String serviceId;
    private String tenantAppId;
    private String requestPath;

}
