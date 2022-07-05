package com.bc.finance.common.utils;

public class IntegerUtils {

    public static Integer parseInt(Object obj) {
        return StringUtils.isNotBlank(obj)? Integer.parseInt(String.valueOf(obj)): null;
    }

}
