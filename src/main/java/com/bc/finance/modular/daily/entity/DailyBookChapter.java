package com.bc.finance.modular.daily.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 章节表
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
@Getter
@Setter
@TableName("daily_book_chapter")
@ApiModel(value = "DailyBookChapter对象", description = "章节表")
public class DailyBookChapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private String bookId;

    private String parentId;

    private String name;

    private LocalDateTime crtTime;

    private Integer number;

    private String seq;

    private String numbers;

    private String context;




    @TableField(exist = false)
    private String parentName;

    @TableField(exist = false)
    private String value;

    @TableField(exist = false)
    private List<DailyBookChapter> children;

    public boolean addChildren(DailyBookChapter chapter) {
        if(children == null) children = new ArrayList<>();
        children.add(chapter);

        return true;
    }
}
