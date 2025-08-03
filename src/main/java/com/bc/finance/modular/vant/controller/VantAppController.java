package com.bc.finance.modular.vant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.common.helper.PageHelper;
import com.bc.finance.common.msg.BaseResponse;
import com.bc.finance.common.msg.ObjectResponse;
import com.bc.finance.modular.interview.entity.InterviewJavaguide;
import com.bc.finance.modular.interview.service.IInterviewJavaguideService;
import com.bc.finance.modular.vant.dto.VanAppAddChannelsDto;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/van/app")
public class VantAppController {

    @Autowired
    private IInterviewJavaguideService interviewJavaguideService;

    @ApiModelProperty("获取文章类型")
    @GetMapping("/channels")
    public BaseResponse channels() {
        List<InterviewJavaguide> list = interviewJavaguideService.listRoot();
        return ObjectResponse.data(list);
    }

    @ApiModelProperty("获取文章列表")
    @GetMapping("/articles")
    public BaseResponse articles(@RequestParam("channelId") String id) {
        Page page = PageHelper.defaultPage();
        List<InterviewJavaguide> list = interviewJavaguideService.articles(id, page);
        return ObjectResponse.data(list);
    }


    @GetMapping("/article/{articleId}")
    public BaseResponse article(@PathVariable("articleId") String articleId) {
        InterviewJavaguide article = interviewJavaguideService.getById(articleId);
        return ObjectResponse.data(article);
    }


    @ApiModelProperty("添加用户频道")
    @PostMapping("/addChannels")
    public BaseResponse addChannels(@RequestBody VanAppAddChannelsDto dto) {
        return BaseResponse.success();
    }


    @ApiModelProperty("删除用户频道")
    @DeleteMapping("/channels/{channelId}")
    public BaseResponse deleteChannels(@PathVariable("channelId") String channelId) {
        return BaseResponse.success();
    }


    @ApiModelProperty("获取当前用户的频道列表")
    @GetMapping("/currentChannels")
    public BaseResponse currentChannels() {
        return BaseResponse.success();
    }


    @ApiModelProperty("获取频道")
    @GetMapping(value = {"/suggestion",})
    public BaseResponse suggestion(@RequestParam("question") String question) {
        List<String> collect = IntStream.range(0, 100).mapToObj(e -> String.format("推荐%d", e)).filter(e -> e.indexOf(question) > -1).collect(Collectors.toList());
        return ObjectResponse.data(collect);
    }


    @ApiModelProperty("获取频道")
    @GetMapping(value = { "/getSearchResult"})
    public BaseResponse getSearchResult(@RequestParam("question") String question) {
        Page page = PageHelper.defaultPage();
        List<String> collect = IntStream.range(0, 100)
                .mapToObj(e -> String.format("推荐%d", e))
                .filter(e -> e.indexOf(question) > -1)
                .skip(page.getSize()*(page.getCurrent()-1))
                .limit(page.getSize())
                .collect(Collectors.toList());
        return ObjectResponse.data(collect);
    }



}