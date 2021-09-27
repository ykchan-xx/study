package com.aisino.frems.modules.hdl_qyxx.service.impl;

import com.aisino.frems.modules.hdl_qyxx.dao.HdlQyfjDao;
import com.aisino.frems.modules.hdl_qyxx.entity.HdlQyfj;
import com.aisino.frems.modules.hdl_qyxx.service.IHdlQyfjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 企业协议附件信息管理 ServiceImpl服务实现类
 *
 * @author create by yangshunfei
 * @date 2021-06-23
 */
@Service("hdlQyfjService")
public class HdlQyfjServiceImpl extends ServiceImpl<HdlQyfjDao, HdlQyfj> implements IHdlQyfjService {

    @Resource
    private HdlQyfjDao hdlQyfjDao;
}