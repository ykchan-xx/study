package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 用户代理人设置 Entity实体类
 *
 * @author auto generate
 * @date 2020-01-16
 */
@Data
@TableName(value = "sys_user_agent")
public class SysUserAgent extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 序号 */
	@TableId(type = IdType.UUID)
	@TableField(value = "id")
	private String id;
	/** 用户名 */
	@TableField(value = "user_name")
	private String userName;
	/** 代理人用户名 */
	@TableField(value = "agent_user_name")
	private String agentUserName;
	/** 代理开始时间 */
	@TableField(value = "start_time")
	private Date startTime;
	/** 代理结束时间 */
	@TableField(value = "end_time")
	private Date endTime;
	/** 状态0无效1有效 */
	@TableField(value = "status")
	private String status;
	/** 创建人名称 */
	@TableField(value = "create_name")
	private String createName;
	/** 创建人登录名称 */
	@TableField(value = "create_by")
	private String createBy;
	/** 创建日期 */
	@TableField(value = "create_time")
	private Date createTime;
	/** 更新人名称 */
	@TableField(value = "update_name")
	private String updateName;
	/** 更新人登录名称 */
	@TableField(value = "update_by")
	private String updateBy;
	/** 更新日期 */
	@TableField(value = "update_time")
	private Date updateTime;
	/** 所属部门 */
	@TableField(value = "sys_org_code")
	private String sysOrgCode;
	/** 所属公司 */
	@TableField(value = "sys_company_code")
	private String sysCompanyCode;
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
