package com.bc.finance.modular.daily.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.common.helper.PageHelper;
import com.bc.finance.common.msg.BaseResponse;
import com.bc.finance.common.msg.ObjectResponse;
import com.bc.finance.common.msg.TableResponse;
import com.bc.finance.modular.daily.entity.DailyWords;
import com.bc.finance.modular.daily.service.IDailyWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 单词表 前端控制器
 * </p>
 *
 * @author pcc
 * @since 2022-08-23
 */
@Controller
@RequestMapping("/daily/words")
public class DailyWordsController {

    @Autowired
    IDailyWordsService wordsService;


    @GetMapping("")
    public String index() {

        return "daily/words/index";
    }


    @GetMapping("/page")
    @ResponseBody
    public TableResponse page(@RequestParam Map param) {

        Page page = PageHelper.defaultPage();
        List list = wordsService.page(page, param);

        return new TableResponse(page.getTotal(), list);
    }


    @GetMapping("/get/{id}")
    @ResponseBody
    public ObjectResponse get(@PathVariable String id) {
        DailyWords word = wordsService.getById(id);

        return new ObjectResponse(word);
    }


    @PostMapping("/save")
    @ResponseBody
    public BaseResponse save(@RequestBody DailyWords words) {
        wordsService.save(words);
        return BaseResponse.success();
    }


    @GetMapping("/complete/{id}")
    @ResponseBody
    public BaseResponse complete(@PathVariable String id) {
        DailyWords word = new DailyWords();
        word.setId(id);
        word.setCompleted(true);
        wordsService.update(word);

        return BaseResponse.success();
    }


    @GetMapping("/getWordDetail")
    @ResponseBody
    public ObjectResponse getWordDetail(@RequestParam("word") String word) throws IOException {
        URL url = new URL("http://dict.youdao.com/fsearch?client=deskdict&keyfrom=chrome.extension&q=" + word + "&pos=-1&doctype=xml&xmlVersion=3.2&dogVersion=1.0&vendor=unknown&appVer=3.1.17.4208&le=eng");
        InputStreamReader reader = new InputStreamReader(url.openStream(),"utf-8");
        StringBuilder builder = new StringBuilder();
        char[] chars = new char[1024];
        int read;
        if((read = reader.read(chars))>-1) {
            builder.append(chars);
        }
        reader.close();
        return new ObjectResponse(builder.toString());
    }

}
