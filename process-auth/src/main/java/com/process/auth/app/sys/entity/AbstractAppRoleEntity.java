package com.process.auth.app.sys.entity;

import com.process.common.domain.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Danfeng
 * @since 2018/12/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AbstractAppRoleEntity extends AbstractEntity {

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 角色类型
     */
    private String roleType;

    /**
     * 备注
     */
    private String remark;

}
