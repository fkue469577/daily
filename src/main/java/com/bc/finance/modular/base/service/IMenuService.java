package com.bc.finance.modular.base.service;

import com.bc.finance.modular.base.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* <p>
    * 菜单表 服务类
    * </p>
*
* @author pcc
* @since 2022-06-02
*/
public interface IMenuService extends IService<SysMenu> {

    List listMenuListExpendRole(Integer roleId);

    List<SysMenu> listByUserId(String userId);
}
