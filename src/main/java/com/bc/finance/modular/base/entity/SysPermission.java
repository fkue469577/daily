package com.bc.finance.modular.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import io.swagger.annotations.ApiModelProperty;

/**
* @Description：权限表
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_permission")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    @TableField("id")
    private Integer id;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "值")
    @TableField("value")
    private String value;

    @ApiModelProperty(value = "url地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "简介")
    @TableField("intro")
    private String intro;

    @ApiModelProperty(value = "菜单id")
    @TableField("menu_id")
    private Integer menuId;

    @ApiModelProperty(value = "菜单排序号")
    @TableField("num")
    private Integer num;

    @ApiModelProperty(value = "管理员是否可获取")
    @TableField("sys_enabled")
    private Boolean sysEnabled;

    @ApiModelProperty(value = "普通用户是否可获取")
    @TableField("user_enabled")
    private Boolean userEnabled;
}

