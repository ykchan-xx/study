package com.aisino.frems.modules.system.service.impl;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.aisino.frems.modules.system.entity.SysRole;
import com.aisino.frems.modules.system.entity.SysUser;
import com.aisino.frems.modules.system.entity.SysUserRole;
import com.aisino.frems.modules.system.dao.SysUserRoleMapper;
import com.aisino.frems.modules.system.service.ISysRoleService;
import com.aisino.frems.modules.system.service.ISysUserRoleService;
import com.aisino.frems.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @Author scott
 * @since 2018-12-21
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

	@Autowired
	private ISysUserService userService;
	@Autowired
	private ISysRoleService roleService;
	@Resource
	private SysUserRoleMapper sysUserRoleMapper;

	/**
	 * 查询所有用户对应的角色信息
	 */
	@Override
	public Map<String,String> queryUserRole() {
		List<SysUserRole> uRoleList = this.list();
		List<SysUser> userList = userService.list();
		List<SysRole> roleList = roleService.list();
		Map<String,String> map = new IdentityHashMap<>();
		String userId = "";
		String roleId = "";
		String roleName = "";
		if(uRoleList != null && uRoleList.size() > 0) {
			for(SysUserRole uRole : uRoleList) {
				roleId = uRole.getRoleId();
				for(SysUser user : userList) {
					userId = user.getId();
					if(uRole.getUserId().equals(userId)) {
						roleName = this.searchByRoleId(roleList,roleId);
						map.put(userId, roleName);
					}
				}
			}
			return map;
		}
		return map;
	}

	/**
	 *
	 * @return根据角色id查询用户id列表
	 */
	@Override
	public List<String> getUserIDListByRoleName(String roleName) {
		return sysUserRoleMapper.getUserIDListByRoleName(roleName);
	}

	/**
	 * queryUserRole调用的方法
	 * @param roleList
	 * @param roleId
	 * @return
	 */
	private String searchByRoleId(List<SysRole> roleList, String roleId) {
		while(true) {
			for(SysRole role : roleList) {
				if(roleId.equals(role.getId())) {
					return role.getRoleName();
				}
			}
		}
	}

}
