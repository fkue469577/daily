package com.bc.finance.modular.base.controller;

import com.bc.finance.common.exception.jwt.TokenUserHelper;
import com.bc.finance.common.msg.BaseResponse;
import com.bc.finance.modular.base.entity.SysRole;
import com.bc.finance.modular.base.entity.SysRoleMenu;
import com.bc.finance.modular.base.entity.SysRolePermission;
import com.bc.finance.modular.base.service.IRoleMenuService;
import com.bc.finance.modular.base.service.IRolePermissionService;
import com.bc.finance.modular.base.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/system/role")
public class RoleController {
    @Resource
    TokenUserHelper userHelper;

    @Autowired
    IRoleService roleService;

    @Autowired
    IRoleMenuService roleMenuService;

    @Autowired
    IRolePermissionService rolePermissionService;

    @GetMapping("")
    public String index(Model model) {
        List<SysRole> roleList = roleService.listSystem();

        model.addAttribute("roleList", roleList);

        return "system/role/index";
    }

    @PostMapping("/save")
    @ResponseBody
    public BaseResponse save(@RequestBody SysRole role) {

        roleService.updateById(role);

        return BaseResponse.success();
    }

    @PostMapping("/insert")
    @ResponseBody
    public BaseResponse insert(@RequestBody SysRole role) {
        roleService.save(role);

        return BaseResponse.success();
    }

    @PostMapping("/removeMenuRelation")
    @ResponseBody
    public BaseResponse removeMenuRelation(@RequestBody SysRoleMenu model){

        roleMenuService.removeByRoleIdAndMenuId(model.getRoleId(), model.getMenuId());

        return BaseResponse.success();
    }

    @PostMapping("/addMenuRelation")
    @ResponseBody
    public BaseResponse addMenuRelation(@RequestBody SysRoleMenu model){

        roleMenuService.insert(model);

        return BaseResponse.success();
    }

    @PostMapping("/removePermissionRelation")
    @ResponseBody
    public BaseResponse removePermission(@RequestBody SysRolePermission model){

        rolePermissionService.removeByRoleIdAndPermissionId(model.getRoleId(), model.getPermissionId());

        return BaseResponse.success();
    }

    @PostMapping("/addPermissionRelation")
    @ResponseBody
    public BaseResponse addPermissionRelation(@RequestBody SysRolePermission model){

        rolePermissionService.insert(model);

        return BaseResponse.success();
    }

    @GetMapping("/remove/{id}")
    @ResponseBody
    public BaseResponse remove(@PathVariable Integer id) {

        roleService.removeById(id);

        return BaseResponse.success();
    }
}
