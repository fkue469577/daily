package com.bc.finance.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KVModel {

    public KVModel(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;

    private String name;

    private String word;
}
