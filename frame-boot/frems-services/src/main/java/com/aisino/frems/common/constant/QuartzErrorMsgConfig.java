package com.aisino.frems.common.constant;

public enum QuartzErrorMsgConfig {
    configError("Please configure parameters first: %s. The format is: %s");
    private String value;
    QuartzErrorMsgConfig(String value) {
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
