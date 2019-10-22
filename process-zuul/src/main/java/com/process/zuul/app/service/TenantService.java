package com.process.zuul.app.service;

import com.process.zuul.app.entity.TenantServiceEntity;
import java.util.List;

/**
 * @author Lynn
 * @since 2019-09-04
 */
public interface TenantService {

    TenantServiceEntity findBySpecialAppKey(String specialAppKey);

    TenantServiceEntity findByPath(String requestPath);

    List<TenantServiceEntity> findAll();
}
