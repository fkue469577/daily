package com.bc.finance.modular.base.service.impl;

import com.bc.finance.modular.base.entity.SysPermission;
import com.bc.finance.modular.base.entity.SysRolePermission;
import com.bc.finance.modular.base.mapper.RolePermissionMapper;
import com.bc.finance.modular.base.service.IPermissionService;
import com.bc.finance.modular.base.service.IRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* <p>
    * 角色 权限关联表 服务实现类
    * </p>
*
* @author pcc
* @since 2022-06-02
*/
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, SysRolePermission> implements IRolePermissionService {

    @Resource
    private RolePermissionMapper mapper;

    @Autowired
    private IPermissionService permissionService;

    @Override
    public void insert(SysRolePermission rolePermission) {

        SysRolePermission temp = this.getByRoleIdAndPermissionId(rolePermission.getRoleId(), rolePermission.getPermissionId());
        if(temp!=null) {
            return;
        }

        mapper.insert(rolePermission);
    }

    @Override
    public SysRolePermission getByRoleIdAndPermissionId(Integer roleId, Integer permissionId) {

        return mapper.getByRoleIdAndPermissionId(roleId, permissionId);
    }

    @Override
    public void removeByRoleIdAndMenuId(Integer roleId, Integer menuId) {
        mapper.removeByRoleIdAndMenuId(roleId, menuId);
    }

    @Override
    public void removeByRoleIdAndPermissionId(Integer roleId, Integer permissionId) {
        mapper.removeByRoleIdAndPermissionId(roleId, permissionId);
    }

    @Override
    public void addByRoleIdAndMenuId(Integer roleId, Integer menuId) {

        List<SysPermission> permissionList = permissionService.listByMenuId(menuId);
        List<SysRolePermission> rolePermissionList = permissionList.stream()
                .map(e -> new SysRolePermission(null, roleId, e.getId()))
                .collect(Collectors.toList());

        super.saveBatch(rolePermissionList);
    }
}
