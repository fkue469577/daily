package com.bc.finance.modular.daily.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bc.finance.modular.daily.entity.DailyBookChapter;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 章节表 服务类
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
public interface IDailyBookChapterService extends IService<DailyBookChapter> {

    List page(IPage page, Map param);

    List<DailyBookChapter> listByBookIdDepend(String bookId);

    void insert(DailyBookChapter chapter);

    void update(DailyBookChapter chapter);

    int maxNumberByChapter(String bookId);

    int countByBookIdAndParentId(String bookId, String parentId);

    DailyBookChapter getByBookIdAndName(String bookId, String name);
}
