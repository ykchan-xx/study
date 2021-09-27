package com.aisino.frems.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.aisino.frems.modules.system.entity.SysConfig;

import java.util.List;

/**
 * 系统参数 Service服务类
 *
 * @author zhuxiaoyan
 * @date 2020-03-31
 */
public interface ISysConfigService extends IService<SysConfig> {
    /**
     * 根据code查询对应参数
     * @param paraCode 字典
     * @return 查询列表
     */
    List<SysConfig> findByParaCode(String paraCode);

    /**
     * 获取参数字典
     * @param paraCode 字典
     * @return 字典参数值
     */
    String getParaValueByCode(String paraCode);

    /**
     * 更新参数
     * @param paraValue 字典参数值
     * @param paraCode 字典
     */
    void updateParaValueByCode(String paraValue, String paraCode);
}
