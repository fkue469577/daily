package com.bc.finance.common.exception.auth;

import com.bc.finance.common.constant.CodeEnum;
import com.bc.finance.common.exception.BaseException;

/**
 * Created by ace on 2017/9/8.
 */
public class UserPassInvalidException extends BaseException {
    public UserPassInvalidException() {
        super(CodeEnum.EX_USER_PASS_INVALID_CODE.getMsg(), CodeEnum.EX_USER_PASS_INVALID_CODE.getCode());
    }
}
