package com.bc.finance.modular.flow.handler;

import com.bc.finance.modular.flow.context.ProcessContext;
import com.bc.finance.modular.flow.enums.ProcessNodeEnum;
import com.bc.finance.modular.flow.enums.ProcessStateEnum;
import org.springframework.stereotype.Component;

@Component
public class CooperateHandler implements ProcessHandler {
    @Override
    public ProcessNodeEnum getNode() {
        return ProcessNodeEnum.COOPERATE_NODE;
    }

    @Override
    public ProcessStateEnum supportState() {
        return ProcessStateEnum.COOPERATE;
    }

    @Override
    public void handle(ProcessContext context) {
        context.setNextState(ProcessStateEnum.FINISH);
        context.setOperateType("协作完成");
    }
}