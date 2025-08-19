package com.bc.finance.modular.vant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bc.finance.modular.vant.entity.VantInterviewCollect;

public interface IVantInterviewCollectService extends IService<VantInterviewCollect> {
    boolean addChannels(String interviewId);
}
