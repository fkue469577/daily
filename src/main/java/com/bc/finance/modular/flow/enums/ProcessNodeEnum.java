package com.bc.finance.modular.flow.enums;

import lombok.Getter;

@Getter
public enum ProcessNodeEnum {
    DRAFT_NODE("draft","草稿节点"),
    START_NODE("start","发起节点"),
    CHECK_CLASS("checkClass","校对班"),
    ALARM_112_NODE("alarm112","112接警"),
    SIMING_NODE("siming","思明分局"),
    HULI_NODE("huli","湖里分局"),
    COOPERATE_NODE("cooperate","协作处理"),
    FINISH_NODE("finish","办结");

    private final String code;
    private final String name;

    ProcessNodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}