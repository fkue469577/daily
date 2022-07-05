package com.bc.finance.modular.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.modular.base.entity.BaseUser;
import com.bc.finance.modular.base.entity.BaseUserRole;
import com.bc.finance.modular.base.mapper.BaseUserMapper;
import com.bc.finance.modular.base.mapper.BaseUserRoleMapper;
import com.bc.finance.modular.base.service.IBaseUserRoleService;
import com.bc.finance.modular.base.service.IBaseUserService;
import org.springframework.stereotype.Service;

@Service
public class BaseUserRoleServiceImpl extends ServiceImpl<BaseUserRoleMapper, BaseUserRole> implements IBaseUserRoleService {
}
