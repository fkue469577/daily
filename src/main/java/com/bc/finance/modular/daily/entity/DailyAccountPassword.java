package com.bc.finance.modular.daily.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 账号密码表
 * </p>
 *
 * @author pcc
 * @since 2022-09-22
 */
@Data
@NoArgsConstructor
@TableName("daily_account_password")
@ApiModel(value = "DailyAccountPassword对象", description = "账号密码表")
public class DailyAccountPassword implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    @TableField("company_id")
    private String companyId;

    @TableField("url")
    private String url;

    @TableField("account")
    private String account;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("说明")
    @TableField("`describe`")
    private String describe;

    private LocalDateTime crtTime;


}
