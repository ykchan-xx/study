package com.aisino.frems.common.query;

import org.asframework.core.util.LangUtil;

/**
 * 查询链接规则
 *
 * @Author Sunjianlei
 */
public enum MatchTypeEnum {

    AND("AND"),
    OR("OR");

    private String value;

    MatchTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MatchTypeEnum getByValue(Object value) {
        if (LangUtil.isEmpty(value)) {
            return null;
        }
        return getByValue(value.toString());
    }

    public static MatchTypeEnum getByValue(String value) {
        if (LangUtil.isEmpty(value)) {
            return null;
        }
        for (MatchTypeEnum val : values()) {
            if (val.getValue().toLowerCase().equals(value.toLowerCase())) {
                return val;
            }
        }
        return null;
    }
}
