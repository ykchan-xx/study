package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 系统通告 Entity实体类
 *
 * @author auto generate
 * @date 2020-01-16
 */
@Data
@TableName(value = "sys_announcement")
public class SysAnnouncement extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.UUID)
	@TableField(value = "id")
	private String id;
	/** 标题 */
	@TableField(value = "titile")
	private String titile;
	/** 内容 */
	@TableField(value = "msg_content")
	private String msgContent;
	/** 开始时间 */
	@TableField(value = "start_time")
	private Date startTime;
	/** 结束时间 */
	@TableField(value = "end_time")
	private Date endTime;
	/** 发布人 */
	@TableField(value = "sender")
	private String sender;
	/** 优先级（L低，M中，H高） */
	@TableField(value = "priority")
	private String priority;
	/** 消息类型1:通知公告2:系统消息 */
	@TableField(value = "msg_category")
	private String msgCategory;
	/** 通告对象类型（USER:指定用户，ALL:全体用户） */
	@TableField(value = "msg_type")
	private String msgType;
	/** 发布状态（0未发布，1已发布，2已撤销） */
	@TableField(value = "send_status")
	private String sendStatus;
	/** 发布时间 */
	@TableField(value = "send_time")
	private Date sendTime;
	/** 撤销时间 */
	@TableField(value = "cancel_time")
	private Date cancelTime;
	/** 删除状态（0，正常，1已删除） */
	@TableField(value = "del_flag")
	private String delFlag;
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
	/** 指定用户 */
	@TableField(value = "user_ids")
	private String userIds;
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
