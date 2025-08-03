package com.bc.finance.modular.interview.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.modular.interview.entity.InterviewJavaguide;
import com.bc.finance.modular.interview.mapper.InterviewJavaguideMapper;
import com.bc.finance.modular.interview.service.IInterviewJavaguideService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class InterviewJavaguideServiceImpl extends ServiceImpl<InterviewJavaguideMapper, InterviewJavaguide> implements IInterviewJavaguideService {
    @Override
    public List<InterviewJavaguide> listRoot() {
        QueryWrapper<InterviewJavaguide> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(InterviewJavaguide::getParentId, "");
        List<InterviewJavaguide> list = this.list(queryWrapper);
        return list;
    }

    @Override
    public List<InterviewJavaguide> articles(String parentId, Page page) {
        Map map = new HashMap<>();
        map.put("parentId", parentId);
        List<InterviewJavaguide> articles = baseMapper.articles(page, map);
        return articles;
    }
}
