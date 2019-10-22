package com.process.auth.app.sys.mapper;

import com.process.auth.app.sys.api.PsAppRoleQuery;
import com.process.auth.app.sys.entity.AbstractAppRoleEntity;
import com.process.common.database.domain.PsSql;
import com.process.common.domain.BaseCodeName;
import com.process.common.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
public interface PsAppRoleMapper {

    class PsAppRoleSqlBuilder extends PsSql {

        private void buildSelectSql() {
            SELECT("T.ID id");
            SELECT("T.ROLE_CODE roleCode");
            SELECT("T.ROLE_NAME roleName");
            SELECT("T.ROLE_DESC roleDesc");
            SELECT("T.ROLE_TYPE roleType");
            SELECT("T.REMARK remark");
        }

        private void buildFromSql() {
            FROM("PS_AUTH_ROLE T");
        }

        private void buildFilterSql(PsAppRoleQuery query) {
            if (StringUtils.hasText(query.getRoleName())) {
                WHERE("T.ROLE_NAME LIKE " + SqlUtil.toSqlLikeString(query.getRoleName()));
            }
            if (StringUtils.hasText(query.getRoleCode())) {
                WHERE("T.ROLE_CODE LIKE " + SqlUtil.toSqlLikeString(query.getRoleCode()));
            }
            ORDER_BY(query);
        }

        static final String PAGE_SQL = "pages";

        public String pages(PsAppRoleQuery query) {
            buildSelectSql();
            buildFromSql();
            buildFilterSql(query);
            return toPagingSql(query);
        }

        static final String COUNT_SQL = "count";

        public String count(PsAppRoleQuery query) {
            buildFromSql();
            buildFilterSql(query);
            return toCountSql();
        }

        static final String BATCH_DELETE_SQL = "batchDelete";

        public String batchDelete(@Param("ids") List<Long> ids) {
            DELETE_FROM("PS_AUTH_ROLE");
            WHERE("ID IN " + SqlUtil.toSqlNumberSet(ids));
            return toString();
        }

        static final String SUGGEST_SQL = "suggest";

        public String suggest(String key) {
            SELECT("ID id");
            SELECT("ID code");
            SELECT("ROLE_NAME name");
            FROM("PS_AUTH_ROLE");
            if (StringUtils.hasText(key)) {
                WHERE("ROLE_NAME LIKE " + SqlUtil.toSqlLikeString(key));
            }
            return toString();
        }
    }

    @SelectProvider(method = PsAppRoleSqlBuilder.PAGE_SQL, type = PsAppRoleSqlBuilder.class)
    List<AbstractAppRoleEntity> pages(PsAppRoleQuery query);

    /**
     * 计数
     *
     * @param query
     * @return
     */
    @SelectProvider(method = PsAppRoleSqlBuilder.COUNT_SQL, type = PsAppRoleSqlBuilder.class)
    int total(PsAppRoleQuery query);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Select({
            "SELECT T.ID id,T.ROLE_CODE roleCode,",
            "T.ROLE_NAME roleName,T.ROLE_DESC roleDesc,",
            "T.ROLE_TYPE roleType,T.REMARK remark",
            "FROM PS_AUTH_ROLE T",
            "WHERE T.ID = #{id}"
    })
    AbstractAppRoleEntity getEntity(long id);

    /**
     * 新增
     *
     * @param appUser
     * @return
     */
    @Insert({
            "INSERT INTO PS_AUTH_ROLE (",
            "ROLE_CODE,ROLE_NAME,ROLE_DESC,",
            "ROLE_TYPE,REMARK",
            ") VALUE (",
            "#{roleCode},#{roleName},#{roleDesc},",
            "#{roleType},#{remark}",
            ")",
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    int insertEntity(AbstractAppRoleEntity appUser);

    /**
     * 修改
     *
     * @param appUser
     * @return
     */
    @Update({
            "UPDATE PS_AUTH_ROLE SET",
            "ROLE_CODE=#{roleCode},ROLE_NAME=#{roleName},",
            "ROLE_DESC=#{roleDesc},ROLE_TYPE=#{roleType},",
            "REMARK=#{remark}",
            "WHERE ID = #{id}"
    })
    int updateEntity(AbstractAppRoleEntity appUser);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteProvider(type = PsAppRoleSqlBuilder.class, method = PsAppRoleSqlBuilder.BATCH_DELETE_SQL)
    int batchDelete(@Param("ids") List<Long> ids);


    /**
     * 下拉选择
     *
     * @param key
     * @return
     */
    @SelectProvider(method = PsAppRoleSqlBuilder.SUGGEST_SQL, type = PsAppRoleSqlBuilder.class)
    List<BaseCodeName> suggest(String key);

}
