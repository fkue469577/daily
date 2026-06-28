package com.bc.finance.modular.flow.handler;

import com.bc.finance.modular.flow.context.ProcessContext;
import com.bc.finance.modular.flow.enums.ProcessNodeEnum;
import com.bc.finance.modular.flow.enums.ProcessStateEnum;
import org.springframework.stereotype.Component;

@Component
public class StartHandler implements ProcessHandler {
    @Override
    public ProcessNodeEnum getNode() {
        return ProcessNodeEnum.START_NODE;
    }

    @Override
    public ProcessStateEnum supportState() {
        return ProcessStateEnum.DRAFT;
    }

    @Override
    public void handle(ProcessContext context) {
        context.setNextState(ProcessStateEnum.CHECKING);
        context.setOperateType("发起流程");
    }
}