package com.bc.finance.modular.vant.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("vant_interview")
public class VantInterview {

    @TableId()
    private String id;

    private String parentId;

    private String type;

    private String title;

    private String content;

    private String authorId;

    private String createTime;

    private String source;

    @TableField("`show`")
    private Boolean show;
}