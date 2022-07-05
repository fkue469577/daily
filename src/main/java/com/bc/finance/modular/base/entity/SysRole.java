package com.bc.finance.modular.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName
@Data
public class SysRole {
    @TableId(type= IdType.AUTO)
    private Integer id;

    private Integer num;

    // 编号
    private String code;

    // 父编号
    private String pcode;

    // 当前编号的所有父编号
    private String pcodes;

    // 角色名称
    private String name;

    // 是否超级管理员
    private Boolean isadmin;

    private String tips;

    private String sclId;

}
