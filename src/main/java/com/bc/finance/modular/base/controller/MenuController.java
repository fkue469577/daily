package com.bc.finance.modular.base.controller;

import com.bc.finance.common.msg.ObjectResponse;
import com.bc.finance.modular.base.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
* <p>
* 菜单表 前端控制器
* </p>
*
* @author pcc
* @since 2022-06-02
*/
@Controller
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @GetMapping("/getMenuListExpendRole/{roleId}")
    @ResponseBody
    public ObjectResponse getMenuListExpendRole(@PathVariable("roleId") Integer roleId) {

        List roleTreeList = menuService.listMenuListExpendRole(roleId);

        return new ObjectResponse(roleTreeList);
    }
}
