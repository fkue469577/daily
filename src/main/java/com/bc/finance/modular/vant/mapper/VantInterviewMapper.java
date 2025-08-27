package com.bc.finance.modular.vant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.modular.vant.entity.VantInterview;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface VantInterviewMapper extends BaseMapper<VantInterview> {

    @Select("select * from vant_interview where type='TITLE' and `show`=1")
    List<VantInterview> defaultChannels();

    @Select("select * from vant_interview where type='TITLE'")
    List<VantInterview> allChannels();

    @Select("select * from vant_interview where (type='TITLE' and `show`=1 ) " +
            "or id in (select interview_id from vant_interview_collect where user_id=#{userId}) order by `show` desc")
    List<VantInterview> myChannels(String userId);

    List<VantInterview> articles(@Param("channelId") String articlesId, Page page);

}