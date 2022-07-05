package com.bc.finance.modular.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.modular.base.entity.SysRoleMenu;
import com.bc.finance.modular.base.mapper.RoleMenuMapper;
import com.bc.finance.modular.base.service.IRoleMenuService;
import com.bc.finance.modular.base.service.IRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, SysRoleMenu> implements IRoleMenuService {

    @Resource
    RoleMenuMapper mapper;

    @Autowired
    IRolePermissionService rolePermissionService;

    @Override
    @Transactional
    public void removeByRoleIdAndMenuId(Integer roleId, Integer menuId) {

        mapper.removeByRoleIdAndMenuId(roleId, menuId);

        rolePermissionService.removeByRoleIdAndMenuId(roleId, menuId);
    }

    @Override
    @Transactional
    public void insert(SysRoleMenu model) {
        mapper.insert(model);

        rolePermissionService.addByRoleIdAndMenuId(model.getRoleId(), model.getMenuId());
    }

    @Override
    public List<SysRoleMenu> listByRoleId(Integer roleId) {

        return mapper.listByRoleId(roleId);
    }

}
