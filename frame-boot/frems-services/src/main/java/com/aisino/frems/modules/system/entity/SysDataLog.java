package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 数据日志 Entity实体类
 *
 * @author auto generate
 * @date 2020-01-16
 */
@Data
@TableName(value = "sys_data_log")
public class SysDataLog extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.UUID)
	@TableField(value = "id")
	private String id;
	/** 创建人登录名称 */
	@TableField(value = "create_by")
	private String createBy;
	/** 创建日期 */
	@TableField(value = "create_time")
	private Date createTime;
	/** 更新人登录名称 */
	@TableField(value = "update_by")
	private String updateBy;
	/** 更新日期 */
	@TableField(value = "update_time")
	private Date updateTime;
	/** 表名 */
	@TableField(value = "data_table")
	private String dataTable;
	/** 数据ID */
	@TableField(value = "data_id")
	private String dataId;
	/** 数据内容 */
	@TableField(value = "data_content")
	private String dataContent;
	/** 版本号 */
	@TableField(value = "data_version")
	private Integer dataVersion;
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
