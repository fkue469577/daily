package com.bc.finance.common.result;

public class BaseResponse<T> implements IResponse {

    int code;
    String message;

    private static BaseResponse success = new BaseResponse();

    public BaseResponse(){
        this.code = ResponseCode.SUCCESS.getCode();
        this.message = ResponseCode.SUCCESS.getMessage();
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static BaseResponse success() {
        return success;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
