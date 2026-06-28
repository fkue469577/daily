package com.bc.finance.modular.flow.handler;

import com.bc.finance.modular.flow.context.ProcessContext;
import com.bc.finance.modular.flow.enums.ProcessNodeEnum;
import com.bc.finance.modular.flow.enums.ProcessStateEnum;
import org.springframework.stereotype.Component;

@Component
public class Alarm112Handler implements ProcessHandler {
    @Override
    public ProcessNodeEnum getNode() {
        return ProcessNodeEnum.ALARM_112_NODE;
    }

    @Override
    public ProcessStateEnum supportState() {
        return ProcessStateEnum.ALARM_112;
    }

    @Override
    public void handle(ProcessContext context) {
        context.setNextState(ProcessStateEnum.BRANCH_DEAL);
        context.setOperateType("112接警分派");
    }
}