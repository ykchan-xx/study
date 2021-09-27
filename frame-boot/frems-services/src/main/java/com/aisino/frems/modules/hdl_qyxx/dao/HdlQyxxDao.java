package com.aisino.frems.modules.hdl_qyxx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aisino.frems.modules.hdl_qyxx.entity.HdlQyxx;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 企业信息Dao类
 * 
 * @author create by huangdanlei
 * @date 2021-07-23
 */
public interface HdlQyxxDao extends BaseMapper<HdlQyxx> {
    @Select("select distinct qymc,qybm from hdl_qyxx")
    List<Map> queryDictInfo();
	
}
