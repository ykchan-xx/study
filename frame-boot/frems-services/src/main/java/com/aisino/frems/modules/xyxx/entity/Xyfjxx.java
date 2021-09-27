package com.aisino.frems.modules.xyxx.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 企业协议附件信息管理 Entity实体类
 *
 * @author create by yangshunfei
 * @date 2021-06-23
 */
@Data
@TableName(value = "qy_xyfjxx")
public class Xyfjxx extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 主键id */
	@TableId(type = IdType.AUTO)
	@TableField(value = "ID")
	private Long id;
	/** 协议编号 */
	@TableField(value = "XYBH")
	private String xybh;
	/** 协议描述 */
	@TableField(value = "XYMS")
	private String xyms;

}
