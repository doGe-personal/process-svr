package com.process.auth.core.security.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AbstractAppUser extends AbstractAuthUser {

    private String nickname;
    private String headImgUrl;
    private String phone;
    private String email;
    private Integer sex;
    private Integer age;
    private transient boolean enabled;
    private String type;

    /**
     * 角色ID
     */
    private List<Long> roleIds;
    
}
