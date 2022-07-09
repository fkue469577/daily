package com.bc.finance.modular.daily.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.common.helper.PageHelper;
import com.bc.finance.common.msg.BaseResponse;
import com.bc.finance.common.msg.ObjectResponse;
import com.bc.finance.common.msg.TableResponse;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.daily.entity.DailyBook;
import com.bc.finance.modular.daily.service.IDailyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 书本名称 前端控制器
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
@Controller
@RequestMapping("/daily/book")
public class BookController {

    @Autowired
    IDailyBookService bookService;

    @GetMapping("")
    public String index() {

        return "/daily/book/index";
    }

    @GetMapping("/page")
    @ResponseBody
    public TableResponse page(@RequestParam Map param) {
        Page page = PageHelper.defaultPage();

        QueryWrapper query = new QueryWrapper();
        query.select("id", "name", "published_date");
        bookService.page(page, query);

        return new TableResponse(page.getTotal(), page.getRecords());
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ObjectResponse get(@PathVariable String id) {

        DailyBook book = bookService.getById(id);
        return new ObjectResponse<>(book);
    }

    @PostMapping("/save")
    @ResponseBody
    public BaseResponse save(@RequestBody DailyBook book) {

        if(StringUtils.isBlank(book.getId())) {
            bookService.insert(book);
        } else {
            bookService.update(book);
        }

        return BaseResponse.success();
    }
}
