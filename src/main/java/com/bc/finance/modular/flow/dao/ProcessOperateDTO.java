package com.bc.finance.modular.flow.dao;

import lombok.Data;

@Data
public class ProcessOperateDTO {
    private Long taskId;
    private String remark;
    private String operator;
    private String operatorName;
}