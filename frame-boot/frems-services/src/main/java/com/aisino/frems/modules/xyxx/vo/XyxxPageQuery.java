package com.aisino.frems.modules.xyxx.vo;

import lombok.Data;
import org.asframework.data.mybatis.model.BasePageQuery;
import com.aisino.frems.modules.xyxx.entity.Xyxx;

import java.util.Date;

/**
 * 企业人员管理分页查询对象类
 *
 * @author create by yangshunfei
 * @date 2021-06-22
 */
@Data
public class XyxxPageQuery extends BasePageQuery<Xyxx> {

    /** 签约方负责人 */
    private String qyffzr;
    /** 协议领队人员 */
    private String xyldry;
    /** 协议开始时间 yyyy-MM-dd hh:mm:ss */
    private String xykssj;
    /** 协议结束时间 yyyy-MM-dd hh:mm:ss */
    private String xyjssj;

}
