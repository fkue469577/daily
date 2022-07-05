package com.bc.finance.modular.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.modular.base.entity.BaseUser;
import com.bc.finance.modular.base.mapper.BaseUserMapper;
import com.bc.finance.modular.base.service.IBaseUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements IBaseUserService {

    @Resource
    BaseUserMapper mapper;

    @Override
    public BaseUser getByUsername(String username) {

        return mapper.getByUsername(username);
    }
}
