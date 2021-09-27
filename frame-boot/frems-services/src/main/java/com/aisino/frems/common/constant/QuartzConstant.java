package com.aisino.frems.common.constant;

/**
 * 定时任务基本常量
 * @author huangdanyang 2020-04-18
 */
public enum QuartzConstant {
    /**
     * 删除标志，未删除=0, 已删除=1,
     */
    DEL_NO("0"), DEL_YES("1"),
    /**
     * 有效标志，无效=0,有效=1
     */
    YXBZ_NO("0"),YXBZ_YES("1"),
    /**
     * 核查标志，待处理 = 0,已更新数据，待更新标签 = 1, 已更新标签，待评估数据 = 2, 已更新安全系数，待更新=3,数据异常 = E
     */
    HCBZ_DCL("0"), HCBZ_YCL("1"),  HCBZ_YGX("2"),HCBZ_YPG("3"),HCBZ_ERROR("E"),
    /**
     * 预警核查标志  初始化常量 如核查类别增加，需更新此处
     */
    YJHCBZ_CSHCL("00000000000");

    private String value;
    QuartzConstant(String value) {
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
