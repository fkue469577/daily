package com.bc.finance.modular.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDict {

    @TableId(type= IdType.ASSIGN_UUID)
    private String id;

    private String dictCode;

    private String dictName;

    private String parentCode;

    private String typeCode;

    private String typeName;

    private Integer sort;

    private Integer level;

    private Boolean deleted;

    private LocalDateTime createTime;

    private String extendFile1;

    private String extendFile2;

    private String extendFile3;

    private String extendFile4;

    private String extendFile5;

    private String extendFile6;

    private String extendFile7;

    private String extendFile8;

    private String extendFile9;

    private String extendFile10;
}