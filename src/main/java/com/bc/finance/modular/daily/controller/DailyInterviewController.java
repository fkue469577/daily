package com.bc.finance.modular.daily.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.common.helper.PageHelper;
import com.bc.finance.common.msg.BaseResponse;
import com.bc.finance.common.msg.ObjectResponse;
import com.bc.finance.common.msg.TableResponse;
import com.bc.finance.common.utils.ObjectUtils;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.daily.bo.InterviewTitleTreeVO;
import com.bc.finance.modular.daily.entity.DailyInterview;
import com.bc.finance.modular.daily.entity.DailyInterviewTitle;
import com.bc.finance.modular.daily.service.IDailyInterviewService;
import com.bc.finance.modular.daily.service.IDailyInterviewTitleService;
import com.bc.finance.modular.daily.vo.InterviewGetSubTitleVO;
import com.bc.finance.modular.daily.vo.InterviewGetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        model.addAttribute("titles", interviewTitleService.listRoot());
        return "daily/interview/index";
    }


    @GetMapping("mobile")
    public String mobile(Model model) {
        model.addAttribute("titles", interviewTitleService.listRoot());
        return "daily/interview/mobile";
    }

    @GetMapping("/condition")
    @ResponseBody
    public ObjectResponse condition() {
        Map map = new HashMap();
        List<InterviewTitleTreeVO> titleList = interviewTitleService.tree();
        map.put("titleList", titleList);
        return new ObjectResponse(map);
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
        return new ObjectResponse<>(interview.getId());
    }


    @GetMapping("/get/{id}")
    @ResponseBody
    public ObjectResponse get(@PathVariable String id) {
        DailyInterview interview = interviewService.getById(id);
        InterviewGetVO vo = InterviewGetVO.convert(interview);
        StringUtils.notBlankRunnable(interview.getTitleId(), ()->{
            DailyInterviewTitle title = interviewTitleService.getById(vo.getTitleId());
            if(StringUtils.isNotBlank(title.getParentId())) {
                vo.setSubTitleId(vo.getTitleId());
                vo.setTitleId(title.getParentId());
            }
        });
        return new ObjectResponse(vo);
    }


    @GetMapping("/mobile/detail/{id}")
    public String mobileDetail(@PathVariable String id, Model model) {
        DailyInterview interview = interviewService.getById(id);
        interview = ObjectUtils.nullSupplier(interview, DailyInterview::new);
        model.addAttribute("interview", interview);
        model.addAttribute("aa", "aa-1");
        return "daily/interview/mobileDetail";
    }
}
