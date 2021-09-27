package com.aisino.frems.modules.xyxx.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 企业协议档案管理 Entity实体类
 *
 * @author create by yangshunfei
 * @date 2021-06-22
 */
@Data
@TableName(value = "qy_xyxx")
public class Xyxx extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 主键id */
	@TableId(type = IdType.AUTO)
	@TableField(value = "ID")
	private Long id;
	/** 协议编号 */
	@TableField(value = "XYBH")
	private String xybh;
	/** 企业编码 */
	@TableField(value = "QYBM")
	private String qybm;
	/** 企业协议规定人数 */
	@TableField(value = "QYGDRS")
	private String qygdrs;
	/** 签约方负责人 */
	@TableField(value = "QYFFZR")
	private String qyffzr;
	/** 协议领队人员 */
	@TableField(value = "XYLDRY")
	private String xyldry;
	/** 领队联系方式 */
	@TableField(value = "LDLXFS")
	private String ldlxfs;
	/** 协议开始时间 yyyy-MM-dd hh:mm:ss */
	@TableField(value = "XYKSSJ")
	private String xykssj;
	/** 协议结束时间 yyyy-MM-dd hh:mm:ss */
	@TableField(value = "XYJSSJ")
	private String xyjssj;
	/** 录入人 */
	@TableField(value = "LRR")
	private String lrr;
	/** 录入时间 yyyy-MM-dd hh:mm:ss */
	@TableField(value = "LRSJ")
	private String lrsj;
	/** 最后修改人 */
	@TableField(value = "ZHXGR")
	private String zhxgr;
	/** 最后修改时间 yyyy-MM-dd hh:mm:ss */
	@TableField(value = "ZHXGSJ")
	private String zhxgsj;


}
