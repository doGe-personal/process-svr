package com.process.auth.app.sys.mapper;

import com.process.auth.app.sys.entity.AbstractAppMenuEntity;
import com.process.common.database.domain.PsSql;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author Danfeng
 * @since 2018/7/23
 */
public interface PsAppMenuMapper {

    class SqlBuilder extends PsSql {

        private static final String MENUS_OF_SQL = "menusOfSql";

        public String menusOfSql() {
            SELECT("distinct sm.menu_Id menuId, sm.upper_Id upperId, sm.menu_Uri menuUri, " +
                    "sm.menu_Ico menuIco, sm.menu_Label menuLabel, sm.menu_Type menuType");
            FROM("PS_MENU sm");
            INNER_JOIN("PS_ROLE_MENU rm on rm.menu_id = sm.MENU_ID and rm.role_id = (select role_id from PS_ROLE_USER ru where ru.USER_ID = #{userId})");
            ORDER_BY("menuId");
            return toString();
        }

        private static final String MENUS_OF_ROLE_SQL = "menusOfRoleSql";

        public String menusOfRoleSql() {
            SELECT("distinct sm.menu_Id menuId, sm.upper_Id upperId, sm.menu_Uri menuUri, " +
                    "sm.menu_Ico menuIco, sm.menu_Label menuLabel, sm.menu_Type menuType");
            FROM("PS_MENU sm");
            INNER_JOIN("PS_ROLE_MENU rm on rm.menu_id = sm.MENU_ID and rm.role_id = #{roleId}");
            ORDER_BY("menuId");
            return toString();
        }

    }

    /**
     * 根据用户ID获取能访问的菜单一览
     *
     * @param userId 用户ID
     * @return 菜单一览
     */
    @SelectProvider(type = SqlBuilder.class, method = SqlBuilder.MENUS_OF_SQL)
    List<AbstractAppMenuEntity> menusOf(long userId);

    /**
     * 根据角色Id查询
     *
     * @param roleId
     * @return
     */
    @SelectProvider(type = SqlBuilder.class, method = SqlBuilder.MENUS_OF_ROLE_SQL)
    List<AbstractAppMenuEntity> menusByRoleId(long roleId);

    /**
     * 查询所有菜单
     *
     * @return
     */
    @Select({
            "SELECT distinct sm.menu_Id menuId, ",
            "sm.upper_Id upperId, sm.menu_Uri menuUri, ",
            "sm.menu_Ico menuIco, sm.menu_Label menuLabel,",
            "sm.menu_Type menuType",
            "FROM PS_MENU sm"
    })
    List<AbstractAppMenuEntity> findList();


}
