package com.bc.finance.common.exception.auth;

import com.bc.finance.common.constant.CommonConstants;
import com.bc.finance.common.exception.BaseException;

public class ClientLoginInvalidException  extends BaseException {
    public ClientLoginInvalidException(String message) {
        super(message, CommonConstants.EX_CLIENT_LOGIN_INVALID_CODE);
    }
}
