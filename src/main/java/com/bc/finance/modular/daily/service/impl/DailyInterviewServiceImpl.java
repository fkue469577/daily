package com.bc.finance.modular.daily.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.common.utils.ObjectId;
import com.bc.finance.modular.daily.entity.DailyInterview;
import com.bc.finance.modular.daily.entity.DailyNotes;
import com.bc.finance.modular.daily.mapper.DailyInterviewMapper;
import com.bc.finance.modular.daily.service.IDailyInterviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pcc
 * @since 2023-03-02
 */
@Service
public class DailyInterviewServiceImpl extends ServiceImpl<DailyInterviewMapper, DailyInterview> implements IDailyInterviewService {

    @Resource
    DailyInterviewMapper mapper;

    @Override
    public List paging(Page page, Map param) {
        List list = mapper.paging(page, param);
        return list;
    }


    @Override
    public void insert(DailyInterview interview) {
        interview.setCrtTime(LocalDateTime.now());
        interview.setId(ObjectId.getString());
        super.save(interview);
    }


    @Override
    public void update(DailyInterview interview) {

        super.updateById(interview);
    }
}
