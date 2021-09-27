package com.aisino.frems.modules.system.service.impl;

import com.aisino.frems.common.constant.QuartzConstant;
import com.aisino.frems.common.util.oConvertUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aisino.frems.modules.system.entity.SysConfig;
import com.aisino.frems.modules.system.dao.SysConfigDao;
import com.aisino.frems.modules.system.service.ISysConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统参数 ServiceImpl服务实现类
 *
 * @author zhuxiaoyan
 * @date 2020-03-31
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfig> implements ISysConfigService {

    @Resource
    private SysConfigDao sysConfigDao;

    /**
     * 根据code查询对应参数
     *
     * @param paraCode 字典
     * @return 查询列表
     */
    @Override
    public List<SysConfig> findByParaCode(String paraCode) {
        Wrapper<SysConfig> sysConfigWrapper = new LambdaQueryWrapper<SysConfig>()
                .eq(SysConfig::getDelFlag, QuartzConstant.DEL_NO.getValue()).eq(SysConfig::getParaCode, paraCode);
        return sysConfigDao.selectList(sysConfigWrapper);
    }

    /**
     * 获取参数字典
     *
     * @param paraCode 字典
     * @return 字典参数值
     */
    @Override
    public String getParaValueByCode(String paraCode) {
        List<SysConfig> sysConfigList = this.findByParaCode(paraCode);
        if (oConvertUtils.isNotEmpty(sysConfigList) && sysConfigList.size()>0){
            return sysConfigList.get(0).getParaValue();
        }
        return null;
    }

    /**
     * 更新参数
     *
     * @param paraValue 字典参数值
     * @param paraCode  字典
     */
    @Override
    public void updateParaValueByCode(String paraValue, String paraCode) {
        List<SysConfig> sysConfigList = this.findByParaCode(paraCode);
        if (oConvertUtils.isNotEmpty(sysConfigList) && sysConfigList.size()>0){
            SysConfig sysConfig = sysConfigList.get(0);
            sysConfig.setParaValue(paraValue);
            sysConfigDao.updateById(sysConfig);
        }
    }
}
