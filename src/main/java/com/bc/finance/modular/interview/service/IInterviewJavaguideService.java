package com.bc.finance.modular.interview.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bc.finance.modular.interview.entity.InterviewJavaguide;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IInterviewJavaguideService extends IService<InterviewJavaguide> {

    List<InterviewJavaguide> listRoot();

    List<InterviewJavaguide> articles(String parentId, Page page);

}