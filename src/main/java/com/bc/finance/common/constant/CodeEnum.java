package com.bc.finance.common.constant;

public enum CodeEnum {
    EX_USER_INVALID_CODE(40101, "User token expired!"),
    EX_USER_PASS_INVALID_CODE(40102, "用户名或者密码错误!")
    ;





    private Integer code;
    private String msg;

    CodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
