package com.bc.finance.modular.daily.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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


}
