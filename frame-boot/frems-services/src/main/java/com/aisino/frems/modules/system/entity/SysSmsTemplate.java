package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 消息模版 Entity实体类
 *
 * @author auto generate
 * @date 2020-01-16
 */
@Data
@TableName(value = "sys_sms_template")
public class SysSmsTemplate extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId(type = IdType.UUID)
	@TableField(value = "id")
	private String id;
	/** 模板标题 */
	@TableField(value = "template_name")
	private String templateName;
	/** 模板CODE */
	@TableField(value = "template_code")
	private String templateCode;
	/** 模板类型：1短信 2邮件 3微信 */
	@TableField(value = "template_type")
	private String templateType;
	/** 模板内容 */
	@TableField(value = "template_content")
	private String templateContent;
	/** 模板测试json */
	@TableField(value = "template_test_json")
	private String templateTestJson;
	/** 创建日期 */
	@TableField(value = "create_time")
	private Date createTime;
	/** 创建人登录名称 */
	@TableField(value = "create_by")
	private String createBy;
	/** 更新日期 */
	@TableField(value = "update_time")
	private Date updateTime;
	/** 更新人登录名称 */
	@TableField(value = "update_by")
	private String updateBy;
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
