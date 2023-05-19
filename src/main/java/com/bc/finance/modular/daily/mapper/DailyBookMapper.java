package com.bc.finance.modular.daily.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.modular.daily.entity.DailyBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 书本名称 Mapper 接口
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
public interface DailyBookMapper extends BaseMapper<DailyBook> {

    List<Map> paging(Page page, Map param);

    @Select("select * from daily_book where name=#{name}")
    DailyBook getByName(String name);
}
