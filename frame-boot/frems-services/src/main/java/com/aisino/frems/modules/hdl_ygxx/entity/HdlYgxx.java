package com.aisino.frems.modules.hdl_ygxx.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 员工信息 Entity实体类
 * 
 * @author create by huangdanlei
 * @date 2021-07-23
 */
@Data
@TableName(value = "hdl_ygxx")
public class HdlYgxx extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId(type = IdType.AUTO)
	@TableField(value = "id")
	private Long id;
	/** 员工名字 */
	@TableField(value = "ygname")
	private String ygname;
	/** 员工性别 */
	@TableField(value = "ygsex")
	private String ygsex;
	/** 员工企业 */
	@TableField(value = "ygqy")
	private String ygqy;
	/** 员工出生日期 */
	@TableField(value = "ygbirth")
	private String ygbirth;
	/** 员工身份证号 */
	@TableField(value = "ygsfzid")
	private String ygsfzid;
	/** 员工邮箱 */
	@TableField(value = "ygyx")
	private String ygyx;
	/** 员工号 */
	@TableField(value = "ygqyid")
	private String ygqyid;
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
