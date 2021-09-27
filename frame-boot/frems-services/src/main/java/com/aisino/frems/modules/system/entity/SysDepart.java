package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 组织机构 Entity实体类
 *
 * @author auto generate
 * @date 2020-01-16
 */
@Data
@TableName(value = "sys_depart")
public class SysDepart extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	@TableId(type = IdType.UUID)
	@TableField(value = "id")
	private String id;
	/** 父机构ID */
	@TableField(value = "parent_id")
	private String parentId;
	/** 机构/部门名称 */
	@TableField(value = "depart_name")
	private String departName;
	/** 英文名 */
	@TableField(value = "depart_name_en")
	private String departNameEn;
	/** 缩写 */
	@TableField(value = "depart_name_abbr")
	private String departNameAbbr;
	/** 排序 */
	@TableField(value = "depart_order")
	private Integer departOrder;
	/** 描述 */
	@TableField(value = "description")
	private String description;
	/** 机构类别 1组织机构，2岗位 */
	@TableField(value = "org_category")
	private String orgCategory;
	/** 机构类型 1一级部门 2子部门 */
	@TableField(value = "org_type")
	private String orgType;
	/** 机构编码 */
	@TableField(value = "org_code")
	private String orgCode;
	/** 手机号 */
	@TableField(value = "mobile")
	private String mobile;
	/** 传真 */
	@TableField(value = "fax")
	private String fax;
	/** 地址 */
	@TableField(value = "address")
	private String address;
	/** 备注 */
	@TableField(value = "memo")
	private String memo;
	/** 状态（1启用，0不启用） */
	@TableField(value = "status")
	private String status;
	/** 删除状态（0，正常，1已删除） */
	@TableField(value = "del_flag")
	private String delFlag;
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
