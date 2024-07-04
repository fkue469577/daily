package com.bc.finance.modular.daily.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 个税
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
@Controller
@RequestMapping("/daily/tax")
public class TaxController {
    @GetMapping("")
    public String index() {

        return "daily/tax/index";
    }
}
