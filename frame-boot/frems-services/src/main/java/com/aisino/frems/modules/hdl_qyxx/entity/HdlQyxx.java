package com.aisino.frems.modules.hdl_qyxx.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 企业信息 Entity实体类
 * 
 * @author create by huangdanlei
 * @date 2021-07-23
 */
@Data
@TableName(value = "hdl_qyxx")
public class HdlQyxx extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId(type = IdType.AUTO)
	@TableField(value = "id")
	private Long id;
	/** 市级 */
	@TableField(value = "qysj")
	private String qysj;
	/** 县区 */
	@TableField(value = "qyxq")
	private String qyxq;
	/** 企业地址 */
	@TableField(value = "qydz")
	private String qydz;
	/** 企业类别 */
	@TableField(value = "qylb")
	private String qylb;
	/** 企业名称 */
	@TableField(value = "qymc")
	private String qymc;
	/** 企业编码 */
	@TableField(value = "qybm")
	private String qybm;
	/** 注册资金 */
	@TableField(value = "zczj")
	private String zczj;
	/** 注册地址 */
	@TableField(value = "zcdz")
	private String zcdz;
	/** 住宿登记地址 */
	@TableField(value = "zsdz")
	private String zsdz;
	/** 法人 */
	@TableField(value = "fr")
	private String fr;
	/** 联系人 */
	@TableField(value = "lxr")
	private String lxr;
	/** 联系电话 */
	@TableField(value = "lxdh")
	private String lxdh;
	/** 签约方 */
	@TableField(value = "qyf")
	private String qyf;
	/** 签约开始时间 */
	@TableField(value = "qy_starttime")
	private String qyStarttime;
	/** 签约结束时间 */
	@TableField(value = "qy_endtime")
	private String qyEndtime;
	/** 企业总人数 */
	@TableField(value = "qyrs")
	private String qyrs;
	/** 录入人 */
	@TableField(value = "lrr")
	private String lrr;
	/** 录入时间 */
	@TableField(value = "lrsj")
	private String lrsj;
	/** 最后修改人 */
	@TableField(value = "zhxgr")
	private String zhxgr;
	/** 最后修改时间 */
	@TableField(value = "zhxgsj")
	private String zhxgsj;

}
