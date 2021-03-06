package com.aisino.frems.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.aisino.frems.modules.system.entity.SysDepart;

import java.util.List;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 *
 * @Author: Steve
 * @Since：   2019-01-22
 */
public interface SysDepartMapper extends BaseMapper<SysDepart> {

	/**
	 * 根据用户ID查询部门集合
	 */
	List<SysDepart> queryUserDeparts(@Param("userId" ) String userId);

	/**
	 * 根据用户名查询部门
	 *
	 * @param username
	 * @return
	 */
	List<SysDepart> queryDepartsByUsername(@Param("username" ) String username);

	@Select("select id from sys_depart where org_code=#{orgCode}")
	String queryDepartIdByOrgCode(@Param("orgCode" ) String orgCode);

	@Select("select id,parent_id from sys_depart where id=#{departId}")
	SysDepart getParentDepartId(@Param("departId" ) String departId);

}
