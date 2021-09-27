package com.aisino.frems.modules.system.service;

import java.util.List;
import java.util.Map;

import com.aisino.frems.modules.system.entity.SysUserRole;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @Author scott
 * @since 2018-12-21
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

	/**
	 * 查询所有的用户角色信息
	 * @return
	 */
	Map<String,String> queryUserRole();

	/**
	 *
	 * @return根据角色id查询用户id列表
	 */
	List<String> getUserIDListByRoleName(String roleName);

}
