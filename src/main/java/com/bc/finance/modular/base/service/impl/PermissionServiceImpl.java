package com.bc.finance.modular.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.modular.base.entity.SysPermission;
import com.bc.finance.modular.base.mapper.PermissionMapper;
import com.bc.finance.modular.base.service.IPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* <p>
    * 权限表 服务实现类
    * </p>
*
* @author pcc
* @since 2022-06-02
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, SysPermission> implements IPermissionService {

    @Resource
    private PermissionMapper mapper;

    @Override
    public List<Map> getPermissionByRoldid(Integer roleId) {
        return mapper.getPermissionByRoldid(roleId);
    }

    @Override
    public List<SysPermission> listByMenuId(Integer menuId) {

        return mapper.listByMenuId(menuId);
    }

    @Override
    public List<Map> listSysPermissionExpendRole(Integer roleId) {

        return mapper.listSysPermissionExpendRole(roleId);
    }

    @Override
    public List<SysPermission> listByUserId(String userId) {

        return mapper.listByUserId(userId);
    }
}
