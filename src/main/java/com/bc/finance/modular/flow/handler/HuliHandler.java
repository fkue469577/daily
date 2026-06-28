package com.bc.finance.modular.flow.handler;

import com.bc.finance.modular.flow.context.ProcessContext;
import com.bc.finance.modular.flow.enums.ProcessNodeEnum;
import com.bc.finance.modular.flow.enums.ProcessStateEnum;
import org.springframework.stereotype.Component;

@Component
public class HuliHandler implements ProcessHandler {
    @Override
    public ProcessNodeEnum getNode() {
        return ProcessNodeEnum.HULI_NODE;
    }

    @Override
    public ProcessStateEnum supportState() {
        return ProcessStateEnum.BRANCH_DEAL;
    }

    @Override
    public void handle(ProcessContext context) {
        context.setNextState(ProcessStateEnum.COOPERATE);
        context.setOperateType("湖里分局处理");
    }
}