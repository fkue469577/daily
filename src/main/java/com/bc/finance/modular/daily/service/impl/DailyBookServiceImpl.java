package com.bc.finance.modular.daily.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.common.utils.ObjectId;
import com.bc.finance.modular.daily.entity.DailyBook;
import com.bc.finance.modular.daily.entity.DailyBookChapter;
import com.bc.finance.modular.daily.mapper.DailyBookMapper;
import com.bc.finance.modular.daily.service.IDailyBookChapterService;
import com.bc.finance.modular.daily.service.IDailyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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

    @Autowired
    IDailyBookChapterService chapterService;


    @Override
    @Transactional
    public void insert(DailyBook book) {
        book.setId(ObjectId.getString());
        super.save(book);

        DailyBookChapter chapter = new DailyBookChapter();
        chapter.setBookId(book.getId());
        chapter.setName(book.getName());
        chapter.setCrtTime(LocalDateTime.now());
        chapterService.insert(chapter);
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


    @Override
    public DailyBook getByName(String name) {
        DailyBook book = mapper.getByName(name);
        return book;
    }
}
