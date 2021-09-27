package com.aisino.frems.modules.yjxx.service.impl;

import com.aisino.frems.modules.ryxx.entity.Ryxx;
import com.aisino.frems.modules.yjxx.dao.QyYjxxbDao;
import com.aisino.frems.modules.yjxx.dto.QyYjDto;
import com.aisino.frems.modules.yjxx.entity.QyYjxxb;
import com.aisino.frems.modules.yjxx.service.IQyYjxxbService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 预警信息 ServiceImpl服务实现类
 *
 * @author create by luozheng
 * @date 2021-07-02
 */
@Service("qyYjxxbService")
public class QyYjxxbServiceImpl extends ServiceImpl<QyYjxxbDao, QyYjxxb> implements IQyYjxxbService {

    @Resource
    private QyYjxxbDao qyYjxxbDao;

    @Override
    public List<QyYjxxb> findByYzzg(String ywxm, String zjzl, String zjhm, String gjdq) {
        QueryWrapper<QyYjxxb> wrapper = new QueryWrapper<QyYjxxb>()
                .eq("ywxm",ywxm)
                .eq("zjzl",zjzl)
                .eq("zjhm",zjhm)
                .eq("gjdq",gjdq);
        List<QyYjxxb> yjxxbList=this.list(wrapper);
        return yjxxbList;
    }

    /**
     * 判断该企业人员是否被预警
     *
     * @param yjDto
     * @return: java.lang.Boolean
     * @author yangshunfei
     * @date 2021/7/8 15:15
     */
    @Override
    public List<QyYjxxb> isWarned(QyYjDto yjDto,String yjlx) {
        QueryWrapper<QyYjxxb> wrapper = new QueryWrapper<QyYjxxb>();
        if (StringUtils.isNotBlank(yjDto.getYwxm())) {
            wrapper.eq("ywxm",yjDto.getYwxm());
        }
        if (StringUtils.isNotBlank(yjDto.getCsrq())) {
            wrapper.eq("csrq",yjDto.getCsrq());
        }
        if (StringUtils.isNotBlank(yjDto.getZjzl())) {
            wrapper.eq("zjzl",yjDto.getZjzl());
        }
        if (StringUtils.isNotBlank(yjDto.getGjdq())) {
            wrapper.eq("gjdq",yjDto.getGjdq());
        }
        if (StringUtils.isNotBlank(yjDto.getSex())) {
            wrapper.eq("sex",yjDto.getSex());
        }
        if (StringUtils.isNotBlank(yjDto.getZjhm())) {
            wrapper.eq("zjhm",yjDto.getZjhm());
        }
            wrapper.eq("yjlx",yjlx);
        return qyYjxxbDao.selectList(wrapper);
    }
}
