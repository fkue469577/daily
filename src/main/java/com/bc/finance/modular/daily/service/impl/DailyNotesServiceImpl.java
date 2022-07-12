package com.bc.finance.modular.daily.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bc.finance.common.utils.ObjectId;
import com.bc.finance.modular.daily.entity.DailyNotes;
import com.bc.finance.modular.daily.mapper.DailyNotesMapper;
import com.bc.finance.modular.daily.service.IDailyNotesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 笔记表 服务实现类
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
@Service
public class DailyNotesServiceImpl extends ServiceImpl<DailyNotesMapper, DailyNotes> implements IDailyNotesService {

    @Resource
    DailyNotesMapper mapper;

    @Override
    public List page(IPage page, Map param) {

        return mapper.page(page, param);
    }

    @Override
    public void insert(DailyNotes notes) {
        notes.setId(ObjectId.getString());
        notes.setCrtTime(LocalDateTime.now());

        super.save(notes);
    }

    @Override
    public void update(DailyNotes notes) {

        super.updateById(notes);
    }
}
