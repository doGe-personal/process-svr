package com.process.auth.app.sys.service;

import com.process.auth.core.security.domain.AbstractAppUser;

import java.util.List;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
public interface PsAppUserService {
    /**
     * 新增/修改
     *
     * @param appUser
     * @return
     */
    int optEntity(AbstractAppUser appUser);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    int batchDelete(List<Long> ids);

    /**
     * 重置密码
     * @param id
     * @return
     */
    int resetPwd(long id);
}
