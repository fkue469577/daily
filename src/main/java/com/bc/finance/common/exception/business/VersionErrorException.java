package com.bc.finance.common.exception.business;

import com.bc.finance.common.constant.CommonConstants;
import com.bc.finance.common.exception.BaseException;

public class VersionErrorException extends BaseException {
    public VersionErrorException(String message) {
        super(message, CommonConstants.EX_VERSION_ERROR);
    }
}
