package com.aisino.frems.modules.qygl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aisino.frems.modules.qygl.entity.Archiveinfo;
import com.aisino.frems.modules.qygl.dao.ArchiveinfoDao;
import com.aisino.frems.modules.qygl.service.IArchiveinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 开票回款 ServiceImpl服务实现类
 *
 * @author create by caochundi
 * @date 2021-06-22
 */
@Service("archiveinfoService")
public class ArchiveinfoServiceImpl extends ServiceImpl<ArchiveinfoDao, Archiveinfo> implements IArchiveinfoService {

    @Resource
    private ArchiveinfoDao archiveinfoDao;

    /**
     * 获取企业数据字典信息
     *
     * @return: java.util.List<java.util.Map>
     * @author yangshunfei
     * @date 2021/7/2 14:44
     */
    @Override
    public List<Map> queryDictInfo() {
        return archiveinfoDao.queryDictInfo();
    }
}
