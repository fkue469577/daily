package com.bc.finance.modular.daily.controller;


import com.bc.finance.common.msg.ObjectResponse;
import com.bc.finance.modular.daily.entity.DailyCompany;
import com.bc.finance.modular.daily.service.IDailyCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 公司表 前端控制器
 * </p>
 *
 * @author pcc
 * @since 2022-09-22
 */
@Controller
@RequestMapping("/daily/company")
public class DailyCompanyController {

    @Autowired
    private IDailyCompanyService companyService;

    @GetMapping("/list")
    @ResponseBody
    public ObjectResponse list() {
        List<DailyCompany> list = companyService.list();
        return new ObjectResponse(list);
    }
}
