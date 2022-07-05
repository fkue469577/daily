package com.bc.finance.modular;

import com.bc.finance.common.msg.ObjectResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/captcha")
@RestController
public class CaptchaController {

    @GetMapping("")
    public Object index() {

        return new ObjectResponse<>("aaa");
    }
}
