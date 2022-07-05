package com.bc.finance.common.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

    public static BigDecimal parseBigDecimal(Object obj) {
        return new BigDecimal(String.valueOf(obj));
    }

}
