package com.aisino.frems.modules.yjxx.service;

import com.aisino.frems.modules.ryxx.entity.Ryxx;
import com.aisino.frems.modules.yjxx.dto.QyYjDto;
import com.aisino.frems.modules.yjxx.entity.QyYjxxb;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 预警信息 Service服务类
 *
 * @author create by luozheng
 * @date 2021-07-02
 */
public interface IQyYjxxbService extends IService<QyYjxxb> {

    List<QyYjxxb> findByYzzg(String ywxm, String zjzl, String zjhm, String zjhm1);

    /**
     * 判断该企业人员是否被预警
     * @param yjDto
     * @return: java.lang.Boolean
     * @author yangshunfei
     * @date 2021/7/8 15:15
     */
    List<QyYjxxb> isWarned(QyYjDto yjDto,String yjlx);
}
