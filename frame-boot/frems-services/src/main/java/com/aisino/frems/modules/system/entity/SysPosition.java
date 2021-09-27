package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 职务 Entity实体类
 *
 * @author auto generate
 * @date 2020-01-16
 */
@Data
@TableName(value = "sys_position")
public class SysPosition extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.UUID)
	@TableField(value = "id")
	private String id;
	/** 职务编码 */
	@TableField(value = "code")
	private String code;
	/** 职务名称 */
	@TableField(value = "name")
	private String name;
	/** 职级 */
	@TableField(value = "rank")
	private String rank;
	/** 公司id */
	@TableField(value = "company_id")
	private String companyId;
	/** 创建人 */
	@TableField(value = "create_by")
	private String createBy;
	/** 创建时间 */
	@TableField(value = "create_time")
	private Date createTime;
	/** 修改人 */
	@TableField(value = "update_by")
	private String updateBy;
	/** 修改时间 */
	@TableField(value = "update_time")
	private Date updateTime;
	/** 组织机构编码 */
	@TableField(value = "sys_org_code")
	private String sysOrgCode;
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
