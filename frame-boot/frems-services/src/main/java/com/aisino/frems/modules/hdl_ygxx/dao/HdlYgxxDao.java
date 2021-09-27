package com.aisino.frems.modules.hdl_ygxx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aisino.frems.modules.hdl_ygxx.entity.HdlYgxx;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 员工信息Dao类
 * 
 * @author create by huangdanlei
 * @date 2021-07-23
 */
public interface HdlYgxxDao extends BaseMapper<HdlYgxx> {
    @Select("select distinct ygname,ygsfzid from hdl_ygxx")
    List<Map> queryDictInfo();

}
