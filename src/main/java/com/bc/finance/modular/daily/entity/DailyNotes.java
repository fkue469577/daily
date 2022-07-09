package com.bc.finance.modular.daily.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 笔记表
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
@Getter
@Setter
@TableName("daily_notes")
@ApiModel(value = "DailyNotes对象", description = "笔记表")
public class DailyNotes implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private String title;

    private String context;

    private LocalDateTime crtTime;


}
