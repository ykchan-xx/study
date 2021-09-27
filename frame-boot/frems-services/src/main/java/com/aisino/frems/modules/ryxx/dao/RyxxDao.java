package com.aisino.frems.modules.ryxx.dao;

import com.aisino.frems.modules.ryxx.entity.Ryxx;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 企业人员管理Dao类
 *
 * @author create by yangshunfei
 * @date 2021-06-22
 */
public interface RyxxDao extends BaseMapper<Ryxx> {

    @Select("select count(1) as rs,SUBSTR(lrsj,1,7) rq from qy_ryxx group by rq order by rq")
    List<Map> getRyxxMonthCount();
}
