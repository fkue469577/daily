package com.bc.finance.modular.vant.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("vant_interview_collect")
public class VantInterviewCollect {

    private Integer id;

    private String interviewId;

    private String userId;

    private LocalDateTime createTime;

}
