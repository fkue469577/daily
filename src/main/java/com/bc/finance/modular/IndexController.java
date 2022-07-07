package com.bc.finance.modular;

import com.bc.finance.common.exception.jwt.IJWTInfo;
import com.bc.finance.common.exception.jwt.TokenUserHelper;
import com.bc.finance.common.msg.ObjectResponse;
import com.bc.finance.modular.base.entity.SysMenu;
import com.bc.finance.modular.base.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private TokenUserHelper userHelper;

    @Autowired
    private IMenuService menuService;

    @GetMapping("")
    public String index(Model model) {

        return "index";
    }

    @GetMapping("/index/menu")
    @ResponseBody
    public ObjectResponse menu() {
        IJWTInfo user = userHelper.getTokenUser();

        List<SysMenu> menuList = menuService.listByUserId(user.getUserId());

        Map map = new HashMap();
        map.put("menuList", menuList);
        map.put("name", user.getName());

        return new ObjectResponse(map);
    }
}
