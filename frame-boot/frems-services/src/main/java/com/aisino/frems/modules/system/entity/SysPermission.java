package com.aisino.frems.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asframework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 菜单权限 Entity实体类
 *
 * @author auto generate
 * @date 2020-01-16
 */
@Data
@TableName(value = "sys_permission")
public class SysPermission extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 主键id */
	@TableId(type = IdType.UUID)
	@TableField(value = "id")
	private String id;
	/** 父id */
	@TableField(value = "parent_id")
	private String parentId;
	/** 菜单标题 */
	@TableField(value = "name")
	private String name;
	/** 路径 */
	@TableField(value = "url")
	private String url;
	/** 组件 */
	@TableField(value = "component")
	private String component;
	/** 组件名字 */
	@TableField(value = "component_name")
	private String componentName;
	/** 一级菜单跳转地址 */
	@TableField(value = "redirect")
	private String redirect;
	/** 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限) */
	@TableField(value = "menu_type")
	private Integer menuType;
	/** 菜单权限编码 */
	@TableField(value = "perms")
	private String perms;
	/** 权限策略1显示2禁用 */
	@TableField(value = "perms_type")
	private String permsType;
	/** 菜单排序 */
	@TableField(value = "sort_no")
	private Double sortNo;
	/** 聚合子路由: 1是0否 */
	@TableField(value = "always_show")
	private boolean alwaysShow;
	/** 菜单图标 */
	@TableField(value = "icon")
	private String icon;
	/** 是否路由菜单: 0:不是  1:是（默认值1） */
	@TableField(value = "is_route")
	private boolean route;
	/** 是否叶子节点:    1:是   0:不是 */
	@TableField(value = "is_leaf")
	private boolean leaf;
	/** 是否缓存该页面:    1:是   0:不是 */
	@TableField(value = "keep_alive")
	private boolean keepAlive;
	/** 是否隐藏路由: 0否,1是 */
	@TableField(value = "hidden")
	private boolean hidden;
	/** 描述 */
	@TableField(value = "description")
	private String description;
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
	/** 删除状态 0正常 1已删除 */
	@TableField(value = "del_flag")
	private Integer delFlag;
	/** 是否添加数据权限1是0否 */
	@TableField(value = "rule_flag")
	private Integer ruleFlag;
	/** 按钮权限状态(0无效1有效) */
	@TableField(value = "status")
	private String status;
	/** 外链菜单打开方式 0/内部打开 1/外部打开 */
	@TableField(value = "internal_or_external")
	private boolean internalOrExternal;
	/** 租户编号 */
	@TableField(value = "zhbh")
	private String zhbh;
	/** 企业编号 */
	@TableField(value = "qybh")
	private String qybh;
	/** 部门编号 */
	@TableField(value = "bmbh")
	private String bmbh;

	public SysPermission() {

	}
	public SysPermission(boolean index) {
		if(index) {
			this.id = "9502685863ab87f0ad1134142788a385";
			this.name="首页";
			this.component="dashboard/Analysis";
			this.url="/dashboard/analysis";
			this.icon="home";
			this.menuType=0;
			this.sortNo=0.0;
			this.ruleFlag=0;
			this.delFlag=0;
			this.alwaysShow=false;
			this.route=true;
			this.keepAlive=true;
			this.leaf=true;
			this.hidden=false;
		}

	}
}
