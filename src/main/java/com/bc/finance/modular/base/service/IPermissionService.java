package com.bc.finance.modular.base.service;

import com.bc.finance.modular.base.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
* <p>
    * 权限表 服务类
    * </p>
*
* @author pcc
* @since 2022-06-02
*/
public interface IPermissionService extends IService<SysPermission> {
    /**
     * 获取所有的权限,  并检验是否已经跟 roleid 匹配上
     * @param roleId
     * @return
     */
    List<Map> getPermissionByRoldid(Integer roleId);

    List<SysPermission> listByMenuId(Integer menuId);

    List<Map> listSysPermissionExpendRole(Integer roleId);

    List<SysPermission> listByUserId(String userId);
}
