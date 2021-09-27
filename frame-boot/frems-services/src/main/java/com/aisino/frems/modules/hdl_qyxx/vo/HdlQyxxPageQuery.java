package com.aisino.frems.modules.hdl_qyxx.vo;

import lombok.Data;
import org.asframework.data.mybatis.model.BasePageQuery;
import com.aisino.frems.modules.hdl_qyxx.entity.HdlQyxx;

import java.util.Date;

/**
 * 企业信息分页查询对象类
 *
 * @author create by huangdanlei
 * @date 2021-07-23
 */
@Data
public class HdlQyxxPageQuery extends BasePageQuery<HdlQyxx> {

    /** 企业地址 */
    private String qydz;
    /** 企业类别 */
    private String qylb;
    /** 企业名称 */
    private String qymc;
    /** 企业编码 */
    private String qybm;
    /** 法人 */
    private String fr;
    /** 签约方 */
    private String qyf;

}
