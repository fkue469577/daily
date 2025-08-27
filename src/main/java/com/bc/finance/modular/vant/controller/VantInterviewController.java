package com.bc.finance.modular.vant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.common.helper.PageHelper;
import com.bc.finance.common.msg.BaseResponse;
import com.bc.finance.common.msg.ObjectResponse;
import com.bc.finance.modular.vant.entity.VantInterview;
import com.bc.finance.modular.vant.service.IVantInterviewCollectService;
import com.bc.finance.modular.vant.service.IVantInterviewService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vant/interview")
public class VantInterviewController {

    @Autowired
    private IVantInterviewService vantInterviewService;

    @Autowired
    private IVantInterviewCollectService vantInterviewCollectService;

    @ApiModelProperty("获取默认的文章类型")
    @GetMapping("/defaultChannels")
    public BaseResponse defaultChannels(){
        List<VantInterview> channels = vantInterviewService.defaultChannels();
        return ObjectResponse.data(channels);
    }

    @ApiModelProperty("获取我的文章类型")
    @GetMapping("/myChannels")
    public BaseResponse myChannels(){
        List<VantInterview> channels = vantInterviewService.myChannels();
        return ObjectResponse.data(channels);
    }

    @ApiModelProperty("获取全部文章类型channel-parentId is null")
    @GetMapping("/allChannels")
    public BaseResponse allChannels(){
        List<VantInterview> channels = vantInterviewService.allChannels();
        return ObjectResponse.data(channels);
    }

    @ApiModelProperty("添加我的文章类型")
    @GetMapping("/addChannels/{interviewId}")
    public BaseResponse addChannels(@PathVariable("interviewId") String interviewId){
        boolean result = vantInterviewCollectService.addChannels(interviewId);
        return BaseResponse.success();
    }

//    @ApiModelProperty("删除我的文章类型")
    @ApiModelProperty("获取文章列表 type=doc")
    @GetMapping("/articles")
    public ObjectResponse articles(@RequestParam("channelId") String channelId) {
        Page page = PageHelper.defaultPage();
        List<VantInterview> list = vantInterviewService.articles(channelId, page);
        return ObjectResponse.data(list);
    }

    @ApiModelProperty("获取文章详细")
    @GetMapping("/article/{articleId}")
    public ObjectResponse article(@PathVariable("articleId") String articleId) {
        VantInterview vantInterview = vantInterviewService.getById(articleId);
        return ObjectResponse.data(vantInterview);
    }
}
