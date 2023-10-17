package com.bc.finance.modular.daily.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/daily/javabean/json")
public class JavaBeanJsonController {

    @GetMapping("")
    public String index() {

        return "daily/javabean/json/index";
    }
}
