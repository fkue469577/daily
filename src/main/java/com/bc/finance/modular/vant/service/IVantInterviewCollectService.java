package com.bc.finance.modular.vant.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bc.finance.modular.vant.entity.VantInterviewCollect;

@DS("myvant")
public interface IVantInterviewCollectService extends IService<VantInterviewCollect> {
    boolean addChannels(String interviewId);
}
