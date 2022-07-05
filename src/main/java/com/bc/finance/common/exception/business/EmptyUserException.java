package com.bc.finance.common.exception.business;

import com.bc.finance.common.constant.CommonConstants;
import com.bc.finance.common.exception.BaseException;

public class EmptyUserException extends BaseException {
    public EmptyUserException() {
        super("用户为空", CommonConstants.EX_EMPTY_USER);
    }

    public EmptyUserException(String message) {
        super(message, CommonConstants.EX_EMPTY_USER);
    }
}
