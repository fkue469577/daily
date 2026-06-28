package com.bc.finance.modular.flow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_process_log")
public class SysProcessLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long taskId;
    private String operateNode;
    private String operateType;
    private String operator;
    private String operatorName;
    private String remark;
    private LocalDateTime createTime;
}