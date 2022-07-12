package com.bc.finance.modular.daily.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.common.helper.PageHelper;
import com.bc.finance.common.msg.BaseResponse;
import com.bc.finance.common.msg.ObjectResponse;
import com.bc.finance.common.msg.TableResponse;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.daily.entity.DailyNotes;
import com.bc.finance.modular.daily.service.IDailyNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 笔记表 前端控制器
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
@Controller
@RequestMapping("/daily/notes")
public class NotesController {

    @Autowired
    IDailyNotesService notesService;


    @GetMapping("")
    public String index() {

        return "/daily/notes/index";
    }

    @GetMapping("/page")
    @ResponseBody
    public TableResponse page(@RequestParam Map param) {

        Page page = PageHelper.defaultPage();
        List list = notesService.page(page, param);

        return new TableResponse(page.getTotal(), list);
    }

    @PostMapping("/save")
    @ResponseBody
    public BaseResponse save(@RequestBody DailyNotes notes) {

        if(StringUtils.isBlank(notes.getId())) {
            notesService.insert(notes);
        } else {
            notesService.update(notes);
        }

        return BaseResponse.success();
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ObjectResponse get(@PathVariable String id) {

        DailyNotes notes = notesService.getById(id);

        return new ObjectResponse(notes);
    }
}
