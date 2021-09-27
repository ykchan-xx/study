package com.aisino.frems.modules.ryxx.service;

import com.aisino.frems.common.ws.dto.WsResultGabCrjxx;
import com.aisino.frems.common.ws.dto.WsResultGabRyLzxx;
import com.aisino.frems.modules.ryxx.entity.Ryxx;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 企业人员管理 Service服务类
 *
 * @author create by yangshunfei
 * @date 2021-06-22
 */
public interface IRyxxService extends IService<Ryxx> {

    public List<Map> getRyxxMonthCount() ;
    /**
     * 查询该注册人员是否曾被注册
     * @param entity
     * @return Ryxx
     * @date 2021/7/3 11:18
     */
    Ryxx queryRegisteredQyRyxx(Ryxx entity);

    /**
     * 部接口查询获取临住信息
     * @return 临住信息
     */
    WsResultGabRyLzxx queryInfoByGABLZXX(Ryxx ryxx)throws Exception;

    /**
     * 部接口查询获取临住信息
     * @return 临住信息
     */
    WsResultGabRyLzxx queryInfoByGABLZXX(String zjzl,String zjhm,String gjdq,String ywxm,String csrq) throws Exception;

    /**
     *
     * @param startDate
     * @param endDate
     * @param ryxx
     * @return WsResultGabCrjxx
     * @author yangshunfei
     * @date 2021/7/8 10:12
     */
    WsResultGabCrjxx queryInfoByGabcrjxx(String startDate,String endDate, Ryxx ryxx);

    /**
     * 部接口获取出入境记录
     * @param startDate
     * @param endDate
     * @param name
     * @param birth
     * @param gender
     * @param zjhm
     * @return WsResultGabCrjxx
     * @author yangshunfei
     * @date 2021/7/8 10:49
     */
    WsResultGabCrjxx queryInfoByGabcrjxx(String startDate,String endDate,String name,String birth,String gender,String zjhm);


}
