package com.bc.finance.modular.daily.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bc.finance.modular.daily.entity.DailyBookNotes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 章节笔记表 Mapper 接口
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
public interface DailyBookNotesMapper extends BaseMapper<DailyBookNotes> {

    List<Map> page(IPage page, Map param);
}
