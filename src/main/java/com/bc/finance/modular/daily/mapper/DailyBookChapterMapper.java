package com.bc.finance.modular.daily.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bc.finance.modular.daily.entity.DailyBookChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 章节表 Mapper 接口
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
public interface DailyBookChapterMapper extends BaseMapper<DailyBookChapter> {

    List<Map> page(IPage page, Map param);

    @Select("select id,name,seq,parent_id from daily_book_chapter where book_id=#{bookId}")
    List<DailyBookChapter> listByBookId(String bookId);

    @Select("select ifnull(max(number), -1) number from daily_book_chapter where book_id=#{bookId}")
    int maxNumberByChapter(String bookId);


    @Select("select ifnull(count(1), 0) number from daily_book_chapter where book_id=#{bookId} and parent_id=#{parentId}")
    int countByBookIdAndParentId(String bookId, String parentId);
}
