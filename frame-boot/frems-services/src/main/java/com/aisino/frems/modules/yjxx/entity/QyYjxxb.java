package com.aisino.frems.modules.yjxx.entity;

import com.aisino.frems.common.annotation.Dict;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 预警信息 Entity实体类
 *
 * @author create by luozheng
 * @date 2021-07-02
 */
@Data
@TableName(value = "qy_yjxxb")
public class QyYjxxb extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 主键id */
	@TableId(type = IdType.AUTO)
	@TableField(value = "ID")
	private Long id;
	/** 企业编号 */
	@TableField(value = "qybh")
	private String qybh;
	/** 企业名称 */
	@TableField(value = "qymc")
	private String qymc;
	/** 英文姓名 */
	@TableField(value = "ywxm")
	private String ywxm;
	/** 性别 */
	@TableField(value = "sex")
	private String sex;
	/** 出生日期 */
	@TableField(value = "csrq")
	private String csrq;
	/** 国家地区 */
	@TableField(value = "gjdq")
	@Dict(dicCode = "gjdq")
	private String gjdq;
	/** 证件种类 */
	@TableField(value = "zjzl")
	@Dict(dicCode = "zjzl")
	private String zjzl;
	/** 证件号码 */
	@TableField(value = "zjhm")
	private String zjhm;
	/** 预警类型 */
	@TableField(value = "yjlx")
	@Dict(dicCode = "yjlx")
	private String yjlx;
	/** 预警原因 */
	@TableField(value = "yjyy")
	private String yjyy;
	/** 预警状态 */
	@TableField(value = "yjzt")
	@Dict(dicCode = "yjzt")
	private String yjzt;
	/** 处理意见 */
	@TableField(value = "clyj")
	private String clyj;
	/**  */
	@TableField(value = "cjsj")
	private String cjsj;
	/** 创建人 */
	@TableField(value = "cjr")
	private String cjr;
	/** 最后修改时间 */
	@TableField(value = "zhxgsj")
	private String zhxgsj;
	/** 最后修改人 */
	@TableField(value = "zhxgr")
	private String zhxgr;

}
