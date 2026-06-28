package com.bc.finance.modular.flow.service;


import com.bc.finance.modular.flow.dao.ProcessOperateDTO;
import com.bc.finance.modular.flow.entity.SysProcessTask;
import com.bc.finance.modular.flow.result.Result;

public interface ProcessTaskService {
    Result<?> createDraft(SysProcessTask task);
    Result<?> startProcess(ProcessOperateDTO dto);
    Result<?> checkClassOperate(ProcessOperateDTO dto);
    Result<?> alarm112Operate(ProcessOperateDTO dto);
    Result<?> simingOperate(ProcessOperateDTO dto);
    Result<?> huliOperate(ProcessOperateDTO dto);
    Result<?> cooperateOperate(ProcessOperateDTO dto);
    Result<?> finishOperate(ProcessOperateDTO dto);
}