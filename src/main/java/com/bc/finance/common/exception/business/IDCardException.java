package com.bc.finance.common.exception.business;

import com.bc.finance.common.constant.CommonConstants;
import com.bc.finance.common.exception.BaseException;

public class IDCardException extends BaseException {
    public IDCardException(String message) {
        super(message, CommonConstants.EX_ID_CARD_CODE);
    }
}
