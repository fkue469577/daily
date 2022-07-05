package com.bc.finance.common.exception.business;

import com.bc.finance.common.constant.CommonConstants;
import com.bc.finance.common.exception.BaseException;

public class NotEmptyException extends BaseException {
    public NotEmptyException(String message) {
        super(message, CommonConstants.EX_NOT_EMPTY_CODE);
    }
}
