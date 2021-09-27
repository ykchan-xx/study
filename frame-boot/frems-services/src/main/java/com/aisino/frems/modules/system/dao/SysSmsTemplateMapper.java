package com.aisino.frems.modules.system.dao;

import com.aisino.frems.modules.system.entity.SysSmsTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description: 消息模板
 * @Author: jeecg-boot
 * @Date:  2019-04-09
 * @Version: V1.0
 */
public interface SysSmsTemplateMapper extends BaseMapper<SysSmsTemplate> {
    @Select("SELECT * FROM SYS_SMS_TEMPLATE WHERE TEMPLATE_CODE = #{code}")
    List<SysSmsTemplate> selectByCode(String code);
}
