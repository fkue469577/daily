package com.bc.finance.modular.daily.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bc.finance.modular.daily.entity.DailyBook;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 书本名称 服务类
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
public interface IDailyBookService extends IService<DailyBook> {

    void insert(DailyBook book);

    void update(DailyBook book);

    List paging(Page page, Map param);
}
