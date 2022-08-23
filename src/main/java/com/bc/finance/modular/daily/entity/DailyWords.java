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
 * 单词表
 * </p>
 *
 * @author pcc
 * @since 2022-08-23
 */
@Getter
@Setter
@TableName("daily_words")
@ApiModel(value = "DailyWords对象", description = "单词表")
public class DailyWords implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String word;

    @TableField("`explain`")
    private String explain;

    private Boolean completed;

    private LocalDateTime crtTime;


}
