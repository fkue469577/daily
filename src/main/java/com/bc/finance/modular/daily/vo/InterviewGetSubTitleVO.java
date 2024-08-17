package com.bc.finance.modular.daily.vo;

import cn.hutool.core.bean.BeanUtil;
import com.bc.finance.modular.daily.entity.DailyInterviewTitle;
import lombok.Data;

import java.util.List;

@Data
public class InterviewGetSubTitleVO {

    private String id;

    private String name;

    public static List<InterviewGetSubTitleVO> from(List<DailyInterviewTitle> list) {

        return BeanUtil.copyToList(list, InterviewGetSubTitleVO.class);
    }
}