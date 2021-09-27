package com.aisino.frems.modules.qygl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aisino.frems.modules.qygl.entity.Archiveinfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 开票回款Dao类
 *
 * @author create by caochundi
 * @date 2021-06-22
 */
public interface ArchiveinfoDao extends BaseMapper<Archiveinfo> {
    @Select("select distinct qymc,qybm from qy_archiveinfo")
    List<Map> queryDictInfo();
}
