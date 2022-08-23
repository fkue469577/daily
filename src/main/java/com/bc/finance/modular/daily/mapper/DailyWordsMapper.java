package com.bc.finance.modular.daily.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bc.finance.modular.daily.entity.DailyWords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 单词表 Mapper 接口
 * </p>
 *
 * @author pcc
 * @since 2022-08-23
 */
public interface DailyWordsMapper extends BaseMapper<DailyWords> {

    List<Map> page(IPage page, @Param("param") Map param);
}
