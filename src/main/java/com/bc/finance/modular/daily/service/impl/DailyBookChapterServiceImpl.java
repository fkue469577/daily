package com.bc.finance.modular.daily.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.common.utils.ObjectId;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.daily.entity.DailyBookChapter;
import com.bc.finance.modular.daily.mapper.DailyBookChapterMapper;
import com.bc.finance.modular.daily.service.IDailyBookChapterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 章节表 服务实现类
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
@Service
public class DailyBookChapterServiceImpl extends ServiceImpl<DailyBookChapterMapper, DailyBookChapter> implements IDailyBookChapterService {

    @Resource
    DailyBookChapterMapper mapper;

    @Override
    public List page(IPage page, Map param) {

        return mapper.page(page, param);
    }

    @Override
    public List<DailyBookChapter> listByBookIdDepend(String bookId) {
        List<DailyBookChapter> chapterList = mapper.listByBookId(bookId);
        chapterList.forEach(e->e.setValue(e.getId()));
        List<DailyBookChapter> rootList = chapterList.stream().filter(e -> StringUtils.isBlank(e.getParentId())).collect(Collectors.toList());

        Map<String, DailyBookChapter> id2Chapter = chapterList.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
        Set<String> idList = id2Chapter.keySet();
        chapterList.stream()
                .filter(e -> StringUtils.isNotBlank(e.getParentId()) && idList.contains(e.getParentId()))
                .forEach(e->{
                    id2Chapter.get(e.getParentId()).addChildren(e);
                    e.setParentName(id2Chapter.get(e.getParentId()).getName());
                });

        return rootList;
    }

    @Override
    public void insert(DailyBookChapter chapter) {
        chapter.setCrtTime(LocalDateTime.now());
        chapter.setId(ObjectId.getString());

        int number = this.maxNumberByChapter(chapter.getBookId());
        chapter.setNumber(number+1);
        if (number==-1) {
            chapter.setNumbers(String.format("[%d]", chapter.getNumber()));
        } else {
            number = this.countByBookIdAndParentId(chapter.getBookId(), chapter.getParentId())+1;
            DailyBookChapter parent = this.getById(chapter.getParentId());
            chapter.setSeq(parent==null||StringUtils.isBlank(parent.getSeq())? String.valueOf(number): parent.getSeq()+"."+number);
            chapter.setNumbers(String.format("%s[%d]", StringUtils.getObjectValue(parent.getNumbers()), chapter.getNumber()));
        }

        super.save(chapter);
    }


    @Override
    public void update(DailyBookChapter chapter) {

        super.updateById(chapter);
    }


    @Override
    public int maxNumberByChapter(String bookId) {
        int number = mapper.maxNumberByChapter(bookId);
        return number;
    }

    @Override
    public int countByBookIdAndParentId(String bookId, String parentId) {

        return mapper.countByBookIdAndParentId(bookId, parentId);
    }

}
