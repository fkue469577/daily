package com.bc.finance.modular.daily.entity;

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
@TableName("daily_interview")
@ApiModel(value = "DailyInterview对象", description = "")
public class DailyInterview implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty("daily_interview_title 主键ID")
    private String titleId;

    private String name;

    private String context;

    private Integer sort;

    private LocalDateTime crtTime;


}
