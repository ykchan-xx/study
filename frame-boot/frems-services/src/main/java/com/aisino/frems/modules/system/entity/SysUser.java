package com.aisino.frems.modules.system.entity;

import com.aisino.frems.common.annotation.Dict;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 用户 Entity实体类
 *
 * @author auto generate
 * @date 2020-01-16
 */
@Data
@TableName(value = "sys_user")
public class SysUser extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 主键id */
	@TableId(type = IdType.UUID)
	@TableField(value = "id")
	private String id;
	/** 登录账号 */
	@TableField(value = "username")
	private String username;
	/** 真实姓名 */
	@TableField(value = "realname")
	private String realname;
	/** 密码 */
	@TableField(value = "password")
	private String password;
	/** md5密码盐 */
	@TableField(value = "salt")
	private String salt;
	/** 头像 */
	@TableField(value = "avatar")
	private String avatar;
	/** 生日 */
	@TableField(value = "birthday")
	private Date birthday;
	/** 性别(0-默认未知,1-男,2-女) */
	@TableField(value = "sex")
	@Dict(dicCode = "sex")
	private Integer sex;
	/** 电子邮件 */
	@TableField(value = "email")
	private String email;
	/** 电话 */
	@TableField(value = "phone")
	private String phone;
	/** 机构编码 */
	@TableField(value = "org_code")
	private String orgCode;
	/** 用户状态(1-正常,2-冻结) */
	@TableField(value = "status")
	@Dict(dicCode = "user_status")
	private Integer status;
	/** 删除状态(0-正常,1-已删除) */
	@TableField(value = "del_flag")
	private Integer delFlag;
	/** 同步工作流引擎(1-同步,0-不同步) */
	@TableField(value = "activiti_sync")
	private Integer activitiSync;
	/** 工号，唯一键 */
	@TableField(value = "work_no")
	private String workNo;
	/** 职务，关联职务表 */
	@TableField(value = "post")
	private String post;
	/** 座机号 */
	@TableField(value = "telephone")
	private String telephone;
	/** 创建人 */
	@TableField(value = "create_by")
	private String createBy;
	/** 创建时间 */
	@TableField(value = "create_time")
	private Date createTime;
	/** 更新人 */
	@TableField(value = "update_by")
	private String updateBy;
	/** 更新时间 */
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
