package com.process.auth.app.sys.service;

import com.process.auth.app.sys.mapper.PsAppUserMapper;
import com.process.auth.core.security.domain.AbstractAppUser;
import com.process.common.database.utils.PsBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PsAppUserServiceImpl implements PsAppUserService {
    private final PsAppUserMapper appUserMapper;
    private final PsBatchService psBatchService;
    private static final BCryptPasswordEncoder bCryptPassword = new BCryptPasswordEncoder();
    private static final String DEFAULT_PASS = "password";

    @Transactional
    @Override
    public int optEntity(AbstractAppUser appUser) {
        int result;
        if (appUser.isNew()) {
            appUser.setPassword(bCryptPassword.encode(DEFAULT_PASS));
//            appUser.setEnabled(true);
            result = appUserMapper.insertEntity(appUser);
        } else {
            result = appUserMapper.updateEntity(appUser);
        }
//        if (result > 0 && !CollectionUtils.isEmpty(appUser.getRoleIds())) {
//            appUserMapper.deleteUserRole(appUser.getId());
//            psBatchService.runBatch(appUser.getRoleIds().iterator(), 100, PsAppUserMapper.class, (appUserMapper, roleId) -> {
//                appUserMapper.bindUserRole(appUser.getId(), roleId);
//            });
//        }
        return result;
    }

    @Transactional
    @Override
    public int batchDelete(List<Long> ids) {
        return appUserMapper.batchDelete(ids);
    }

    @Override
    public int resetPwd(long id) {
        return appUserMapper.resetPwd(id, bCryptPassword.encode(DEFAULT_PASS));
    }

}
