package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 分类字典 Entity实体类
 *
 * @author auto generate
 * @date 2020-01-16
 */
@Data
@TableName(value = "sys_category")
public class SysCategory extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.UUID)
	@TableField(value = "id")
	private String id;
	/** 父级节点 */
	@TableField(value = "pid")
	private String pid;
	/** 类型名称 */
	@TableField(value = "name")
	private String name;
	/** 类型编码 */
	@TableField(value = "code")
	private String code;
	/** 创建人 */
	@TableField(value = "create_by")
	private String createBy;
	/** 创建日期 */
	@TableField(value = "create_time")
	private Date createTime;
	/** 更新人 */
	@TableField(value = "update_by")
	private String updateBy;
	/** 更新日期 */
	@TableField(value = "update_time")
	private Date updateTime;
	/** 所属部门 */
	@TableField(value = "sys_org_code")
	private String sysOrgCode;
	/** 是否有子节点 */
	@TableField(value = "has_child")
	private String hasChild;
	/** 租户编号 */
	@TableField(value = "zhbh")
	private String zhbh;
	/** 企业编号 */
	@TableField(value = "qybh")
	private String qybh;
	/** 部门编号 */
	@TableField(value = "bmbh")
	private String bmbh;

}
