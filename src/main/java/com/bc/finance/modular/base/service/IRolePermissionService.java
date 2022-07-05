package com.bc.finance.modular.base.service;

import com.bc.finance.modular.base.entity.SysRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
    * 角色 权限关联表 服务类
    * </p>
*
* @author pcc
* @since 2022-06-02
*/
public interface IRolePermissionService extends IService<SysRolePermission> {

    void insert(SysRolePermission rolePermission);

    SysRolePermission getByRoleIdAndPermissionId(Integer roleId, Integer permissionId);

    void removeByRoleIdAndMenuId(Integer roleId, Integer menuId);

    void removeByRoleIdAndPermissionId(Integer roleId, Integer permissionId);

    void addByRoleIdAndMenuId(Integer roleId, Integer menuId);
}
