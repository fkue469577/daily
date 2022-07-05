package com.bc.finance.common.exception.business;

import com.bc.finance.common.constant.CommonConstants;
import com.bc.finance.common.exception.BaseException;

//  金额转换错误
public class BDException extends BaseException {
    public BDException(String message) {
        super(message, CommonConstants.EX_BD_ERROR);
    }
}
