package com.bc.finance.modular.interview.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
@TableName("interview_javaguide")
@ApiModel(value = "DailyBook对象", description = "书本名称")
public class InterviewJavaguide implements Serializable {

    @TableId
    private String id;

    private String parentId;

    private String prefix;

    private String title;

    private Boolean leaf;

    private String content;

    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime crtTime;

}