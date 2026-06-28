package com.bc.finance.modular.flow.handler;


import com.bc.finance.modular.flow.context.ProcessContext;
import com.bc.finance.modular.flow.enums.ProcessNodeEnum;
import com.bc.finance.modular.flow.enums.ProcessStateEnum;
import org.springframework.stereotype.Component;

@Component
public class CheckClassHandler implements ProcessHandler {
    @Override
    public ProcessNodeEnum getNode() {
        return ProcessNodeEnum.CHECK_CLASS;
    }

    @Override
    public ProcessStateEnum supportState() {
        return ProcessStateEnum.CHECKING;
    }

    @Override
    public void handle(ProcessContext context) {
        String remark = context.getRemark();
        if("退回".equals(remark)){
            context.setNextState(ProcessStateEnum.BACK);
            context.setOperateType("校对退回");
        }else if("拒绝".equals(remark)){
            context.setNextState(ProcessStateEnum.REJECT);
            context.setOperateType("校对拒绝");
        }else{
            context.setNextState(ProcessStateEnum.ALARM_112);
            context.setOperateType("校对签收");
        }
    }
}