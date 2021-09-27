package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 字典明细 Entity实体类
 *
 * @author auto generate
 * @date 2020-01-16
 */
@Data
@TableName(value = "sys_dict_item")
public class SysDictItem extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.UUID)
	@TableField(value = "id")
	private String id;
	/** 字典id */
	@TableField(value = "dict_id")
	private String dictId;
	/** 字典项文本 */
	@TableField(value = "item_text")
	private String itemText;
	/** 字典项值 */
	@TableField(value = "item_value")
	private String itemValue;
	/** 描述 */
	@TableField(value = "description")
	private String description;
	/** 排序 */
	@TableField(value = "sort_order")
	private Integer sortOrder;
	/** 状态（1启用 0不启用） */
	@TableField(value = "status")
	private Integer status;
	/**  */
	@TableField(value = "create_by")
	private String createBy;
	/**  */
	@TableField(value = "create_time")
	private Date createTime;
	/**  */
	@TableField(value = "update_by")
	private String updateBy;
	/**  */
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
