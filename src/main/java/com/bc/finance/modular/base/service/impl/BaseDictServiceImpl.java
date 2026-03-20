package com.bc.finance.modular.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.modular.base.entity.BaseDict;
import com.bc.finance.modular.base.mapper.BaseDictMapper;
import com.bc.finance.modular.base.service.IBaseDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BaseDictServiceImpl extends ServiceImpl<BaseDictMapper, BaseDict> implements IBaseDictService {

    @Resource
    BaseDictMapper mapper;

}
