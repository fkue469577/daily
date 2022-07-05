package com.bc.finance.common.share;

import com.bc.finance.common.utils.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class StringShare {

    public static boolean isEmpty(Object obj) {
        return StringUtils.isBlank(obj);
    }

    public static boolean isNotEmpty(Object obj) {
        return !StringUtils.isBlank(obj);
    }

}
