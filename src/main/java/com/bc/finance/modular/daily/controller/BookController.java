package com.bc.finance.modular.daily.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.common.helper.PageHelper;
import com.bc.finance.common.msg.BaseResponse;
import com.bc.finance.common.msg.ObjectResponse;
import com.bc.finance.common.msg.TableResponse;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.daily.entity.DailyBook;
import com.bc.finance.modular.daily.entity.DailyBookChapter;
import com.bc.finance.modular.daily.pojo.BookCatchZHIHUPojo;
import com.bc.finance.modular.daily.service.IDailyBookChapterService;
import com.bc.finance.modular.daily.service.IDailyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
    IDailyBookChapterService chapterService;

    @GetMapping("")
    public String index() {

        return "daily/book/index";
    }

    @GetMapping("/page")
    @ResponseBody
    public TableResponse page(@RequestParam Map param) {
        Page page = PageHelper.defaultPage();

        List list = bookService.paging(page, param);

        return new TableResponse(page.getTotal(), list);
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

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable String id) {

        model.addAttribute("id", id);

        return "daily/book/detail";
    }


    @PostMapping("/catchZHIHU")
    @ResponseBody
    public BaseResponse catchZHIHU(@RequestBody BookCatchZHIHUPojo pojo) {

        DailyBook book = bookService.getByName(pojo.getBook());
        if(book==null) {
            book = new DailyBook()
                    .setName(pojo.getBook());
            bookService.insert(book);
        }

        String parentName = pojo.getChapter().getParentName();
        String[] strs = parentName.split("第  ");
        DailyBookChapter parent = Optional.ofNullable(chapterService.getByBookIdAndName(book.getId(), strs[strs.length-1])).orElseGet(DailyBookChapter::new);

        DailyBookChapter chapter = new DailyBookChapter()
                .setParentId(parent.getId())
                .setBookId(book.getId())
                .

        return BaseResponse.success();
    }
}
