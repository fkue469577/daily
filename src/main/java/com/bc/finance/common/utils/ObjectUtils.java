package com.bc.finance.common.utils;

import java.util.function.Supplier;

public class ObjectUtils {

    public static <T> T nullSupplier(T t, Supplier<T> supplier) {
        if(t==null) {
            return supplier.get();
        }
        return t;
    }

}
