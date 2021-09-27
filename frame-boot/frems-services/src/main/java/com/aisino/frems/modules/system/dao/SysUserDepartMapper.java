package com.aisino.frems.modules.system.dao;

import java.util.List;

import com.aisino.frems.modules.system.entity.SysUserDepart;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface SysUserDepartMapper extends BaseMapper<SysUserDepart>{

	List<SysUserDepart> getUserDepartByUid(@Param("userId" ) String userId);
}
