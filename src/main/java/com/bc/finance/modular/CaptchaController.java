package com.bc.finance.modular;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bc.finance.common.msg.ObjectResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/captcha")
@RestController
public class CaptchaController {

    @GetMapping("")
    public String index() {

        return "str";
//        return new ObjectResponse<>("aaa");
    }
}
