package com.bc.finance.modular.daily.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bc.finance.common.utils.ObjectId;
import com.bc.finance.modular.daily.entity.DailyBook;
import com.bc.finance.modular.daily.mapper.DailyBookMapper;
import com.bc.finance.modular.daily.service.IDailyBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @Resource
    DailyBookMapper mapper;


    @Override
    public void insert(DailyBook book) {

        book.setId(ObjectId.getString());
        super.save(book);
    }


    @Override
    public void update(DailyBook book) {

        super.updateById(book);
    }


    @Override
    public List paging(Page page, Map param) {
        List list = mapper.paging(page, param);
        return list;
    }
}
