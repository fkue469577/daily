package com.bc.finance.modular.daily.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bc.finance.modular.daily.entity.DailyBookNotes;
import com.baomidou.mybatisplus.extension.service.IService;

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

    List page(IPage page, Map param);

    void insert(DailyBookNotes notes);

    void update(DailyBookNotes notes);
}
