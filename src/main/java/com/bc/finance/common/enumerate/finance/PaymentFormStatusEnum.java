package com.bc.finance.common.enumerate.finance;

public enum PaymentFormStatusEnum {
    UN_REVIEW(10, "审核中"), PASSED(20, "通过"), UN_PASSED(30, "回退");

    PaymentFormStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
