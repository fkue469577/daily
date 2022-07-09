package com.bc.finance.modular.daily.service;

import com.bc.finance.modular.daily.entity.DailyBook;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
