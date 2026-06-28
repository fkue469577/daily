package com.bc.finance.modular.flow.controller;

import com.bc.finance.modular.flow.dao.ProcessOperateDTO;
import com.bc.finance.modular.flow.entity.SysProcessTask;
import com.bc.finance.modular.flow.result.Result;
import com.bc.finance.modular.flow.service.ProcessTaskService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/process")
public class ProcessTaskController {

    @Resource
    private ProcessTaskService processTaskService;

    @PostMapping("/draft")
    public Result<?> draft(@RequestBody SysProcessTask task){
        return processTaskService.createDraft(task);
    }

    @PostMapping("/start")
    public Result<?> start(@RequestBody ProcessOperateDTO dto){
        return processTaskService.startProcess(dto);
    }

    @PostMapping("/check/operate")
    public Result<?> checkOperate(@RequestBody ProcessOperateDTO dto){
        return processTaskService.checkClassOperate(dto);
    }

    @PostMapping("/alarm112/operate")
    public Result<?> alarm112(@RequestBody ProcessOperateDTO dto){
        return processTaskService.alarm112Operate(dto);
    }

    @PostMapping("/siming/operate")
    public Result<?> siming(@RequestBody ProcessOperateDTO dto){
        return processTaskService.simingOperate(dto);
    }

    @PostMapping("/huli/operate")
    public Result<?> huli(@RequestBody ProcessOperateDTO dto){
        return processTaskService.huliOperate(dto);
    }

    @PostMapping("/cooperate/operate")
    public Result<?> cooperate(@RequestBody ProcessOperateDTO dto){
        return processTaskService.cooperateOperate(dto);
    }

    @PostMapping("/finish/operate")
    public Result<?> finish(@RequestBody ProcessOperateDTO dto){
        return processTaskService.finishOperate(dto);
    }
}