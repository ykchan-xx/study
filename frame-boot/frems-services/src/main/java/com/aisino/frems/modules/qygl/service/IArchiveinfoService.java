package com.aisino.frems.modules.qygl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.aisino.frems.modules.qygl.entity.Archiveinfo;

import java.util.List;
import java.util.Map;

/**
 * 开票回款 Service服务类
 *
 * @author create by caochundi
 * @date 2021-06-22
 */
public interface IArchiveinfoService extends IService<Archiveinfo> {
/**
 * 获取企业数据字典信息
 * @return: java.util.List<java.util.Map>
 * @author yangshunfei
 * @date 2021/7/2 14:44
 */
    List<Map> queryDictInfo();
}
