package com.aisino.frems.modules.hmd.vo;

import lombok.Data;
import org.asframework.data.mybatis.model.BasePageQuery;
import com.aisino.frems.modules.hmd.entity.Hmdxx;

import java.util.Date;

/**
 * 企业黑名单管理分页查询对象类
 *
 * @author create by yangshunfei
 * @date 2021-06-22
 */
@Data
public class HmdxxPageQuery extends BasePageQuery<Hmdxx> {

    /** 中文姓名 */
    private String zwxm;
    /** 证件号码 */
    private String zjhm;
    /** 单位名称 */
    private String dwmc;

}
