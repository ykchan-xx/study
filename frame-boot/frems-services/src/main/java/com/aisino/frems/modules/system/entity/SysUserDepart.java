package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

/**
 * 用户组织机构 Entity实体类
 *
 * @author auto generate
 * @date 2020-01-16
 */
@Data
@TableName(value = "sys_user_depart")
public class SysUserDepart extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.UUID)
	@TableField(value = "ID")
	private String id;
	/** 用户id */
	@TableField(value = "user_id")
	private String userId;
	/** 部门id */
	@TableField(value = "dep_id")
	private String depId;
	/** 租户编号 */
	@TableField(value = "zhbh")
	private String zhbh;
	/** 企业编号 */
	@TableField(value = "qybh")
	private String qybh;
	/** 部门编号 */
	@TableField(value = "bmbh")
	private String bmbh;

	public SysUserDepart() {

	}

	public SysUserDepart(String id, String userId, String depId) {
		super();
		this.id = id;
		this.userId = userId;
		this.depId = depId;
	}

	public SysUserDepart(String id, String departId) {
		this.userId = id;
		this.depId = departId;
	}
}
