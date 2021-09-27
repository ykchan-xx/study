package com.aisino.frems.modules.hdl_qyxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aisino.frems.modules.hdl_qyxx.entity.HdlQyxx;
import com.aisino.frems.modules.hdl_qyxx.dao.HdlQyxxDao;
import com.aisino.frems.modules.hdl_qyxx.service.IHdlQyxxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 企业信息 ServiceImpl服务实现类
 *
 * @author create by huangdanlei
 * @date 2021-07-23
 */
@Service("hdlQyxxService")
public class HdlQyxxServiceImpl extends ServiceImpl<HdlQyxxDao, HdlQyxx> implements IHdlQyxxService {

    @Resource
    private HdlQyxxDao hdlQyxxDao;
    @Override
    public List<Map> queryDictInfo() {
        return hdlQyxxDao.queryDictInfo();
    }
}