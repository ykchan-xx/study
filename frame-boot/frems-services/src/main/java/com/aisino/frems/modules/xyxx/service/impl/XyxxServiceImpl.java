package com.aisino.frems.modules.xyxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aisino.frems.modules.xyxx.entity.Xyxx;
import com.aisino.frems.modules.xyxx.dao.XyxxDao;
import com.aisino.frems.modules.xyxx.service.IXyxxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 企业人员管理 ServiceImpl服务实现类
 *
 * @author create by yangshunfei
 * @date 2021-06-22
 */
@Service("xyxxService")
public class XyxxServiceImpl extends ServiceImpl<XyxxDao, Xyxx> implements IXyxxService {

    @Resource
    private XyxxDao xyxxDao;
}