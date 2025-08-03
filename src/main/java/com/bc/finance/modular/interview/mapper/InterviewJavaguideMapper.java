package com.bc.finance.modular.interview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.modular.interview.entity.InterviewJavaguide;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InterviewJavaguideMapper extends BaseMapper<InterviewJavaguide> {

    List<InterviewJavaguide> articles(Page page, @Param("map") Map map);

}
