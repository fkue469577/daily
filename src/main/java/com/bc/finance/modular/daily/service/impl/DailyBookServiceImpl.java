package com.bc.finance.modular.daily.service.impl;

import com.bc.finance.common.utils.ObjectId;
import com.bc.finance.modular.daily.entity.DailyBook;
import com.bc.finance.modular.daily.mapper.DailyBookMapper;
import com.bc.finance.modular.daily.service.IDailyBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 书本名称 服务实现类
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
@Service
public class DailyBookServiceImpl extends ServiceImpl<DailyBookMapper, DailyBook> implements IDailyBookService {

    @Override
    public void insert(DailyBook book) {

        book.setId(ObjectId.getString());
        super.save(book);
    }

    @Override
    public void update(DailyBook book) {

        super.updateById(book);
    }
}
