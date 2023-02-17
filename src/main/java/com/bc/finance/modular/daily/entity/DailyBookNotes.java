package com.bc.finance.modular.daily.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 章节笔记表
 * </p>
 *
 * @author pcc
 * @since 2022-07-08
 */
@Getter
@Setter
@TableName("daily_book_notes")
@ApiModel(value = "DailyBookNotes对象", description = "章节笔记表")
public class DailyBookNotes implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private String bookId;

    private String chapterId;

    private String title;

    private String context;

    private LocalDateTime crtTime;


    @TableField(exist = false)
    private String chapterName;
}
