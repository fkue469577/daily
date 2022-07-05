package com.bc.finance.modular.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName
@Data
public class BaseUser {
    @TableId(type= IdType.INPUT)
    private String id;

    private String username;

    private String password;

    private String name;

    private LocalDate birthday;

    private String address;

    private String phone;

    private String email;

    private Boolean status;

    private String description;

    private LocalDateTime crtTime;
}
