package com.process.business.core.database.tenant.interceptor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Lynn
 * @since 2019-09-04
 */
@Data
@ConfigurationProperties(prefix = "app.tenant")
public class TenantProperties {
    private boolean enabled;
    private String tenantId;
    private String tenantColumn;
    private String tenantColumnType;
    private boolean tenantInsertEnabled;
    private boolean tenantDeleteEnabled;
    private boolean tenantUpdateEnabled;
    private boolean tenantSelectEnabled;
}
