package com.aisino.frems.modules.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.aisino.frems.modules.system.entity.SysCategory;
import com.aisino.frems.modules.system.model.TreeSelectModel;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 分类字典
 * @Author: jeecg-boot
 * @Date:   2019-05-29
 * @Version: V1.0
 */
public interface SysCategoryMapper extends BaseMapper<SysCategory> {

	/**
	  *  根据父级ID查询树节点数据
	 * @param pid
	 * @return
	 */
	List<TreeSelectModel> queryListByPid(@Param("pid" ) String pid, @Param("query" ) Map<String, String> query);

	@Select("SELECT ID FROM sys_category WHERE CODE = #{code,jdbcType=VARCHAR}")
	String queryIdByCode(@Param("code" ) String code);


}
