package com.bc.finance.modular.daily.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    private Integer zIndex;

    private LocalDateTime crtTime;

}
