package com.bc.finance.modular.daily.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.modular.daily.entity.PersonIdentityInfo;
import com.bc.finance.modular.daily.mapper.PersonIdentityInfoMapper;
import com.bc.finance.modular.daily.service.IPersonIdentityInfoService;
import org.springframework.stereotype.Service;

@Service
public class PersonIdentityInfoServiceImpl extends ServiceImpl<PersonIdentityInfoMapper, PersonIdentityInfo> implements IPersonIdentityInfoService {
}
