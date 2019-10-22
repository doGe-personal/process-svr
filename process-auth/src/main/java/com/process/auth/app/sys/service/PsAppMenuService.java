package com.process.auth.app.sys.service;

import com.process.auth.app.sys.entity.AbstractAppMenuEntity;

import java.util.List;

/**
 * @author Danfeng
 * @since 2018/7/10
 */
public interface PsAppMenuService {
    /**
     * 获取菜单
     *
     * @param userId 用戶ID
     * @return 菜单集合
     */
    List<AbstractAppMenuEntity> menusOf(long userId);

    /**
     *
     * @param roleId
     * @return
     */
    List<AbstractAppMenuEntity> menusByRoleId(long roleId);

    /**
     * 查询所有菜单
     *
     * @return
     */
    List<AbstractAppMenuEntity> findList();
}
