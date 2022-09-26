package com.bc.finance.modular.daily.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bc.finance.common.helper.PageHelper;
import com.bc.finance.common.msg.BaseResponse;
import com.bc.finance.common.msg.ObjectResponse;
import com.bc.finance.common.msg.TableResponse;
import com.bc.finance.modular.daily.entity.DailyAccountPassword;
import com.bc.finance.modular.daily.service.IDailyAccountPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 账号密码表 前端控制器
 * </p>
 *
 * @author pcc
 * @since 2022-09-22
 */
@Controller
@RequestMapping("/daily/account/password")
public class DailyAccountPasswordController {

    @Autowired
    private IDailyAccountPasswordService accountPasswordService;

    @GetMapping("")
    public String index() {

        return "daily/account/password/index";
    }


    @GetMapping("/page")
    @ResponseBody
    public TableResponse page(@RequestParam Map param) {
        IPage page = PageHelper.defaultPage();
        List list = accountPasswordService.page(page, param);
        return new TableResponse(page.getTotal(), list);
    }


    @GetMapping("/get/{id}")
    @ResponseBody
    public ObjectResponse get(@PathVariable String id) {
        DailyAccountPassword ap = accountPasswordService.get(id);
        return new ObjectResponse(ap);
    }


    @PostMapping("/save")
    @ResponseBody
    public BaseResponse save(@RequestBody DailyAccountPassword ap) {
        accountPasswordService.save(ap);
        return new BaseResponse();
    }


    @GetMapping("/delete/{id}")
    @ResponseBody
    public BaseResponse delete(@PathVariable String id) {
        accountPasswordService.delete(id);
        return BaseResponse.success();
    }
}
