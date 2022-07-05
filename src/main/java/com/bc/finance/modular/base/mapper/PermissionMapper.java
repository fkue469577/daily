package com.bc.finance.modular.base.mapper;

import com.bc.finance.modular.base.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * $!{table.comment} Mapper 接口
 * </p>
 *
 * @author pcc
 * @since 2022-06-02
 */
public interface PermissionMapper extends BaseMapper<SysPermission> {

    List<Map> getPermissionByRoldid(@Param("roleId") Integer roleId);

    @Select("select * from sys_permission where menu_id=#{menuId} and user_enabled=1")
    List<SysPermission> listByMenuId(Integer menuId);

    @Select("select * from sys_permission where sys_enabled=1")
    List<SysPermission> listSys();

    List<SysPermission> listByAdminId(@Param("sclAdminId") Long sclAdminId);

    List<Map> listSysPermissionExpendRole(Integer roleId);

    @Select("select * from base_permission bp where exists (select 1 from base_user_role bur inner join base_role_permission brp on bur.role_id=brp.role_id where bp.id=brp.permission_id and user_id=#(userId))")
    List<SysPermission> listByUserId(String userId);
}
