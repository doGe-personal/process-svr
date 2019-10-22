package com.process.zuul.app.service;

import com.process.zuul.app.entity.TenantServiceEntity;
import com.process.zuul.app.mapper.TenantServiceMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Lynn
 * @since 2019-09-04
 */
@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantServiceMapper tenantServiceMapper;

    @Override
    public TenantServiceEntity findBySpecialAppKey(String specialAppKey) {
        return tenantServiceMapper.findBySpecialAppKey(specialAppKey);
    }

    @Override
    public TenantServiceEntity findByPath(String requestPath) {
        return tenantServiceMapper.findByPath(requestPath);
    }

    @Override
    public List<TenantServiceEntity> findAll() {
        return tenantServiceMapper.findAll();
    }

}
