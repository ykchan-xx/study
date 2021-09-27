package com.aisino.frems.modules.hdl_qyxx.controller;

import com.aisino.frems.common.model.LoginUser;
import com.aisino.frems.common.util.DateUtils;
import com.aisino.frems.modules.hdl_qyxx.entity.HdlQyfj;
import com.aisino.frems.modules.hdl_qyxx.service.IHdlQyfjService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.asframework.base.model.InvokeResult;
import org.asframework.core.util.CollectionUtil;
import org.asframework.data.mybatis.util.MyQueryHelper;
import com.aisino.frems.modules.hdl_qyxx.entity.HdlQyxx;
import com.aisino.frems.modules.hdl_qyxx.service.IHdlQyxxService;
import com.aisino.frems.modules.hdl_qyxx.vo.HdlQyxxPageQuery;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 企业信息Controller控制类
 *
 * @author create by huangdanlei
 * @date 2021-07-23
 */
@Slf4j
@Api(tags = "企业信息")
@RestController
@RequestMapping("qygl/hdl_qyxx")
public class HdlQyxxController {
    @Resource
    private IHdlQyxxService hdlQyxxService;
    @Resource
    private IHdlQyfjService hdlQyfjService;

    @ApiOperation(value = "企业信息-保存", notes = "企业信息-保存")
    @PostMapping("save")
    public InvokeResult save(@RequestBody JSONObject jsonObject) {
        try {
            String qysj = jsonObject.getString("qysj");
            String qyxq = jsonObject.getString("qyxq");
            String qydz = jsonObject.getString("qydz");
            String qylb = jsonObject.getString("qylb");
            String qymc = jsonObject.getString("qymc");
            String qybm = jsonObject.getString("qybm");
            String zczj = jsonObject.getString("zczj");
            String zcdz = jsonObject.getString("zcdz");
            String zsdz = jsonObject.getString("zsdz");
            String fr = jsonObject.getString("fr");
            String lxr = jsonObject.getString("lxr");
            String lxdh = jsonObject.getString("lxdh");
            String qyf = jsonObject.getString("qyf");
            String qy_starttime = jsonObject.getString("qy_starttime");
            String qy_endtime = jsonObject.getString("qy_endtime");
            String qyrs = jsonObject.getString("qyrs");
            JSONArray jsonArray = jsonObject.getJSONArray("fileList");
            List<Map<String, Object>> fileList = new ArrayList<>();
            if (jsonArray != null && jsonArray.size() > 0) {
                for (Object o : jsonArray) {
                    JSONObject imgObj = (JSONObject) o;
                    Map<String, Object> imgMap = new HashMap<>();
                    imgMap.put("uid", imgObj.getString("uid"));
                    imgMap.put("name", imgObj.getString("name"));
                    imgMap.put("status", imgObj.getString("status"));
                    imgMap.put("url", imgObj.getString("thumbUrl"));
                    fileList.add(imgMap);
                }
            }
            HdlQyxx entity = new HdlQyxx();
            entity.setQysj(qysj);
            entity.setQyxq(qyxq);
            entity.setQydz(qydz);
            entity.setQylb(qylb);
            entity.setQymc(qymc);
            entity.setQybm(qybm);
            entity.setZczj(zczj);
            entity.setZcdz(zcdz);
            entity.setZsdz(zsdz);
            entity.setFr(fr);
            entity.setLxr(lxr);
            entity.setLxdh(lxdh);
            entity.setQyf(qyf);
            entity.setQyStarttime(qy_starttime);
            entity.setQyEndtime(qy_endtime);
            entity.setQyrs(qyrs);

            if (StringUtils.isNotEmpty(entity.getQyStarttime()) && entity.getQyStarttime().length() > 19) {
                entity.setQyStarttime(entity.getQyStarttime().substring(0, 19).replace("T", " "));
            }
            if (StringUtils.isNotEmpty(entity.getQyEndtime()) && entity.getQyEndtime().length() > 19) {
                entity.setQyEndtime(entity.getQyEndtime().substring(0, 19).replace("T", " "));
            }
            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            entity.setLrr(user.getUsername());
            entity.setLrsj(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat));

            boolean success = hdlQyxxService.save(entity);

            if (success) {
                return InvokeResult.success("保存成功", entity);
            } else {
                return InvokeResult.failure("保存失败");
            }
        } catch (Exception e) {
            log.error("保存企业协议信息失败", e);
            return InvokeResult.failure("保存企业协议信息失败");
        }
    }

    @ApiOperation(value = "企业协议管理-获取附件", notes = "企业协议管理-获取附件")
    @GetMapping("getQyfj")
    public InvokeResult getQyfj(@RequestParam(required = false) String qybm) {
        try {
            BaseMapper<HdlQyfj> baseMapper =hdlQyfjService.getBaseMapper();
            QueryWrapper<HdlQyfj> wrapper = new QueryWrapper<>();
            wrapper.eq("qybm", qybm);
            HdlQyfj hdlQyfj = baseMapper.selectOne(wrapper);
            String xysnr= hdlQyfj.getXysnr();
            List<Map<String, Object>> fileList = JSON.parseObject(xysnr, List.class);
            return InvokeResult.success("获取协议附件成功！", fileList);
        } catch (Exception e) {
            return InvokeResult.failure("获取协议附件失败");
        }
    }

    @ApiOperation(value = "企业信息-更新", notes = "企业信息-更新")
    @PutMapping("update")
    public InvokeResult update(@RequestBody JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("id");
            String qysj = jsonObject.getString("qysj");
            String qyxq = jsonObject.getString("qyxq");
            String qydz = jsonObject.getString("qydz");
            String qylb = jsonObject.getString("qylb");
            String qymc = jsonObject.getString("qymc");
            String qybm = jsonObject.getString("qybm");
            String zczj = jsonObject.getString("zczj");
            String zcdz = jsonObject.getString("zcdz");
            String zsdz = jsonObject.getString("zsdz");
            String fr = jsonObject.getString("fr");
            String lxr = jsonObject.getString("lxr");
            String lxdh = jsonObject.getString("lxdh");
            String qyf = jsonObject.getString("qyf");
            String qy_starttime = jsonObject.getString("qy_starttime");
            String qy_endtime = jsonObject.getString("qy_endtime");
            String qyrs = jsonObject.getString("qyrs");
            JSONArray jsonArray = jsonObject.getJSONArray("fileList");
            List<Map<String, Object>> fileList = new ArrayList<>();
            if (jsonArray != null && jsonArray.size() > 0) {
                for (Object o : jsonArray) {
                    JSONObject imgObj = (JSONObject) o;
                    Map<String, Object> imgMap = new HashMap<>();
                    imgMap.put("uid", imgObj.getString("uid"));
                    imgMap.put("name", imgObj.getString("name"));
                    imgMap.put("status", imgObj.getString("status"));
                    String imgUrl = "";
                    if (StringUtils.isNotBlank(imgObj.getString("thumbUrl"))) {
                        imgMap.put("url", imgObj.getString("thumbUrl"));
                    }
                    if (StringUtils.isNotBlank(imgObj.getString("url"))) {
                        imgMap.put("url", imgObj.getString("url"));
                    }
                    fileList.add(imgMap);
                }
            }
            HdlQyxx qyxx1 = new HdlQyxx();
            qyxx1.setId(Long.valueOf(id));
            qyxx1.setQysj(qysj);
            qyxx1.setQyxq(qyxq);
            qyxx1.setQydz(qydz);
            qyxx1.setQylb(qylb);
            qyxx1.setQymc(qymc);
            qyxx1.setQybm(qybm);
            qyxx1.setZczj(zczj);
            qyxx1.setZcdz(zcdz);
            qyxx1.setZsdz(zsdz);
            qyxx1.setFr(fr);
            qyxx1.setLxr(lxr);
            qyxx1.setLxdh(lxdh);
            qyxx1.setQyf(qyf);
            qyxx1.setQyStarttime(qy_starttime);
            qyxx1.setQyEndtime(qy_endtime);
            qyxx1.setQyrs(qyrs);

            if (StringUtils.isNotEmpty(qyxx1.getQyStarttime()) && qyxx1.getQyStarttime().length() > 19) {
                qyxx1.setQyStarttime(qyxx1.getQyStarttime().substring(0, 19).replace("T", " "));
            }
            if (StringUtils.isNotEmpty(qyxx1.getQyEndtime()) && qyxx1.getQyEndtime().length() > 19) {
                qyxx1.setQyEndtime(qyxx1.getQyEndtime().substring(0, 19).replace("T", " "));
            }
            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            qyxx1.setZhxgr(user.getUsername());
            qyxx1.setZhxgsj(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat));
            try {
                UpdateWrapper<HdlQyfj> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("qybm", Long.valueOf(qybm)).set("xysnr", JSON.toJSONString(fileList));
                boolean flag = hdlQyfjService.update(null, updateWrapper);
                if (flag) {
                    return InvokeResult.success("更新协议附件成功！");
                } else {
                    return InvokeResult.failure("更新协议附件失败！");
                }
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
            }

            boolean success = hdlQyxxService.updateById(qyxx1);
            if (success) {
                return InvokeResult.success("更新成功");
            } else {
                return InvokeResult.failure("更新失败");
            }
        } catch (Exception e) {
            log.error("更新企业协议信息失败", e);
            return InvokeResult.failure("更新企业协议信息失败");
        }

    }


    @ApiOperation(value = "企业信息-获取详情", notes = "企业信息-获取详情")
    @GetMapping("getById")
    public InvokeResult getById(@RequestParam String id) {
        HdlQyxx hdlQyxx = hdlQyxxService.getById(id);
        return InvokeResult.success(null, hdlQyxx);
    }

    @ApiOperation(value = "企业信息-分页查询列表", notes = "企业信息-分页查询列表")
    @GetMapping("listPage")
    public InvokeResult listPage(@ModelAttribute HdlQyxxPageQuery pageQuery) {
        IPage<HdlQyxx> page = MyQueryHelper.createPage(pageQuery, HdlQyxx.class);
        QueryWrapper<HdlQyxx> queryWrapper = MyQueryHelper.createQueryWrapper(pageQuery, HdlQyxx.class);
        IPage<HdlQyxx> pageList = hdlQyxxService.page(page, queryWrapper);
        return InvokeResult.success(null, pageList);
    }

    @ApiOperation(value = "企业信息-删除", notes = "企业信息-删除")
    @DeleteMapping("deleteById")
    public InvokeResult deleteById(@RequestParam String id) {
        try {
            boolean success = hdlQyxxService.removeById(id);
            if (success) {
                return InvokeResult.success("删除成功", id);
            } else {
                return InvokeResult.failure("删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("删除企业信息失败", e);
            return InvokeResult.failure("删除企业信息失败");
        }
    }

    @ApiOperation(value = "企业信息-批量删除", notes = "企业信息-批量删除")
    @DeleteMapping("deleteBatchByIds")
    public InvokeResult deleteBatchByIds(@RequestParam String ids) {
        try {
            List<String> idList = CollectionUtil.string2List(ids, ",");
            boolean success = hdlQyxxService.removeByIds(idList);
            if (success) {
                return InvokeResult.success("批量删除成功", idList);
            } else {
                return InvokeResult.failure("批量删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("批量删除企业信息失败", e);
            return InvokeResult.failure("批量删除企业信息失败");
        }
    }

    @ApiOperation(value = "企业信息-获取企业数据字典信息", notes = "企业信息-获取企业数据字典信息")
    @GetMapping("queryDictInfo")
    public InvokeResult queryDictInfo() {
        try {
            List<Map> resList = hdlQyxxService.queryDictInfo();
            return InvokeResult.success("获取企业数据字典信息成功", resList);
        } catch (Exception e) {
            log.error("批量删除企业信息失败", e);
            return InvokeResult.failure("获取企业数据字典信息失败");
        }

    }
}
