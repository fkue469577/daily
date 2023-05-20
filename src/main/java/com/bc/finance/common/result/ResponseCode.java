package com.bc.finance.common.result;

public enum ResponseCode {

    SUCCESS(200, "接口调用成功")

    ;

    ResponseCode(int code, String message) {
        this.code=code;
        this.message=message;
    }

    int code;
    String message;

    public int getCode() {
        return code;
    }

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
