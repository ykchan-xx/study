package com.aisino.frems.modules.hdl_ygxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aisino.frems.modules.hdl_ygxx.entity.HdlYgxx;
import com.aisino.frems.modules.hdl_ygxx.dao.HdlYgxxDao;
import com.aisino.frems.modules.hdl_ygxx.service.IHdlYgxxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 员工信息 ServiceImpl服务实现类
 *
 * @author create by huangdanlei
 * @date 2021-07-23
 */
@Service("hdlYgxxService")
public class HdlYgxxServiceImpl extends ServiceImpl<HdlYgxxDao, HdlYgxx> implements IHdlYgxxService {

    @Resource
    private HdlYgxxDao hdlYgxxDao;
    @Override
    public List<Map> queryDictInfo() {
        return hdlYgxxDao.queryDictInfo();
    }

    }
