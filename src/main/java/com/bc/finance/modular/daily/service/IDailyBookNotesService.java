package com.bc.finance.modular.daily.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bc.finance.modular.daily.entity.DailyBookNotes;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 章节笔记表 服务类
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
public interface IDailyBookNotesService extends IService<DailyBookNotes> {

    List listing(Map param);

    void insert(DailyBookNotes notes);

    void update(DailyBookNotes notes);
}
