package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 系统参数 Entity实体类
 *
 * @author zhuxiaoyan
 * @date 2020-03-31
 */
@Data
@TableName(value = "sys_config")
public class SysConfig extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.UUID)
	@TableField(value = "id")
	private String id;
	/** 参数名称 */
	@TableField(value = "para_name")
	private String paraName;
	/** 参数代码 */
	@TableField(value = "para_code")
	private String paraCode;
	/** 参数值 */
	@TableField(value = "para_value")
	private String paraValue;
	/** 参数描述 */
	@TableField(value = "para_desc")
	private String paraDesc;
	/** 创建人登录名称 */
	@TableField(value = "create_by")
	private String createBy;
	/** 创建日期 */
	@TableField(value = "create_time")
	private Date createTime;
	/** 创建人所属部门 */
	@TableField(value = "sys_org_code")
	private String sysOrgCode;
	/** 更新人登录名称 */
	@TableField(value = "update_by")
	private String updateBy;
	/** 更新日期 */
	@TableField(value = "update_time")
	private Date updateTime;
	/** 删除状态(0-正常,1-已删除) */
	@TableField(value = "del_flag")
	private Integer delFlag;
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
