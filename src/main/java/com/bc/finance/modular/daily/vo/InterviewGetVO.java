package com.bc.finance.modular.daily.vo;

import cn.hutool.core.bean.BeanUtil;
import com.bc.finance.modular.daily.entity.DailyInterview;
import lombok.Data;

@Data
public class InterviewGetVO {

    private String id;

    private String titleId;

    private String subTitleId;

    private String name;

    private String context;

    public static InterviewGetVO convert(DailyInterview interview) {
        InterviewGetVO vo = BeanUtil.copyProperties(interview, InterviewGetVO.class);
        return vo;
    }
}