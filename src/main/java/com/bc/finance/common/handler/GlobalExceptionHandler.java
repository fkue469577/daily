package com.bc.finance.common.handler;

import com.bc.finance.common.constant.CodeEnum;
import com.bc.finance.common.constant.CommonConstants;
import com.bc.finance.common.exception.BaseException;
import com.bc.finance.common.exception.auth.ClientLoginInvalidException;
import com.bc.finance.common.exception.auth.ClientTokenException;
import com.bc.finance.common.exception.auth.UserPassInvalidException;
import com.bc.finance.common.exception.auth.UserTokenException;
import com.bc.finance.common.exception.business.BusinessException;
import com.bc.finance.common.exception.business.NotEmptyException;
import com.bc.finance.common.msg.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by ace on 2017/9/8.
 */
@RestControllerAdvice()
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessException(HttpServletResponse response, BusinessException ex) {
        response.setStatus(200);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(CommonConstants.EX_CLIENT_BUSINESS_CODE, ex.getMessage());
    }

    @ExceptionHandler(NotEmptyException.class)
    public BaseResponse businessException(HttpServletResponse response, NotEmptyException ex) {
        response.setStatus(200);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(CommonConstants.EX_NOT_EMPTY_CODE, ex.getMessage());
    }

    @ExceptionHandler( MethodArgumentNotValidException.class)
    public BaseResponse methodArgumentNotValidException(HttpServletResponse response, MethodArgumentNotValidException ex) {
        response.setStatus(200);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(CommonConstants.EX_NOT_EMPTY_CODE, ex.getMessage());
    }

    @ExceptionHandler(ClientTokenException.class)
    public BaseResponse clientTokenExceptionHandler(HttpServletResponse response, ClientTokenException ex) {
        response.setStatus(200);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(ClientLoginInvalidException.class)
    public BaseResponse clientLoginInvalidHandler(HttpServletResponse response, ClientLoginInvalidException ex) {
        response.setStatus(200);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(UserTokenException.class)
    public BaseResponse userTokenExceptionHandler(HttpServletResponse response, UserTokenException ex) {
        response.setStatus(401);
        logger.error(ex.getMessage(), ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(UserPassInvalidException.class)
    public BaseResponse userInvalidExceptionHandler(HttpServletResponse response, UserPassInvalidException ex) {
        response.setStatus(200);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    public BaseResponse baseExceptionHandler(HttpServletResponse response, BaseException ex) {
        response.setStatus(200);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse otherExceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(200);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(CommonConstants.EX_OTHER_CODE, ex.getMessage());
    }

}
