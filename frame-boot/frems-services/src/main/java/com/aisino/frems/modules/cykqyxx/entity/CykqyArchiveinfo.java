package com.aisino.frems.modules.cykqyxx.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * cyk企业信息 Entity实体类
 * 
 * @author create by cyk
 * @date 2021-09-24
 */
@Data
@TableName(value = "cykqy_archiveinfo")
public class CykqyArchiveinfo extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 主键id */
	@TableId(type = IdType.AUTO)
	@TableField(value = "ID")
	private Long id;
	/** 城市 */
	@TableField(value = "SJ")
	private String sj;
	/** 区县 */
	@TableField(value = "QX")
	private String qx;
	/** 企业类别 */
	@TableField(value = "QYLB")
	private String qylb;
	/** 企业名称 */
	@TableField(value = "QYMC")
	private String qymc;
	/** 企业编码 */
	@TableField(value = "QYBM")
	private String qybm;
	/** 注册资金 */
	@TableField(value = "ZCZJ")
	private String zczj;
	/** 注册地址 */
	@TableField(value = "ZCDZ")
	private String zcdz;
	/** 住宿登记地址 */
	@TableField(value = "ZCDJDZ")
	private String zcdjdz;
	/** 法人 */
	@TableField(value = "FR")
	private String fr;
	/** 联系人 */
	@TableField(value = "LXR")
	private String lxr;
	/** 联系电话 */
	@TableField(value = "LXDH")
	private String lxdh;
	/** 现有人数 */
	@TableField(value = "XYRS")
	private String xyrs;
	/** 目前境内人数 */
	@TableField(value = "MQJNRS")
	private String mqjnrs;
	/** 目前境外人数 */
	@TableField(value = "MQJWRS")
	private String mqjwrs;
	/** 属地派出所 */
	@TableField(value = "DDPCS")
	private String ddpcs;
	/** 出入境责任民警 */
	@TableField(value = "CRJZRMJ")
	private String crjzrmj;
	/** 走访时间 */
	@TableField(value = "ZFSJ")
	private String zfsj;
	/** 企业地址 */
	@TableField(value = "QYDZ")
	private String qydz;
	/** 签约方 */
	@TableField(value = "QYF")
	private String qyf;
	/** 合同有效期 */
	@TableField(value = "HTYXQ")
	private String htyxq;
	/** 企业总人数 */
	@TableField(value = "QYZRS")
	private String qyzrs;
	/** 录入人 */
	@TableField(value = "LRR")
	private String lrr;
	/** 录入时间 yyyy-MM-dd hh:mm:ss  */
	@TableField(value = "LRSJ")
	private String lrsj;
	/** 最后修改人 */
	@TableField(value = "ZHXGR")
	private String zhxgr;
	/** 最后修改时间 yyyy-MM-dd hh:mm:ss  */
	@TableField(value = "ZHXGSJ")
	private String zhxgsj;

}
