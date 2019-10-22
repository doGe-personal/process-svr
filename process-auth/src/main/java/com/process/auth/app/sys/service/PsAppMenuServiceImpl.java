package com.process.auth.app.sys.service;

import com.process.auth.app.sys.entity.AbstractAppMenuEntity;
import com.process.auth.app.sys.mapper.PsAppMenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Danfeng
 * @since 2018/7/23
 */
@Service
@RequiredArgsConstructor
public class PsAppMenuServiceImpl implements PsAppMenuService {

    private final PsAppMenuMapper menuMapper;

    @Override
    public List<AbstractAppMenuEntity> menusOf(long userId) {
        return menuMapper.menusOf(userId);
    }

    @Override
    public List<AbstractAppMenuEntity> menusByRoleId(long roleId) {
        return menuMapper.menusByRoleId(roleId);
    }

    @Override
    public List<AbstractAppMenuEntity> findList() {
        return menuMapper.findList();
    }

}
