package com.process.auth.app.sys.api;

import com.process.common.database.domain.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PsAppRoleQuery extends PagingQuery {

    private String roleName;

    private String roleCode;

}
