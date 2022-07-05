package com.bc.finance.common.utils;

public class LongUtils {
    public static Long parseLong(Object obj) {
        return StringUtils.isNotBlank(obj)? Long.parseLong(String.valueOf(obj)): null;
    }
}
