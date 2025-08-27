package com.bc.finance.modular.vant.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.common.exception.jwt.TokenUserHelper;
import com.bc.finance.modular.vant.entity.VantInterview;
import com.bc.finance.modular.vant.mapper.VantInterviewMapper;
import com.bc.finance.modular.vant.service.IVantInterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VantInterviewServiceImpl extends ServiceImpl<VantInterviewMapper, VantInterview> implements IVantInterviewService {

    @Resource
    VantInterviewMapper vantInterviewMapper;

    @Autowired
    TokenUserHelper tokenUserHelper;

    @Override
    public List<VantInterview> defaultChannels() {
        List<VantInterview> channels = vantInterviewMapper.defaultChannels();
        return channels;
    }

    @Override
    public List<VantInterview> allChannels() {
        List<VantInterview> channels = vantInterviewMapper.allChannels();
        return channels;
    }

    @Override
    public List<VantInterview> myChannels() {
        List<VantInterview> channels = vantInterviewMapper.myChannels(tokenUserHelper.getUserId());
        return channels;
    }

    @Override
    public List<VantInterview> articles(String channelId, Page page) {
        List<VantInterview> articles = vantInterviewMapper.articles(channelId, page);
        return articles;
    }
}