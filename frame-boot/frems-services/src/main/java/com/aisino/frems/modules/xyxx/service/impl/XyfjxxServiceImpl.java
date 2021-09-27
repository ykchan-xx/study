package com.aisino.frems.modules.xyxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aisino.frems.modules.xyxx.entity.Xyfjxx;
import com.aisino.frems.modules.xyxx.dao.XyfjxxDao;
import com.aisino.frems.modules.xyxx.service.IXyfjxxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 企业协议附件信息管理 ServiceImpl服务实现类
 *
 * @author create by yangshunfei
 * @date 2021-06-23
 */
@Service("xyfjxxService")
public class XyfjxxServiceImpl extends ServiceImpl<XyfjxxDao, Xyfjxx> implements IXyfjxxService {

    @Resource
    private XyfjxxDao xyfjxxDao;
}