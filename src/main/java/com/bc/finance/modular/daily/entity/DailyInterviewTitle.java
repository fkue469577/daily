package com.bc.finance.modular.daily.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author pcc
 * @since 2023-03-02
 */
@Getter
@Setter
@TableName("daily_interview_title")
@ApiModel(value = "DailyInterviewTitle对象", description = "")
public class DailyInterviewTitle implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private Integer sort;

    @TableField("`describe`")
    private String describe;

    private LocalDateTime crtTime;


}
