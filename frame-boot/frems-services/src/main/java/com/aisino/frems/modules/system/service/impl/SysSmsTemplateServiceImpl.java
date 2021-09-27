package com.aisino.frems.modules.system.service.impl;

import com.aisino.frems.modules.system.dao.SysSmsTemplateMapper;
import com.aisino.frems.modules.system.entity.SysSmsTemplate;
import com.aisino.frems.modules.system.service.ISysSmsTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 消息模板
 * @Author: jeecg-boot
 * @Date:  2019-04-09
 * @Version: V1.0
 */
@Service
public class SysSmsTemplateServiceImpl extends ServiceImpl<SysSmsTemplateMapper, SysSmsTemplate> implements ISysSmsTemplateService {

    @Resource
    private SysSmsTemplateMapper sysMessageTemplateMapper;


    @Override
    public List<SysSmsTemplate> selectByCode(String code) {
        return sysMessageTemplateMapper.selectByCode(code);
    }
}
