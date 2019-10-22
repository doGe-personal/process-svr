package com.process.auth.app.sys.mapper;

import com.process.auth.app.sys.api.PsAppUserQuery;
import com.process.auth.core.security.domain.AbstractAppUser;
import com.process.common.database.domain.PsSql;
import com.process.common.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
public interface PsAppUserMapper {

    class PsAppUserSqlBuilder extends PsSql {

        private void buildSelectSql() {
            SELECT("T.ID id");
            SELECT("T.USERNAME username");
            SELECT("T.PASSWORD password");
            SELECT("T.NICKNAME nickname");
            SELECT("T.SEX sex");
            SELECT("T.AGE age");
            SELECT("T.PHONE phone");
            SELECT("T.EMAIL email");
            SELECT("T.HEAD_IMG_URL headImgUrl");
            SELECT("T.ENABLE enable");
        }

        private void buildFromSql() {
            FROM("PS_AUTH_USER T");
        }

        private void buildFilterSql(PsAppUserQuery query) {
            if (StringUtils.hasText(query.getUsername())) {
                WHERE("T.USERNAME LIKE " + SqlUtil.toSqlLikeString(query.getUsername()));
            }
            ORDER_BY(query);
        }

        static final String PAGE_SQL = "pages";

        public String pages(PsAppUserQuery query) {
            buildSelectSql();
            buildFromSql();
            buildFilterSql(query);
            return toPagingSql(query);
        }

        static final String COUNT_SQL = "count";

        public String count(PsAppUserQuery query) {
            buildFromSql();
            buildFilterSql(query);
            return toCountSql();
        }

        static final String BATCH_DELETE_SQL = "batchDelete";

        public String batchDelete(@Param("ids") List<Long> ids) {
            DELETE_FROM("PS_AUTH_USER");
            WHERE("ID IN " + SqlUtil.toSqlNumberSet(ids));
            return toString();
        }

    }

    @SelectProvider(method = PsAppUserSqlBuilder.PAGE_SQL, type = PsAppUserSqlBuilder.class)
    List<AbstractAppUser> pages(PsAppUserQuery query);

    /**
     * 计数
     *
     * @param query
     * @return
     */
    @SelectProvider(method = PsAppUserSqlBuilder.COUNT_SQL, type = PsAppUserSqlBuilder.class)
    int total(PsAppUserQuery query);


    /**
     * 根据登陆名查询
     *
     * @param username
     * @return
     */
    @Select({
            "SELECT T.ID id,T.USERNAME username,",
            "T.PASSWORD password,T.NICKNAME nickname,",
            "T.SEX sex,T.AGE age,",
            "T.PHONE phone,T.EMAIL email,",
            "T.HEAD_IMG_URL headImgUrl,T.ENABLE enabled",
            "FROM PS_AUTH_USER T",
            "WHERE T.USERNAME = #{username}"
    })
    AbstractAppUser getEntityByName(String username);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Select({
            "SELECT T.ID id,T.USERNAME username,",
            "T.PASSWORD password,T.NICKNAME nickname,",
            "T.SEX sex,T.AGE age,",
            "T.PHONE phone,T.EMAIL email,",
            "T.HEAD_IMG_URL headImgUrl,T.ENABLE enabled",
            "FROM PS_AUTH_USER T",
            "WHERE T.ID = #{id}"
    })
    AbstractAppUser getEntity(long id);

    /**
     * 新增
     *
     * @param appUser
     * @return
     */
    @Insert({
            "INSERT INTO PS_AUTH_USER (",
            "USERNAME,PASSWORD,NICKNAME,",
            "SEX,AGE,PHONE,EMAIL,HEAD_IMG_URL,",
            "ENABLE",
            ") VALUE (",
            "#{username},#{password},#{nickname},",
            "#{sex},#{age},#{phone},#{email},",
            "#{headImgUrl},#{enabled}",
            ")",
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    int insertEntity(AbstractAppUser appUser);

    /**
     * 修改
     *
     * @param appUser
     * @return
     */
    @Update({
            "UPDATE PS_AUTH_USER SET",
            "USERNAME=#{username},NICKNAME=#{nickname},",
            "SEX=#{sex},AGE=#{age},PHONE=#{phone},",
            "EMAIL=#{email},HEAD_IMG_URL=#{headImgUrl},",
            "ENABLE=#{enabled}",
            "WHERE ID = #{id}"
    })
    int updateEntity(AbstractAppUser appUser);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteProvider(type = PsAppUserSqlBuilder.class, method = PsAppUserSqlBuilder.BATCH_DELETE_SQL)
    int batchDelete(@Param("ids") List<Long> ids);

    /**
     * 重置密码
     *
     * @param id
     * @param pwd
     * @return
     */
    @Update({
            "UPDATE PS_AUTH_USER SET",
            "PASSWORD=#{pwd}",
            "WHERE ID = #{id}"
    })
    int resetPwd(@Param("id") long id, @Param("pwd") String pwd);

    /**
     * 绑定用户角色
     *
     * @param userId 用户Id
     * @param roleId 角色ID
     * @return
     */
    @Insert({
            "INSERT INTO PS_ROLE_USER (",
            "USER_ID,ROLE_ID",
            ") VALUE (",
            "#{userId},#{roleId}",
            ")",
    })
    int bindUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 删除用户的角色
     *
     * @param userId
     * @return
     */
    @Delete({
            "DELETE FROM PS_ROLE_USER",
            "WHERE USER_ID = #{userId}"
    })
    int deleteUserRole(Long userId);

    /**
     * 查询用户角色ID
     *
     * @param userId
     * @return
     */
    @Select({
            "SELECT ROLE_ID FROM PS_ROLE_USER",
            "WHERE USER_ID =#{userId}"
    })
    List<Long> getUserRoleIds(Long userId);

}
