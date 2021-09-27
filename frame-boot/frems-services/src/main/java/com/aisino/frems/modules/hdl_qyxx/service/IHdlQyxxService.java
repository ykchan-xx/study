package com.aisino.frems.modules.hdl_qyxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.aisino.frems.modules.hdl_qyxx.entity.HdlQyxx;

import java.util.List;
import java.util.Map;

/**
 * 企业信息 Service服务类
 *
 * @author create by huangdanlei
 * @date 2021-07-23
 */
public interface IHdlQyxxService extends IService<HdlQyxx> {
    List<Map> queryDictInfo();
}