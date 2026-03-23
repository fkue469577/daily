package com.bc.finance.modular.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.modular.base.entity.BaseDict;
import com.bc.finance.modular.base.mapper.BaseDictMapper;
import com.bc.finance.modular.base.service.IBaseDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class BaseDictServiceImpl extends ServiceImpl<BaseDictMapper, BaseDict> implements IBaseDictService {

    @Resource
    BaseDictMapper mapper;

    @Override
    public List<BaseDict> listByTypeCode(String typeCode) {
        List<BaseDict> list = this.lambdaQuery().eq(BaseDict::getTypeCode, typeCode).list();
        return list;
    }
}
