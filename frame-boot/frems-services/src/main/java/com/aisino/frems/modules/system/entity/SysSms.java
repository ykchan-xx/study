package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 消息 Entity实体类
 *
 * @author auto generate
 * @date 2020-01-16
 */
@Data
@TableName(value = "sys_sms")
public class SysSms extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	@TableId(type = IdType.UUID)
	@TableField(value = "id")
	private String id;
	/** 消息标题 */
	@TableField(value = "es_title")
	private String esTitle;
	/** 发送方式：1短信 2邮件 3微信 */
	@TableField(value = "es_type")
	private String esType;
	/** 接收人 */
	@TableField(value = "es_receiver")
	private String esReceiver;
	/** 发送所需参数Json格式 */
	@TableField(value = "es_param")
	private String esParam;
	/** 推送内容 */
	@TableField(value = "es_content")
	private String esContent;
	/** 推送时间 */
	@TableField(value = "es_send_time")
	private Date esSendTime;
	/** 推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送 */
	@TableField(value = "es_send_status")
	private String esSendStatus;
	/** 发送次数 超过5次不再发送 */
	@TableField(value = "es_send_num")
	private Integer esSendNum;
	/** 推送失败原因 */
	@TableField(value = "es_result")
	private String esResult;
	/** 备注 */
	@TableField(value = "remark")
	private String remark;
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
