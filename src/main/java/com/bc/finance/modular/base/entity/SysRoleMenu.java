package com.bc.finance.modular.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName
@Data
public class SysRoleMenu {
    @TableId(type= IdType.AUTO)
    private Integer id;

    private Integer menuId;

    private Integer roleId;
}
