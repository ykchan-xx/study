package com.aisino.frems.modules.ryxx.entity;

import com.aisino.frems.common.annotation.Dict;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

/**
 * 企业人员管理 Entity实体类
 *
 * @author create by yangshunfei
 * @date 2021-06-22
 */
@Data
@TableName(value = "qy_ryxx")
public class Ryxx extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 主键id */
	@TableId(type = IdType.AUTO)
	@TableField(value = "ID")
	private Long id;
	/** 头像 */
	@TableField(value = "TX")
	private String tx;
	/** 城市 */
	@TableField(value = "SJ")
	private String sj;
	/** 区县 */
	@TableField(value = "QX")
	private String qx;
	/** 中文姓名 */
	@TableField(value = "ZWXM")
	private String zwxm;
	/** 英文姓名 */
	@TableField(value = "YWXM")
	private String ywxm;
	/** 性别 */
	@TableField(value = "SEX")
	@Dict(dicCode = "sex")
	private String sex;
	/** 国家地区 */
	@TableField(value = "GJDQ")
	@Dict(dicCode = "gjdq")
	private String gjdq;
	/** 出生日期  */
	@TableField(value = "CSRQ")
	private String csrq;
	/** 证件号码 */
	@TableField(value = "ZJHM")
	private String zjhm;
	/** 证件种类 */
	@TableField(value = "ZJZL")
	@Dict(dicCode = "zjzl")
	private String zjzl;
	/** 所属企业编码 */
	@TableField(value = "SSQYBM")
	private String ssqybm;
	/** 职位 */
	@TableField(value = "ZW")
	private String zw;
	/** 联系电话 */
	@TableField(value = "LXDH")
	private String lxdh;
	/** 实际入境时间  */
	@TableField(value = "SJRJSJ")
	private String sjrjsj;
	/** 实际入境口岸 */
	@TableField(value = "SJRJKA")
	private String sjrjka;
	/** 住宿登记时间 */
	@TableField(value = "ZSDJSJ")
	private String zsdjsj;
	/** 住宿地址 */
	@TableField(value = "ZSDZ")
	private String zsdz;
	/** 单位名称 */
	@TableField(value = "DWMC")
	private String dwmc;
	/** 起始日期 */
	@TableField(value = "QSRQ")
	private String qsrq;
	/** 结束日期 */
	@TableField(value = "JSRQ")
	private String jsrq;
	/** 计划出境时间 */
	@TableField(value = "JHCJSJ")
	private String jhcjsj;
	/** 计划出境口岸 */
	@TableField(value = "JHCJKA")
	private String jhcjka;
	/** 计划入境时间 */
	@TableField(value = "JHRJSJ")
	private String jhrjsj;
	/** 计划入境口岸 */
	@TableField(value = "JHRJKA")
	private String jhrjka;
	/** 关注情况 */
	@TableField(value = "GZQK")
	private String gzqk;
	/** 备注 */
	@TableField(value = "BZ")
	private String bz;
	/** 录入人 */
	@TableField(value = "LRR")
	private String lrr;
	/** 录入时间 */
	@TableField(value = "LRSJ")
	private String lrsj;
	/** 最后修改人 */
	@TableField(value = "ZHXGR")
	private String zhxgr;
	/** 最后修改时间 */
	@TableField(value = "ZHXGSJ")
	private String zhxgsj;

}
