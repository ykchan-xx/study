package com.aisino.frems.modules.system.service;


import com.aisino.frems.modules.system.entity.SysSmsTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 消息模板
 * @Author: jeecg-boot
 * @Date:  2019-04-09
 * @Version: V1.0
 */
public interface ISysSmsTemplateService extends IService<SysSmsTemplate> {
    List<SysSmsTemplate> selectByCode(String code);
}
