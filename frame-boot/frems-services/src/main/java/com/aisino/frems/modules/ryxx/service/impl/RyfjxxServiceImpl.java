package com.aisino.frems.modules.ryxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aisino.frems.modules.ryxx.entity.Ryfjxx;
import com.aisino.frems.modules.ryxx.dao.RyfjxxDao;
import com.aisino.frems.modules.ryxx.service.IRyfjxxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 企业协议附件信息管理 ServiceImpl服务实现类
 *
 * @author create by yangshunfei
 * @date 2021-06-23
 */
@Service("ryfjxxService")
public class RyfjxxServiceImpl extends ServiceImpl<RyfjxxDao, Ryfjxx> implements IRyfjxxService {

    @Resource
    private RyfjxxDao ryfjxxDao;
}