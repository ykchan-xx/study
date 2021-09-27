package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 字典类别 Entity实体类
 *
 * @author auto generate
 * @date 2020-01-16
 */
@Data
@TableName(value = "sys_dict")
public class SysDict extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.UUID)
	@TableField(value = "id")
	private String id;
	/** 字典名称 */
	@TableField(value = "dict_name")
	private String dictName;
	/** 字典编码 */
	@TableField(value = "dict_code")
	private String dictCode;
	/** 描述 */
	@TableField(value = "description")
	private String description;
	/** 删除状态 */
	@TableField(value = "del_flag")
	private Integer delFlag;
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
	/** 字典类型0为string,1为number */
	@TableField(value = "type")
	private Integer type;
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
