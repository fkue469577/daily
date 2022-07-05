package com.bc.finance.common.exception.business;

import com.bc.finance.common.constant.CommonConstants;
import com.bc.finance.common.exception.BaseException;

public class UnlawfulException extends BaseException {
    public UnlawfulException(String message) {
        super(message, CommonConstants.EX_UNLAWFUL_CODE);
    }
}
