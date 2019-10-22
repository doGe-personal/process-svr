package com.process.auth.app.sys.service;

import com.process.auth.app.sys.entity.AbstractAppRoleEntity;
import com.process.auth.app.sys.mapper.PsAppRoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PsAppRoleServiceImpl implements PsAppRoleService {
    private final PsAppRoleMapper appRoleMapper;

    @Transactional
    @Override
    public int optEntity(AbstractAppRoleEntity appRoleEntity) {
        if (appRoleEntity.isNew()) {
            return appRoleMapper.insertEntity(appRoleEntity);
        }
        return appRoleMapper.updateEntity(appRoleEntity);
    }

    @Transactional
    @Override
    public int batchDelete(List<Long> ids) {
        return appRoleMapper.batchDelete(ids);
    }
}
