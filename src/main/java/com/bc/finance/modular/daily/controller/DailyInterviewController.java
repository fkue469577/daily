package com.bc.finance.modular.daily.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.common.helper.PageHelper;
import com.bc.finance.common.msg.BaseResponse;
import com.bc.finance.common.msg.ObjectResponse;
import com.bc.finance.common.msg.TableResponse;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.daily.entity.DailyInterview;
import com.bc.finance.modular.daily.service.IDailyInterviewService;
import com.bc.finance.modular.daily.service.IDailyInterviewTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pcc
 * @since 2023-03-02
 */
@Controller
@RequestMapping("/daily/interview")
public class DailyInterviewController {


    @Autowired
    IDailyInterviewService interviewService;

    @Autowired
    IDailyInterviewTitleService interviewTitleService;


    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("titles", interviewTitleService.list());
        return "daily/interview/index";
    }

    @GetMapping("/page")
    @ResponseBody
    public TableResponse page(@RequestParam Map param) {

        Page page = PageHelper.defaultPage();
        List list = interviewService.paging(page, param);

        return new TableResponse(page.getTotal(), list);
    }

    @PostMapping("/save")
    @ResponseBody
    public BaseResponse save(@RequestBody DailyInterview interview) {

        if(StringUtils.isBlank(interview.getId())) {
            interviewService.insert(interview);
        } else {
            interviewService.update(interview);
        }

        return BaseResponse.success();
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ObjectResponse get(@PathVariable String id) {

        DailyInterview interview = interviewService.getById(id);

        return new ObjectResponse(interview);
    }

}
