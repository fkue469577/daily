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
 * 公司表
 * </p>
 *
 * @author pcc
 * @since 2022-09-22
 */
@Getter
@Setter
@TableName("daily_company")
@ApiModel(value = "DailyCompany对象", description = "公司表")
public class DailyCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private String name;

    private LocalDateTime crtTime;

}
