package com.aisino.frems.modules.system.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysUserRoleVO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**部门id*/
	private String roleId;
	/** 租户编号 */
	private String zhbh;
	/** 企业编号 */
	private String qybh;
	/** 部门编号 */
	private String bmbh;
	/**对应的用户id集合*/
	private List<String> userIdList;

	public SysUserRoleVO() {
		super();
	}

	public SysUserRoleVO(String roleId, List<String> userIdList) {
		super();
		this.roleId = roleId;
		this.userIdList = userIdList;
	}

}
