package com.bc.finance.modular.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.common.exception.business.BusinessException;
import com.bc.finance.modular.base.entity.SysRole;
import com.bc.finance.modular.base.mapper.RoleMapper;
import com.bc.finance.modular.base.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, SysRole> implements IRoleService {

    @Resource
    RoleMapper mapper;

    @Override
    public boolean removeById(Integer id) {
        SysRole role = this.getById(id);
        if("administrator".equals(role.getCode())) {

            throw new BusinessException("系统内置的角色不能删除");
        }

        super.removeById(id);
        return true;
    }

    @Override
    public List<SysRole> listSystem() {

        return mapper.listSystem();
    }

    @Override
    public List<SysRole> listByUserId(String userId) {

        return mapper.listByUserId(userId);
    }
}
