package com.aisino.frems.modules.hdl_ygxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.aisino.frems.modules.hdl_ygxx.entity.HdlYgxx;

import java.util.List;
import java.util.Map;

/**
 * 员工信息 Service服务类
 *
 * @author create by huangdanlei
 * @date 2021-07-23
 */
public interface IHdlYgxxService extends IService<HdlYgxx> {
    List<Map> queryDictInfo();
}