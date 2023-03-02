package com.bc.finance.modular.daily.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.modular.daily.entity.DailyInterview;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pcc
 * @since 2023-03-02
 */
public interface DailyInterviewMapper extends BaseMapper<DailyInterview> {

    List paging(Page page, @Param("param") Map param);
}
