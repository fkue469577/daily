package com.bc.finance.modular.daily.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bc.finance.modular.daily.entity.DailyNotes;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 笔记表 服务类
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
public interface IDailyNotesService extends IService<DailyNotes> {

    List page(IPage page, Map param);

    void insert(DailyNotes notes);

    void update(DailyNotes notes);
}
