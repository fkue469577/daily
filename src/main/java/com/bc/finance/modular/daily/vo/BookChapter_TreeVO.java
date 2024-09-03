package com.bc.finance.modular.daily.vo;

import cn.hutool.core.bean.BeanUtil;
import com.bc.finance.modular.daily.entity.DailyBookChapter;
import lombok.Data;

import java.util.List;

@Data
public class BookChapter_TreeVO {

    private String id;

    private String name;

    private String parentId;

    private String parentName;

    private String seq;

    private List<BookChapter_TreeVO> children;


    public static List<BookChapter_TreeVO> from(List<DailyBookChapter> list) {
        List<BookChapter_TreeVO> vos = BeanUtil.copyToList(list, BookChapter_TreeVO.class);
        return vos;
    }
}