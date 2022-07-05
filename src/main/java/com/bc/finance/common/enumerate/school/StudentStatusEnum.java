package com.bc.finance.common.enumerate.school;

public enum StudentStatusEnum {
    IN_MEMBERSHIP(0, "在籍"), LEAVE_SCHOOL(1, "休学"), DROP_OUT(2, "退学");

    StudentStatusEnum(int code, String name) {
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
