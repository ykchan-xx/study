package com.aisino.frems.modules.hdl_qyxx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

/**
 * 企业协议附件信息管理 Entity实体类
 *
 * @author create by yangshunfei
 * @date 2021-06-23
 */
@Data
@TableName(value = "hdl_qyfjxx")
public class HdlQyfj extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 主键id */
	@TableId(type = IdType.AUTO)
	@TableField(value = "id")
	private Long id;
	/** 企业编码 */
	@TableField(value = "qybm")
	private String qybm;
	/** 协议描述 */
	@TableField(value = "xysnr")
	private String xysnr;

}
