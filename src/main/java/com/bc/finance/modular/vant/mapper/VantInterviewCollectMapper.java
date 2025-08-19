package com.bc.finance.modular.vant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bc.finance.modular.vant.entity.VantInterviewCollect;
import org.apache.ibatis.annotations.Select;

public interface VantInterviewCollectMapper extends BaseMapper<VantInterviewCollect> {

    @Select("select * from vant_interview_collect where interview_id=#{interviewId}")
    VantInterviewCollect getByInterviewId(String interviewId);
}
