package com.process.zuul.app.mapper;

import com.process.zuul.app.entity.TenantServiceEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Lynn
 * @since 2019-09-04
 */
public interface TenantServiceMapper {

    @Select({
            "SELECT id id ,tenant_id tenantId,service_id serviceId,tenant_app_id tenantAppId,request_path requestPath",
            "FROM PS_TENANT_SERVICE where tenant_app_id=#{tenantAppId}"
    })
    TenantServiceEntity findBySpecialAppKey(String tenantAppId);

    @Select({
            "SELECT id id ,tenant_id tenantId,service_id serviceId,tenant_app_id tenantAppId,request_path requestPath",
            "FROM PS_TENANT_SERVICE where request_path=#{requestPath}"
    })
    TenantServiceEntity findByPath(String requestPath);

    @Select({
            "SELECT id,tenant_id,service_id,tenant_app_id,request_path",
            "FROM PS_TENANT_SERVICE"
    })
    List<TenantServiceEntity> findAll();

    @Select({
            "SELECT SERVICE_ID FROM PS_TENANT_SERVICE WHERE service_name = #{serviceName} AND tenant_app_id=#{tenantAppId} LIMIT 1"
    })
    String getCurrentTenantServiceId(@Param("serviceName") String serviceName, @Param("tenantAppId") String tenantAppId);

}
