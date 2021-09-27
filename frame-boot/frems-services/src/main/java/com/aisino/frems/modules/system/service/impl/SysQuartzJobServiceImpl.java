package com.aisino.frems.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aisino.frems.modules.system.entity.SysQuartzJob;
import com.aisino.frems.modules.system.dao.SysQuartzJobDao;
import com.aisino.frems.modules.system.service.ISysQuartzJobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 定时任务配置 ServiceImpl服务实现类
 *
 * @author hxq
 * @date 2020-04-23
 */
@Service("sysQuartzJobService")
public class SysQuartzJobServiceImpl extends ServiceImpl<SysQuartzJobDao, SysQuartzJob> implements ISysQuartzJobService {

    @Resource
    private SysQuartzJobDao sysQuartzJobDao;
}
