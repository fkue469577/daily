package com.bc.finance.modular.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bc.finance.modular.base.entity.SysRoleMenu;

import java.util.List;

public interface IRoleMenuService extends IService<SysRoleMenu> {

    void removeByRoleIdAndMenuId(Integer roleId, Integer menuId);

    void insert(SysRoleMenu model);

    List<SysRoleMenu> listByRoleId(Integer roleId);
}
