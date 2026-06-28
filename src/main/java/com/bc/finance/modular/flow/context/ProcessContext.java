package com.bc.finance.modular.flow.context;

import com.bc.finance.modular.flow.enums.ProcessNodeEnum;
import com.bc.finance.modular.flow.enums.ProcessStateEnum;
import lombok.Data;

@Data
public class ProcessContext {
    private Long taskId;
    private String taskSn;
    private ProcessStateEnum currentState;
    private ProcessNodeEnum node;
    private String operator;
    private String operatorName;
    private String remark;
    private String operateType;
    private ProcessStateEnum nextState;
}