package com.aisino.frems.job;

/**
 * 公共方法，定时器必须实现该接口
 */
public interface SystemCommonJob {
    void quartzInit() throws Exception;
}
