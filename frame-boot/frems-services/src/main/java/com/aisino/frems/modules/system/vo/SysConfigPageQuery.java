package com.aisino.frems.modules.system.vo;

import lombok.Data;
import org.asframework.data.mybatis.model.BasePageQuery;
import com.aisino.frems.modules.system.entity.SysConfig;

/**
 * 系统参数分页查询对象类
 *
 * @author zhuxiaoyan
 * @date 2020-03-31
 */
@Data
public class SysConfigPageQuery extends BasePageQuery<SysConfig> {

    /** 参数名称 */
    private String paraName;
    /** 参数代码 */
    private String paraCode;
    /** 参数值 */
    private String paraValue;
    /** 参数描述 */
    private String paraDesc;
    /** 租户编号 */
    private String zhbh;
    /** 企业编号 */
    private String qybh;
    /** 部门编号 */
    private String bmbh;

}
