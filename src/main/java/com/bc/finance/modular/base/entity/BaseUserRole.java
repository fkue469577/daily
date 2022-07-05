package com.bc.finance.modular.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName
@Data
public class BaseUserRole {

    @TableId(type= IdType.INPUT)
    private String id;

    private String userId;

    private Integer roleId;

}
