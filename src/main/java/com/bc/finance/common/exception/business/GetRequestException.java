package com.bc.finance.common.exception.business;

import com.bc.finance.common.constant.CommonConstants;
import com.bc.finance.common.exception.BaseException;

public class GetRequestException extends BaseException {
    public GetRequestException(String message) {
        super(message, CommonConstants.EX_NOT_FIND_WEB_CODE);
    }
}
