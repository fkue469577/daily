package com.bc.finance.modular.daily.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bc.finance.common.utils.ObjectId;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.daily.entity.DailyBookNotes;
import com.bc.finance.modular.daily.mapper.DailyBookNotesMapper;
import com.bc.finance.modular.daily.service.IDailyBookChapterService;
import com.bc.finance.modular.daily.service.IDailyBookNotesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public List page(IPage page, Map param) {
        if(StringUtils.isNotBlank(param.get("chapterId"))) {
            List<String> chapterIds = chapterService.listBelongId(String.valueOf(param.get("chapterId")));
            param.put("chapterIds", chapterIds);
            param.remove("chapterId");
        }


        List<Map> list = mapper.page(page, param);

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
