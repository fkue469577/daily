package com.bc.finance.modular;

import com.bc.finance.common.handler.LoginHandler;
import com.bc.finance.common.model.LoginModel;
import com.bc.finance.common.msg.BaseResponse;
import com.bc.finance.common.msg.ObjectResponse;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/login")
@Controller
public class LoginController {

    @Autowired
    private LoginHandler loginHandler;

    @GetMapping("")
    public String index() {

        return "login";
    }

    @PostMapping ("")
    @ResponseBody
    public ObjectResponse login(@RequestBody LoginModel loginModel) {

        Map result = loginHandler.login(loginModel);

        return new ObjectResponse(result);
    }

}
