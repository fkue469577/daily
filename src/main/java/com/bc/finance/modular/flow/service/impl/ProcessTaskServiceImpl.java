package com.bc.finance.modular.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bc.finance.modular.flow.context.ProcessContext;
import com.bc.finance.modular.flow.dao.ProcessOperateDTO;
import com.bc.finance.modular.flow.entity.SysProcessLog;
import com.bc.finance.modular.flow.entity.SysProcessTask;
import com.bc.finance.modular.flow.enums.ProcessNodeEnum;
import com.bc.finance.modular.flow.enums.ProcessStateEnum;
import com.bc.finance.modular.flow.handler.ProcessHandler;
import com.bc.finance.modular.flow.mapper.SysProcessLogMapper;
import com.bc.finance.modular.flow.mapper.SysProcessTaskMapper;
import com.bc.finance.modular.flow.result.Result;
import com.bc.finance.modular.flow.service.ProcessTaskService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProcessTaskServiceImpl implements ProcessTaskService {

    @Resource
    private SysProcessTaskMapper taskMapper;
    @Resource
    private SysProcessLogMapper logMapper;
    @Resource
    private List<ProcessHandler> handlerList;

    @Override
    public Result<?> createDraft(SysProcessTask task) {
        task.setTaskSn("TASK_" + UUID.randomUUID().toString().replace("-",""));
        task.setCurrentState(ProcessStateEnum.DRAFT.getCode());
        task.setCreateTime(LocalDateTime.now());
        taskMapper.insert(task);
//        return Result.success("草稿创建成功",task.getId());
        return null;
    }

    @Override
    public Result<?> startProcess(ProcessOperateDTO dto) {
        SysProcessTask task = getTask(dto.getTaskId());
        if(!ProcessStateEnum.DRAFT.getCode().equals(task.getCurrentState())){
            return Result.fail("只能草稿状态发起");
        }
        ProcessContext context = buildContext(dto, task, ProcessNodeEnum.START_NODE);
        doHandle(context);
        updateTaskState(dto.getTaskId(), context.getNextState().getCode());
        addLog(context, ProcessNodeEnum.START_NODE.getName());
        return Result.success("发起成功");
    }

    @Override
    public Result<?> checkClassOperate(ProcessOperateDTO dto) {
        SysProcessTask task = getTask(dto.getTaskId());
        if(!ProcessStateEnum.CHECKING.getCode().equals(task.getCurrentState())){
            return Result.fail("当前不是校对中状态，无法操作");
        }
        ProcessContext context = buildContext(dto, task, ProcessNodeEnum.CHECK_CLASS);
        doHandle(context);
        updateTaskState(dto.getTaskId(), context.getNextState().getCode());
        addLog(context, ProcessNodeEnum.CHECK_CLASS.getName());
        return Result.success("操作成功");
    }

    @Override
    public Result<?> alarm112Operate(ProcessOperateDTO dto) {
        SysProcessTask task = getTask(dto.getTaskId());
        if(!ProcessStateEnum.ALARM_112.getCode().equals(task.getCurrentState())){
            return Result.fail("当前不是112接警状态");
        }
        ProcessContext context = buildContext(dto, task, ProcessNodeEnum.ALARM_112_NODE);
        doHandle(context);
        updateTaskState(dto.getTaskId(), context.getNextState().getCode());
        addLog(context, ProcessNodeEnum.ALARM_112_NODE.getName());
        return Result.success("分派成功");
    }

    @Override
    public Result<?> simingOperate(ProcessOperateDTO dto) {
        SysProcessTask task = getTask(dto.getTaskId());
        if(!ProcessStateEnum.BRANCH_DEAL.getCode().equals(task.getCurrentState())){
            return Result.fail("当前不是分局处理状态");
        }
        ProcessContext context = buildContext(dto, task, ProcessNodeEnum.SIMING_NODE);
        doHandle(context);
        updateTaskState(dto.getTaskId(), context.getNextState().getCode());
        addLog(context, ProcessNodeEnum.SIMING_NODE.getName());
        return Result.success("思明分局处理完成");
    }

    @Override
    public Result<?> huliOperate(ProcessOperateDTO dto) {
        SysProcessTask task = getTask(dto.getTaskId());
        if(!ProcessStateEnum.BRANCH_DEAL.getCode().equals(task.getCurrentState())){
            return Result.fail("当前不是分局处理状态");
        }
        ProcessContext context = buildContext(dto, task, ProcessNodeEnum.HULI_NODE);
        doHandle(context);
        updateTaskState(dto.getTaskId(), context.getNextState().getCode());
        addLog(context, ProcessNodeEnum.HULI_NODE.getName());
        return Result.success("湖里分局处理完成");
    }

    @Override
    public Result<?> cooperateOperate(ProcessOperateDTO dto) {
        SysProcessTask task = getTask(dto.getTaskId());
        if(!ProcessStateEnum.COOPERATE.getCode().equals(task.getCurrentState())){
            return Result.fail("当前不是协作中状态");
        }
        ProcessContext context = buildContext(dto, task, ProcessNodeEnum.COOPERATE_NODE);
        doHandle(context);
        updateTaskState(dto.getTaskId(), context.getNextState().getCode());
        addLog(context, ProcessNodeEnum.COOPERATE_NODE.getName());
        return Result.success("协作完成");
    }

    @Override
    public Result<?> finishOperate(ProcessOperateDTO dto) {
        SysProcessTask task = getTask(dto.getTaskId());
        ProcessContext context = buildContext(dto, task, ProcessNodeEnum.FINISH_NODE);
        doHandle(context);
        updateTaskState(dto.getTaskId(), ProcessStateEnum.FINISH.getCode());
        addLog(context, ProcessNodeEnum.FINISH_NODE.getName());
        return Result.success("流程已办结");
    }


    private SysProcessTask getTask(Long taskId){
        SysProcessTask task = taskMapper.selectById(taskId);
        if(task == null){
            throw new RuntimeException("任务不存在");
        }
        return task;
    }

    private ProcessContext buildContext(ProcessOperateDTO dto, SysProcessTask task, ProcessNodeEnum node){
        ProcessContext ctx = new ProcessContext();
        ctx.setTaskId(dto.getTaskId());
        ctx.setTaskSn(task.getTaskSn());
        ctx.setCurrentState(ProcessStateEnum.getByCode(task.getCurrentState()));
        ctx.setNode(node);
        ctx.setOperator(dto.getOperator());
        ctx.setOperatorName(dto.getOperatorName());
        ctx.setRemark(dto.getRemark());
        return ctx;
    }

    private void doHandle(ProcessContext context){
        ProcessHandler handler = handlerList.stream()
                .filter(h -> h.getNode() == context.getNode())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("无对应流程处理器"));
        handler.handle(context);
    }

    private void updateTaskState(Long taskId, Integer state){
        LambdaUpdateWrapper<SysProcessTask> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysProcessTask::getId, taskId)
                .set(SysProcessTask::getCurrentState, state)
                .set(SysProcessTask::getUpdateTime, LocalDateTime.now());
        taskMapper.update(null, wrapper);
    }

    private void addLog(ProcessContext context, String nodeName){
        SysProcessLog log = new SysProcessLog();
        log.setTaskId(context.getTaskId());
        log.setOperateNode(nodeName);
        log.setOperateType(context.getOperateType());
        log.setOperator(context.getOperator());
        log.setOperatorName(context.getOperatorName());
        log.setRemark(context.getRemark());
        log.setCreateTime(LocalDateTime.now());
        logMapper.insert(log);
    }
}