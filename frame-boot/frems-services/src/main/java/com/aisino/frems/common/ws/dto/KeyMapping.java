package com.aisino.frems.common.ws.dto;

public class KeyMapping {
    private String code;// 编码.
    private String value;// 编码对照表现值.

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return code + ":" + value;
    }
}
