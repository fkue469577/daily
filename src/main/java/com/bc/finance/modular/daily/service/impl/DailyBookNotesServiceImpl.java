package com.bc.finance.modular.daily.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.common.utils.ObjectId;
import com.bc.finance.modular.daily.entity.DailyBookNotes;
import com.bc.finance.modular.daily.mapper.DailyBookNotesMapper;
import com.bc.finance.modular.daily.service.IDailyBookChapterService;
import com.bc.finance.modular.daily.service.IDailyBookNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 章节笔记表 服务实现类
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
@Service
public class DailyBookNotesServiceImpl extends ServiceImpl<DailyBookNotesMapper, DailyBookNotes> implements IDailyBookNotesService {

    @Resource
    DailyBookNotesMapper mapper;

    @Autowired
    IDailyBookChapterService chapterService;

    @Override
    public List listing(Map param) {
        List<Map> list = mapper.listing(param);

        return list;
    }

    @Override
    public void insert(DailyBookNotes notes) {
        notes.setId(ObjectId.getString());
        notes.setCrtTime(LocalDateTime.now());

        super.save(notes);
    }

    @Override
    public void update(DailyBookNotes notes) {

        super.updateById(notes);
    }
}
