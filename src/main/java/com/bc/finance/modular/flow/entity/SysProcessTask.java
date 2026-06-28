package com.bc.finance.modular.flow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_process_task")
public class SysProcessTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskSn;
    private String title;
    private String content;
    private Integer currentState;
    private String createUser;
    private String createUserName;
    private String deptCode;
    private String attachmentIds;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}