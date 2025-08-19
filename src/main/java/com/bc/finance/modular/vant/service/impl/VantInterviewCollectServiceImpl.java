package com.bc.finance.modular.vant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.common.exception.jwt.TokenUserHelper;
import com.bc.finance.modular.vant.entity.VantInterviewCollect;
import com.bc.finance.modular.vant.mapper.VantInterviewCollectMapper;
import com.bc.finance.modular.vant.service.IVantInterviewCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class VantInterviewCollectServiceImpl extends ServiceImpl<VantInterviewCollectMapper, VantInterviewCollect> implements IVantInterviewCollectService {

    @Resource
    VantInterviewCollectMapper vantInterviewCollectMapper;

    @Autowired
    TokenUserHelper tokenUserHelper;

    @Override
    public boolean addChannels(String interviewId) {
        VantInterviewCollect vantInterviewCollect = this.getByInterviewId(interviewId);
        if (vantInterviewCollect==null) {
            vantInterviewCollect = new VantInterviewCollect();
            vantInterviewCollect.setInterviewId(interviewId);
            vantInterviewCollect.setUserId(tokenUserHelper.getUserId());
            vantInterviewCollect.setCreateTime(LocalDateTime.now());
            return this.save(vantInterviewCollect);
        }
        return true;
    }

    private VantInterviewCollect getByInterviewId(String interviewId) {
        VantInterviewCollect collect = vantInterviewCollectMapper.getByInterviewId(interviewId);
        return collect;
    }
}