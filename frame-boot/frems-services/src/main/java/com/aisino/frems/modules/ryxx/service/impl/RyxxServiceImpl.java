package com.aisino.frems.modules.ryxx.service.impl;

import com.aisino.frems.common.ws.EecmiswsCaller;
import com.aisino.frems.common.ws.dto.WsResultGabCrjxx;
import com.aisino.frems.common.ws.dto.WsResultGabRyLzxx;
import com.aisino.frems.common.ws.param.WsQueryParamGab;
import com.aisino.frems.modules.ryxx.dao.RyxxDao;
import com.aisino.frems.modules.ryxx.entity.Ryxx;
import com.aisino.frems.modules.ryxx.service.IRyxxService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.asframework.common.util.JsonUtil;
import org.asframework.core.util.LangUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 企业人员管理 ServiceImpl服务实现类
 *
 * @author create by yangshunfei
 * @date 2021-06-22
 */
@Service("ryxxService")
@Slf4j
public class RyxxServiceImpl extends ServiceImpl<RyxxDao, Ryxx> implements IRyxxService {

    @Resource
    private RyxxDao ryxxDao;

    @Override
    public List<Map> getRyxxMonthCount() {
        return ryxxDao.getRyxxMonthCount();
    }

    /**
     * 查询该注册人员是否曾被注册
     * @param entity
     * @return Ryxx
     * @date 2021/7/3 11:18
     */
    @Override
    public Ryxx queryRegisteredQyRyxx(Ryxx entity) {
        QueryWrapper<Ryxx> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("GJDQ",entity.getGjdq())
                .eq("YWXM",entity.getYwxm())
                .eq("ZJZL",entity.getZjzl())
                .eq("ZJHM",entity.getZjhm())
                .eq("CSRQ",entity.getCsrq());
        return ryxxDao.selectOne(queryWrapper);
    }

    @Override
    public WsResultGabRyLzxx queryInfoByGABLZXX(Ryxx ryxx) throws Exception {
        // 若证件种类，证件号码条件查询无数据，则调用人员姓名，出生日期查询数据
        WsResultGabRyLzxx wsResultGabRyLzxx = this.queryInfoByGABLZXX(ryxx.getZjzl(), ryxx.getZjhm(), ryxx.getGjdq(), "", "");
        if (null == wsResultGabRyLzxx) {
            wsResultGabRyLzxx = this.queryInfoByGABLZXX("", "", ryxx.getGjdq(), ryxx.getYwxm(), ryxx.getCsrq());
        }
        return wsResultGabRyLzxx;
    }

    /**
     * 调用部接口查询出入境记录信息
     * @param zjzl
     * @param zjhm
     * @param gjdq
     * @param ywxm
     * @param csrq
     * @return WsResultGabRyLzxx
     * @author yangshunfei
     * @date 2021/7/8 17:34
     */
    @Override
    public WsResultGabRyLzxx queryInfoByGABLZXX(String zjzl,String zjhm,String gjdq,String ywxm,String csrq ) throws Exception {

        WsQueryParamGab param = new WsQueryParamGab();
        if(StringUtils.isNotEmpty(zjzl) && StringUtils.isNotEmpty(zjzl) && StringUtils.isNotEmpty(gjdq) ) {
            // 证件种类 .
            param.setPaperType(zjzl);
            // 证件号码 .
            param.setPaperNO(zjzl);
            // 国籍.该参数实测发现无效
            param.setNationality(gjdq);
        } else if (LangUtil.notEmpty(gjdq)) {
            param.setNameEN(ywxm);
            param.setBirthday(csrq);
            // 国籍.该参数实测发现无效
            param.setNationality(gjdq);
        }
        String args = JsonUtil.toJson(param);
        WsResultGabRyLzxx wsResult = (WsResultGabRyLzxx) EecmiswsCaller.callUnimethod(args, "qg_jw_queryStay",
                WsResultGabRyLzxx.class);
        if (null!=wsResult && wsResult.getSuccess() == 1){
            return wsResult;
        }
        return null;
    }

    /**
     * 部接口获取出入境记录
     * @return WsResultGabCrjxx
     * @author yangshunfei
     * @date 2021/7/8 10:49
     */
    @Override
    public WsResultGabCrjxx queryInfoByGabcrjxx(String startDate,String endDate,Ryxx ryxx){
        // 若人员类别，时间区间，姓名，出生日期，性别条件查询无数据，则调用时间区间、人员类别、证件号码查询数据
        String birth = ryxx.getCsrq().replace("-", "");
        WsResultGabCrjxx wsResultGabCrjxx = this.queryInfoByGabcrjxx(startDate, endDate, ryxx.getYwxm(), birth,
                ryxx.getSex(), "");
        if (wsResultGabCrjxx==null){
            wsResultGabCrjxx = this.queryInfoByGabcrjxx(startDate, endDate,"","","",ryxx.getZjhm());
        }
        return wsResultGabCrjxx;
    }

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
    @Override
    public WsResultGabCrjxx queryInfoByGabcrjxx(String startDate, String endDate, String name, String birth,
                                                String gender, String zjhm) {
        WsQueryParamGab param = new WsQueryParamGab();
        param.setStartDate(startDate);
        param.setEndDate(endDate);
        param.setPersonType("9");
        param.setYwxm(name);
        param.setBirth(birth);
        param.setGender(gender);
        param.setZjhm(zjhm);

        String args = JsonUtil.toJson(param);
        WsResultGabCrjxx wsResult = null;
        try {

            wsResult = (WsResultGabCrjxx) EecmiswsCaller.callUnimethod(args, "qg_queryCrjRecord",
                   WsResultGabCrjxx.class);
            return wsResult;
        } catch (Exception e) {
            log.error("调用部接口查询涉外企业境外人员出入境记录失败");
        }
        return null;

    }
}
