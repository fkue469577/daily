package com.bc.finance.modular.flow.enums;

import lombok.Getter;

@Getter
public enum ProcessStateEnum {
    DRAFT(0, "草稿"),
    START(1, "已发起"),
    BACK(2, "退回"),
    REJECT(3, "拒绝"),
    FINISH(4, "办结"),
    COOPERATE(5, "协作中"),
    CHECKING(6, "校对中"),
    ALARM_112(7, "112接警处理"),
    BRANCH_DEAL(8, "分局处理");

    private final Integer code;
    private final String desc;

    ProcessStateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ProcessStateEnum getByCode(Integer code) {
        for (ProcessStateEnum e : values()) {
            if (e.getCode().equals(code)) return e;
        }
        return null;
    }
}