package com.bc.finance.common.exception.business;

import com.bc.finance.common.constant.CommonConstants;
import com.bc.finance.common.exception.BaseException;

public class DuplicateDataException extends BaseException {
    public DuplicateDataException(String message) {
        super(message, CommonConstants.EX_DUPLICATE_DATA_CODE);
    }
}
