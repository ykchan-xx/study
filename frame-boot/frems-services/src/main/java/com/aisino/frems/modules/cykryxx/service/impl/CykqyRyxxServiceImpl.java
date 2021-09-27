package com.aisino.frems.modules.cykryxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aisino.frems.modules.cykryxx.entity.CykqyRyxx;
import com.aisino.frems.modules.cykryxx.dao.CykqyRyxxDao;
import com.aisino.frems.modules.cykryxx.service.ICykqyRyxxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * cyk人员信息 ServiceImpl服务实现类
 *
 * @author create by cyk
 * @date 2021-09-27
 */
@Service("cykqyRyxxService")
public class CykqyRyxxServiceImpl extends ServiceImpl<CykqyRyxxDao, CykqyRyxx> implements ICykqyRyxxService {

    @Resource
    private CykqyRyxxDao cykqyRyxxDao;
}