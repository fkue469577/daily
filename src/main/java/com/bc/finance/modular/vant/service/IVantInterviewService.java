package com.bc.finance.modular.vant.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bc.finance.modular.vant.entity.VantInterview;

import java.util.List;

public interface IVantInterviewService extends IService<VantInterview> {
    List<VantInterview> defaultChannels();

    List<VantInterview> allChannels();

    List<VantInterview> myChannels();

    List<VantInterview> articles(String channelId, Page page);
}
