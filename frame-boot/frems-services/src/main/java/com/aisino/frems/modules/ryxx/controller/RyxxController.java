package com.aisino.frems.modules.ryxx.controller;

import com.aisino.frems.common.model.LoginUser;
import com.aisino.frems.common.util.DateUtils;
import com.aisino.frems.modules.qygl.entity.Archiveinfo;
import com.aisino.frems.modules.qygl.service.IArchiveinfoService;
import com.aisino.frems.modules.ryxx.entity.Ryfjxx;
import com.aisino.frems.modules.ryxx.entity.Ryxx;
import com.aisino.frems.modules.ryxx.service.IRyfjxxService;
import com.aisino.frems.modules.ryxx.service.IRyxxService;
import com.aisino.frems.modules.ryxx.vo.RyxxPageQuery;
import com.aisino.frems.modules.system.service.ISysDictService;
import com.aisino.frems.modules.xyxx.entity.Xyxx;
import com.aisino.frems.modules.xyxx.service.IXyxxService;
import com.aisino.frems.modules.yjxx.entity.QyYjxxb;
import com.aisino.frems.modules.yjxx.service.IQyYjxxbService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.asframework.base.model.InvokeResult;
import org.asframework.core.util.CollectionUtil;
import org.asframework.data.mybatis.util.MyQueryHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 企业人员管理Controller控制类
 *
 * @author create by yangshunfei
 * @date 2021-06-22
 */
@Slf4j
@Api(tags = "企业人员管理")
@RestController
@RequestMapping("qygl/ryxx")
public class RyxxController {
    @Resource
    private IRyxxService ryxxService;
    @Resource
    private IRyfjxxService ryfjxxService;
    @Resource
    private ISysDictService dictService;
    @Resource
    private IQyYjxxbService qyYjxxbService;
    @Resource
    private IArchiveinfoService archiveinfoService;
    @Resource
    private IXyxxService xyxxService;
    @ApiOperation(value = "企业人员管理-保存", notes = "企业人员管理-保存")
    @PostMapping("save")
    public InvokeResult save(@RequestBody Ryxx entity) {
        Boolean flag1 = personnelWarning(entity);
        if (flag1){
            return InvokeResult.failure("该人员已被注册!");
        }
        Boolean flag2 = archiveinfoWarning(entity);
        if (flag2) {
            return InvokeResult.failure("该人员所属公司注册人数超过协议规定人数！");
        }

        try {
            String avatar = "";
            if (StringUtils.isNotBlank(entity.getTx())) {
                avatar = entity.getTx();
                entity.setTx(null);
            }
            if (StringUtils.isNotEmpty(entity.getSjrjsj()) && entity.getSjrjsj().length() > 19) {
                entity.setSjrjsj(entity.getSjrjsj().substring(0, 19).replace("T"," "));
            }
            if (StringUtils.isNotEmpty(entity.getZsdjsj()) && entity.getZsdjsj().length() > 19) {
                entity.setZsdjsj(entity.getZsdjsj().substring(0, 19).replace("T"," "));
            }
            if (StringUtils.isNotEmpty(entity.getJhcjsj()) && entity.getJhcjsj().length() > 19) {
                entity.setJhcjsj(entity.getJhcjsj().substring(0, 19).replace("T"," "));
            }
            if (StringUtils.isNotEmpty(entity.getJhrjsj()) && entity.getJhrjsj().length() > 19) {
                entity.setJhrjsj(entity.getJhrjsj().substring(0, 19).replace("T"," "));
            }
            if (StringUtils.isNotEmpty(entity.getQsrq()) && entity.getQsrq().length() > 10) {
                entity.setQsrq(entity.getQsrq().substring(0, 10));
            }
            if (StringUtils.isNotEmpty(entity.getJsrq()) && entity.getJsrq().length() > 10) {
                entity.setJsrq(entity.getJsrq().substring(0, 10));
            }
            if (StringUtils.isNotEmpty(entity.getCsrq()) && entity.getCsrq().length() > 10) {
                entity.setCsrq(entity.getCsrq().substring(0, 10));
            }
            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            entity.setLrr(user.getUsername());
            entity.setLrsj(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat));
            boolean success = ryxxService.save(entity);
            try {

                    Ryfjxx ryfjxx = new Ryfjxx();
                    ryfjxx.setQyRyid(entity.getId());
                    ryfjxx.setAvatar(avatar);
                    ryfjxxService.save(ryfjxx);

            } catch (Exception e) {
                return InvokeResult.failure("图片保存失败");
            }

            if (success) {
                return InvokeResult.success("保存成功", entity);
            } else {
                return InvokeResult.failure("保存失败");
            }
        } catch (Exception e) {
            log.error("保存企业人员管理失败", e);
            return InvokeResult.failure("保存企业人员管理失败");
        }
    }

    @ApiOperation(value = "企业人员管理-更新", notes = "企业人员管理-更新")
    @PutMapping("update")
    public InvokeResult update(@RequestBody Ryxx entity) {
        try {
            if (StringUtils.isNotEmpty(entity.getSjrjsj()) && entity.getSjrjsj().length() > 19) {
                entity.setSjrjsj(entity.getSjrjsj().substring(0, 19).replace("T"," "));
            }
            if (StringUtils.isNotEmpty(entity.getZsdjsj()) && entity.getZsdjsj().length() > 19) {
                entity.setZsdjsj(entity.getZsdjsj().substring(0, 19).replace("T"," "));
            }
            if (StringUtils.isNotEmpty(entity.getJhcjsj()) && entity.getJhcjsj().length() > 19) {
                entity.setJhcjsj(entity.getJhcjsj().substring(0, 19).replace("T"," "));
            }
            if (StringUtils.isNotEmpty(entity.getJhrjsj()) && entity.getJhrjsj().length() > 19) {
                entity.setJhrjsj(entity.getJhrjsj().substring(0, 19).replace("T"," "));
            }
            if (StringUtils.isNotEmpty(entity.getQsrq()) && entity.getQsrq().length() > 10) {
                entity.setQsrq(entity.getQsrq().substring(0, 10));
            }
            if (StringUtils.isNotEmpty(entity.getJsrq()) && entity.getJsrq().length() > 10) {
                entity.setJsrq(entity.getJsrq().substring(0, 10));
            }
            if (StringUtils.isNotEmpty(entity.getCsrq()) && entity.getCsrq().length() > 10) {
                entity.setCsrq(entity.getCsrq().substring(0, 10));
            }
            if (StringUtils.isNotEmpty(entity.getGjdq())){
                String regex1 = "[\\u4e00-\\u9fa5]";
                String regex2 = "^[0-9]*[1-9][0-9]*$";
                Pattern p1 = Pattern.compile(regex1);
                Pattern p2 = Pattern.compile(regex2);
                Matcher m1 = p1.matcher(entity.getGjdq());
                Matcher m2 = p2.matcher(entity.getGjdq());
                if (m1.find()||m2.find()){
                    entity.setGjdq(dictService.queryItemCodeByItemText("gjdq",entity.getGjdq()));
                }else{
                    entity.setGjdq(entity.getGjdq());
                }

            }

            if (StringUtils.isNotEmpty(entity.getSex())){

                String regex = "[\\u4e00-\\u9fa5]";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(entity.getSex());

                if (m.find())
                    entity.setSex(dictService.queryItemCodeByItemText("sex",entity.getSex()));


            }

            if (StringUtils.isNotBlank(entity.getZjzl())) {
                String regex = "[\\u4e00-\\u9fa5]";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(entity.getZjzl());
                if (m.find()){
                    entity.setZjzl(dictService.queryItemCodeByItemText("zjzl",entity.getZjzl()));
                }else{
                    entity.setZjzl(entity.getZjzl());
                }
            }
            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            entity.setZhxgr(user.getUsername());
            entity.setZhxgsj(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat));
            boolean success = ryxxService.updateById(entity);
            if (success) {
                return InvokeResult.success("更新成功");
            } else {
                return InvokeResult.failure("更新失败");
            }
        } catch (Exception e) {
            log.error("更新企业人员管理失败", e);
            return InvokeResult.failure("更新企业人员管理失败");
        }
    }

    @ApiOperation(value = "企业人员管理-获取详情", notes = "企业人员管理-获取详情")
    @GetMapping("getById")
    public InvokeResult getById(@RequestParam String id) {
        Ryxx ryxx = ryxxService.getById(id);
        return InvokeResult.success(null, ryxx);
    }

    @ApiOperation(value = "企业人员管理-分页查询列表", notes = "企业人员管理-分页查询列表")
    @GetMapping("listPage")
    public InvokeResult listPage(@ModelAttribute RyxxPageQuery pageQuery) {
        IPage<Ryxx> page = MyQueryHelper.createPage(pageQuery, Ryxx.class);
        QueryWrapper<Ryxx> queryWrapper = MyQueryHelper.createQueryWrapper(pageQuery, Ryxx.class);
        IPage<Ryxx> pageList = ryxxService.page(page, queryWrapper);
        if (pageList.getRecords() != null && pageList.getRecords().size()>0) {
            pageList.getRecords().stream().forEach(item ->{
                item.setSex(dictService.queryDictTextByKey("sex",item.getSex()));
                item.setGjdq(dictService.queryDictTextByKey("gjdq",item.getGjdq()));
                item.setZjzl(dictService.queryDictTextByKey("zjzl",item.getZjzl()));
            });
        }
        return InvokeResult.success(null, pageList);
    }

    @ApiOperation(value = "企业人员管理-删除", notes = "企业人员管理-删除")
    @DeleteMapping("deleteById")
    public InvokeResult deleteById(@RequestParam String id) {
        try {
            boolean success = ryxxService.removeById(id);
            if (success) {
                return InvokeResult.success("删除成功", id);
            } else {
                return InvokeResult.failure("删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("删除企业人员管理失败", e);
            return InvokeResult.failure("删除企业人员管理失败");
        }
    }

    @ApiOperation(value = "企业人员管理-批量删除", notes = "企业人员管理-批量删除")
    @DeleteMapping("deleteBatchByIds")
    public InvokeResult deleteBatchByIds(@RequestParam String ids) {
        try {
            List<String> idList = CollectionUtil.string2List(ids, ",");
            boolean success = ryxxService.removeByIds(idList);
            if (success) {
                return InvokeResult.success("批量删除成功", idList);
            } else {
                return InvokeResult.failure("批量删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("批量删除企业人员管理失败", e);
            return InvokeResult.failure("批量删除企业人员管理失败");
        }
    }
    /**
     * 人员重复报警
     * @param entity
     * @date 2021/7/2 16:37
     */
    private Boolean personnelWarning(Ryxx entity){
        //人员重复比对项，英文姓名、国家地区、证件种类、证件号码
        if (StringUtils.isNotEmpty(entity.getCsrq()) && entity.getCsrq().length() > 10) {
            entity.setCsrq(entity.getCsrq().substring(0, 10));
        }

        Ryxx ryxx = ryxxService.queryRegisteredQyRyxx(entity);
        if (ryxx !=null){
            //人员重复
            QyYjxxb qyYjxxb = new QyYjxxb();
            qyYjxxb.setQybh(entity.getSsqybm());
            QueryWrapper<Archiveinfo> queryWrapper = new QueryWrapper<Archiveinfo>();
            if (StringUtils.isNotBlank(entity.getSsqybm())) {
                queryWrapper.eq("QYBM",entity.getSsqybm());
                Archiveinfo archiveinfo = archiveinfoService.getOne(queryWrapper);
                qyYjxxb.setQymc(archiveinfo.getQymc());
            }

            qyYjxxb.setYwxm(entity.getYwxm());
            qyYjxxb.setSex(entity.getSex());
            qyYjxxb.setCsrq(entity.getCsrq());
            qyYjxxb.setGjdq(entity.getGjdq());
            qyYjxxb.setZjzl(entity.getZjzl());
            qyYjxxb.setZjhm(entity.getZjhm());
            qyYjxxb.setYjlx("02");
            qyYjxxb.setYjyy("该企业人员已被注册");
            qyYjxxb.setYjzt("0");
            qyYjxxb.setClyj("");
            qyYjxxb.setCjsj(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat));
            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            qyYjxxb.setCjr(user.getUsername());
            qyYjxxbService.save(qyYjxxb);
            return true;
        }

        return false;
    }

    /**
     * 企业超员预警
     * @return:
     * @date 2021/7/3 1:12
     */
    private Boolean archiveinfoWarning(Ryxx entity){
        if (StringUtils.isNotEmpty(entity.getCsrq()) && entity.getCsrq().length() > 10) {
            entity.setCsrq(entity.getCsrq().substring(0, 10));
        }
        QueryWrapper<Archiveinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("QYBM",entity.getSsqybm());
        Archiveinfo archiveinfo = archiveinfoService.getOne(queryWrapper);
        int totalNum = 0;
        if (archiveinfo != null) {
            totalNum = Integer.parseInt(archiveinfo.getQyzrs());
        }
        QueryWrapper<Xyxx> xyxxQueryWrapper = new QueryWrapper<>();
        xyxxQueryWrapper.eq("QYBM",entity.getSsqybm());
        Xyxx xyxx = xyxxService.getOne(xyxxQueryWrapper);
        int xyNum = 0;
        if (xyxx != null) {
            xyNum = Integer.parseInt(xyxx.getQygdrs());
        }
        if (totalNum>=xyNum){
            //企业超员预警
            QyYjxxb qyYjxxb = new QyYjxxb();
            qyYjxxb.setQybh(entity.getSsqybm());
            qyYjxxb.setQymc(archiveinfo.getQymc());
            qyYjxxb.setYwxm(entity.getYwxm());
            qyYjxxb.setSex(entity.getSex());
            qyYjxxb.setCsrq(entity.getCsrq());
            qyYjxxb.setGjdq(entity.getGjdq());
            qyYjxxb.setZjzl(entity.getZjzl());
            qyYjxxb.setZjhm(entity.getZjhm());
            qyYjxxb.setYjlx("03");
            qyYjxxb.setYjyy("企业超员预警");
            qyYjxxb.setYjzt("0");
            qyYjxxb.setClyj("");
            qyYjxxb.setCjsj(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat));
            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            qyYjxxb.setCjr(user.getUsername());
            qyYjxxbService.save(qyYjxxb);
            return true;
        }
        return false;
    }

    @ApiOperation(value = "人员总量增长分析", notes = "人员总量增长分析")
    @GetMapping("getRyxxMonthCount")
    public InvokeResult getRyxxMonthCount() {
        try {
            QueryWrapper<Ryxx> queryWrapper = new QueryWrapper<>();
            //todo 之后补充查询条件：统计今每年的信息量
            List<Map> list = ryxxService.getRyxxMonthCount();
            Map<String, Object> map = new HashMap<>();
            return InvokeResult.success("人员总量增长分析成功", list);
        } catch (Exception e) {
            return InvokeResult.failure("统计人员信息每年总数失败");
        }
    }
}
