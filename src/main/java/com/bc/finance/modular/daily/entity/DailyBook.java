package com.bc.finance.modular.daily.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 书本名称
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("daily_book")
@ApiModel(value = "DailyBook对象", description = "书本名称")
public class DailyBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private String name;

    private LocalDateTime crtTime;

    @ApiModelProperty("出版时间")
    private LocalDate publishedDate;

    private String remark;

    @ApiModelProperty("是否完结")
    private Boolean end;
}
