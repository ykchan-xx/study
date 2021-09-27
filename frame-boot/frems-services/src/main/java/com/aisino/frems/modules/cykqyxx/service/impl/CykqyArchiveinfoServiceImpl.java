package com.aisino.frems.modules.cykqyxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aisino.frems.modules.cykqyxx.entity.CykqyArchiveinfo;
import com.aisino.frems.modules.cykqyxx.dao.CykqyArchiveinfoDao;
import com.aisino.frems.modules.cykqyxx.service.ICykqyArchiveinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * cyk企业信息 ServiceImpl服务实现类
 *
 * @author create by cyk
 * @date 2021-09-24
 */
@Service("cykqyArchiveinfoService")
public class CykqyArchiveinfoServiceImpl extends ServiceImpl<CykqyArchiveinfoDao, CykqyArchiveinfo> implements ICykqyArchiveinfoService {

    @Resource
    private CykqyArchiveinfoDao cykqyArchiveinfoDao;
}