package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 系统日志 Entity实体类
 *
 * @author auto generate
 * @date 2020-01-16
 */
@Data
@TableName(value = "sys_log")
public class SysLog extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.UUID)
	@TableField(value = "id")
	private String id;
	/** 日志类型（1登录日志，2操作日志） */
	@TableField(value = "log_type")
	private Integer logType;
	/** 日志内容 */
	@TableField(value = "log_content")
	private String logContent;
	/** 操作类型 */
	@TableField(value = "operate_type")
	private Integer operateType;
	/** 操作用户账号 */
	@TableField(value = "userid")
	private String userid;
	/** 操作用户名称 */
	@TableField(value = "username")
	private String username;
	/** IP */
	@TableField(value = "ip")
	private String ip;
	/** 请求java方法 */
	@TableField(value = "method")
	private String method;
	/** 请求路径 */
	@TableField(value = "request_url")
	private String requestUrl;
	/** 请求参数 */
	@TableField(value = "request_param")
	private String requestParam;
	/** 请求类型 */
	@TableField(value = "request_type")
	private String requestType;
	/** 耗时 */
	@TableField(value = "cost_time")
	private Long costTime;
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
