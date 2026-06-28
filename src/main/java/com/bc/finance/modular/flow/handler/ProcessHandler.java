package com.bc.finance.modular.flow.handler;

import com.bc.finance.modular.flow.context.ProcessContext;
import com.bc.finance.modular.flow.enums.ProcessNodeEnum;
import com.bc.finance.modular.flow.enums.ProcessStateEnum;

public interface ProcessHandler {
    ProcessNodeEnum getNode();
    ProcessStateEnum supportState();
    void handle(ProcessContext context);
}