package com.bc.finance.modular.base.mapper;

import com.bc.finance.modular.base.entity.SysRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * $!{table.comment} Mapper 接口
 * </p>
 *
 * @author pcc
 * @since 2022-06-02
 */
public interface RolePermissionMapper extends BaseMapper<SysRolePermission> {

    @Select("select * from sys_role_permission where role_id=#{roleId} and permission_id=#{permissionId}")
    SysRolePermission getByRoleIdAndPermissionId(Integer roleId, Integer permissionId);

    @Delete("delete from sys_role_permission where role_id=#{roleId} and permission_id in (select id from sys_permission where menu_id=#{menuId})")
    void removeByRoleIdAndMenuId(Integer roleId, Integer menuId);

    @Delete("delete from sys_role_permission where role_id=#{roleId} and permission_id=#{permissionId}")
    void removeByRoleIdAndPermissionId(Integer roleId, Integer permissionId);
}
