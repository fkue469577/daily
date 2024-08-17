package com.bc.finance.modular.daily.mapper;

import com.bc.finance.modular.daily.entity.DailyInterviewTitle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pcc
 * @since 2023-03-02
 */
public interface DailyInterviewTitleMapper extends BaseMapper<DailyInterviewTitle> {

    @Select("select id, name from daily_interview_title where parent_id=#{parentId}")
    List<DailyInterviewTitle> listByParentId(String parentId);

    @Select("select * from daily_interview_title where parent_id is null")
    List<DailyInterviewTitle> listRoot();
}
