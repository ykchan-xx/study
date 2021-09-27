package com.aisino.frems.modules.hmd.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 企业黑名单管理 Entity实体类
 *
 * @author create by yangshunfei
 * @date 2021-06-22
 */
@Data
@TableName(value = "qy_hmdxx")
public class Hmdxx extends BaseEntity {
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
	/** 中文姓名 */
	@TableField(value = "ZWXM")
	private String zwxm;
	/** 英文姓名 */
	@TableField(value = "YMXM")
	private String ymxm;
	/** 性别 */
	@TableField(value = "XB")
	private String xb;
	/** 证件号码 */
	@TableField(value = "ZJHM")
	private String zjhm;
	/** 单位名称 */
	@TableField(value = "DWMC")
	private String dwmc;
	/** 备注 */
	@TableField(value = "BZ")
	private String bz;
	/** 列控原因 */
	@TableField(value = "LKYY")
	private String lkyy;
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
