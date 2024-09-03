package com.bc.finance.modular.daily.vo;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static cn.hutool.poi.excel.sax.AttributeName.t;

@Data
public class BookPageVO {

    private String id;
    private String name;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    public LocalDate publishedDate;
    private boolean end;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    public static List<BookPageVO> from(List list) {
        List<BookPageVO> vos = BeanUtil.copyToList(list, BookPageVO.class);
        return vos;
    }
}
