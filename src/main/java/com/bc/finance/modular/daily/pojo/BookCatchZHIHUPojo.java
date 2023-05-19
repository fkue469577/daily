package com.bc.finance.modular.daily.pojo;

import lombok.Data;

@Data
public class BookCatchZHIHUPojo {

    String book;

    Chapter chapter;

    @Data
    public class Chapter {
        String parentName;

        String name;

        String context;
    }
}
