package com.aisino.frems.modules.hmd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aisino.frems.modules.hmd.entity.Hmdxx;
import com.aisino.frems.modules.hmd.dao.HmdxxDao;
import com.aisino.frems.modules.hmd.service.IHmdxxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 企业黑名单管理 ServiceImpl服务实现类
 *
 * @author create by yangshunfei
 * @date 2021-06-22
 */
@Service("hmdxxService")
public class HmdxxServiceImpl extends ServiceImpl<HmdxxDao, Hmdxx> implements IHmdxxService {

    @Resource
    private HmdxxDao hmdxxDao;
}