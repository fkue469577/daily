package com.bc.finance.modular.daily.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.common.helper.PageHelper;
import com.bc.finance.common.msg.ObjectResponse;
import com.bc.finance.common.msg.TableResponse;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.daily.entity.DailyBookChapter;
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
 * 章节表 前端控制器
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
@Controller
@RequestMapping("/daily/book/chapter")
public class BookChapterController {

    @Autowired
    IDailyBookChapterService chapterService;

    @Autowired
    IDailyBookService bookService;

    @GetMapping("")
    public String index(Model model) {

        model.addAttribute("bookList", bookService.list());

        return "daily/book/chapter/index";
    }

    @GetMapping("/page")
    @ResponseBody
    public TableResponse page(@RequestParam Map param) {

        Page page = PageHelper.defaultPage();
        List list = chapterService.page(page, param);

        return new TableResponse(page.getTotal(), list);
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ObjectResponse get(@PathVariable String id) {
        DailyBookChapter chapter = chapterService.getById(id);
        DailyBookChapter parent = Optional.ofNullable(chapterService.getById(chapter.getParentId())).orElse(new DailyBookChapter());
        chapter.setParentName(parent.getName());

        return new ObjectResponse(chapter);
    }

    @GetMapping("/listByBookId/{bookId}")
    @ResponseBody
    public ObjectResponse listByBookId(@PathVariable("bookId") String bookId) {

        List<DailyBookChapter> chapter = chapterService.listByBookIdDepend(bookId);

        return new ObjectResponse(chapter);
    }

    @PostMapping("/save")
    @ResponseBody
    public ObjectResponse save(@RequestBody DailyBookChapter chapter) {

        if(StringUtils.isBlank(chapter.getId())) {
            chapterService.insert(chapter);
        } else {
            chapterService.update(chapter);
        }

        return new ObjectResponse(chapter.getId());
    }


    @GetMapping("/tree")
    @ResponseBody
    public ObjectResponse tree(@RequestParam("bookId") String bookId ) {

        List<DailyBookChapter> chapterList = chapterService.listByBookIdDepend(bookId);

        return new ObjectResponse(chapterList);
    }
}
