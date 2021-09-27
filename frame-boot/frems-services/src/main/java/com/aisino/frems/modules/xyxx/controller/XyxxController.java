package com.aisino.frems.modules.xyxx.controller;

import com.aisino.frems.common.model.LoginUser;
import com.aisino.frems.common.util.DateUtils;
import com.aisino.frems.modules.xyxx.entity.Xyfjxx;
import com.aisino.frems.modules.xyxx.entity.Xyxx;
import com.aisino.frems.modules.xyxx.service.IXyfjxxService;
import com.aisino.frems.modules.xyxx.service.IXyxxService;
import com.aisino.frems.modules.xyxx.vo.XyxxPageQuery;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 企业协议管理Controller控制类
 *
 * @author create by yangshunfei
 * @date 2021-06-22
 */
@Slf4j
@Api(tags = "企业协议管理")
@RestController
@RequestMapping("qygl/xyxx")
public class XyxxController {
    @Resource
    private IXyxxService xyxxService;
    @Resource
    private IXyfjxxService xyfjxxService;
    @ApiOperation(value = "企业协议管理-保存", notes = "企业协议管理-保存")
    @PostMapping("save")
    public InvokeResult save(@RequestBody JSONObject jsonObject) {
        try {
            String xyldry = jsonObject.getString("xyldry");
            String xykssj = jsonObject.getString("xykssj");
            String xyjssj = jsonObject.getString("xyjssj");
            String qybm = jsonObject.getString("qybm");
            String qyffzr = jsonObject.getString("qyffzr");
            String ldlxfs = jsonObject.getString("ldlxfs");
            String qygdrs = jsonObject.getString("qygdrs");
            String xybh = jsonObject.getString("xybh");
            JSONArray jsonArray = jsonObject.getJSONArray("fileList");
            List<Map<String,Object>> fileList = new ArrayList<>();
            if (jsonArray != null && jsonArray.size()>0) {
                for (Object o : jsonArray) {
                    JSONObject imgObj = (JSONObject)o;
                    Map<String,Object> imgMap =new HashMap<>();
                    imgMap.put("uid",imgObj.getString("uid"));
                    imgMap.put("name",imgObj.getString("name"));
                    imgMap.put("status",imgObj.getString("status"));
                    imgMap.put("url",imgObj.getString("thumbUrl"));
                    fileList.add(imgMap);
                }
            }

            try {
                Xyfjxx xyfjxx = new Xyfjxx();
                if (StringUtils.isNotBlank(xybh)) {
                    xyfjxx.setXybh(xybh);
                }
                String xyms = JSON.toJSONString(fileList);
                xyfjxx.setXyms(xyms);
                xyfjxxService.save(xyfjxx);
            } catch (Exception e) {
                return InvokeResult.failure("协议附件保存失败");
            }

            Xyxx entity = new Xyxx();
            entity.setXyldry(xyldry);
            entity.setXykssj(xykssj);
            entity.setXyjssj(xyjssj);
            entity.setQybm(qybm);
            entity.setQyffzr(qyffzr);
            entity.setLdlxfs(ldlxfs);
            entity.setXybh(xybh);
            entity.setQygdrs(qygdrs);

            if (StringUtils.isNotEmpty(entity.getXykssj()) && entity.getXykssj().length() > 19) {
                entity.setXykssj(entity.getXykssj().substring(0, 19).replace("T"," "));
            }
            if (StringUtils.isNotEmpty(entity.getXyjssj()) && entity.getXyjssj().length() > 19) {
                entity.setXyjssj(entity.getXyjssj().substring(0, 19).replace("T"," "));
            }
            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            entity.setLrr(user.getUsername());
            entity.setLrsj(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat));
            boolean success = xyxxService.save(entity);

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
    @GetMapping("getxyfj")
    public InvokeResult getXyfj(@RequestParam(required = false) String xybh){
        try {
            BaseMapper<Xyfjxx> baseMapper = xyfjxxService.getBaseMapper();
            QueryWrapper<Xyfjxx> wrapper = new QueryWrapper<>();
            wrapper.eq("XYBH",xybh);
            Xyfjxx xyfjxx = baseMapper.selectOne(wrapper);
            String xyms = xyfjxx.getXyms();
            List<Map<String,Object>> fileList = JSON.parseObject(xyms,List.class);
            return InvokeResult.success("获取协议附件成功！", fileList);
        } catch (Exception e) {
            return InvokeResult.failure("获取协议附件失败");
        }

    }
    @ApiOperation(value = "企业协议管理-更新", notes = "企业协议管理-更新")
    @PutMapping("update")
    public InvokeResult update(@RequestBody JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("id");
            String xyldry = jsonObject.getString("xyldry");
            String xykssj = jsonObject.getString("xykssj");
            String xyjssj = jsonObject.getString("xyjssj");
            String qybm = jsonObject.getString("qybm");
            String qyffzr = jsonObject.getString("qyffzr");
            String ldlxfs = jsonObject.getString("ldlxfs");
            String qygdrs = jsonObject.getString("qygdrs");
            String xybh = jsonObject.getString("xybh");
            JSONArray jsonArray = jsonObject.getJSONArray("fileList");
            List<Map<String,Object>> fileList = new ArrayList<>();
            if (jsonArray != null && jsonArray.size()>0) {
                for (Object o : jsonArray) {
                    JSONObject imgObj = (JSONObject)o;
                    Map<String,Object> imgMap =new HashMap<>();
                    imgMap.put("uid",imgObj.getString("uid"));
                    imgMap.put("name",imgObj.getString("name"));
                    imgMap.put("status",imgObj.getString("status"));
                    String imgUrl = "";
                    if (StringUtils.isNotBlank(imgObj.getString("thumbUrl"))) {
                        imgMap.put("url",imgObj.getString("thumbUrl"));
                    }
                    if (StringUtils.isNotBlank(imgObj.getString("url"))) {
                        imgMap.put("url",imgObj.getString("url"));
                    }
                    fileList.add(imgMap);
                }
            }

            Xyxx xyxx = new Xyxx();
            xyxx.setId(Long.valueOf(id));
            xyxx.setXyldry(xyldry);
            xyxx.setXykssj(xykssj);
            xyxx.setXyjssj(xyjssj);
            xyxx.setQybm(qybm);
            xyxx.setQyffzr(qyffzr);
            xyxx.setLdlxfs(ldlxfs);
            xyxx.setXybh(xybh);
            xyxx.setQygdrs(qygdrs);
            if (StringUtils.isNotEmpty(xyxx.getXykssj()) && xyxx.getXykssj().length() > 19) {
                xyxx.setXykssj(xyxx.getXykssj().substring(0, 19).replace("T"," "));
            }
            if (StringUtils.isNotEmpty(xyxx.getXyjssj()) && xyxx.getXyjssj().length() > 19) {
                xyxx.setXyjssj(xyxx.getXyjssj().substring(0, 19).replace("T"," "));
            }
            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            xyxx.setZhxgr(user.getUsername());
            xyxx.setZhxgsj(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat));

            try {
                UpdateWrapper<Xyfjxx> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("xybh",Long.valueOf(xybh)).set("xyms",JSON.toJSONString(fileList));
                boolean flag = xyfjxxService.update(null, updateWrapper);
                if (flag){
                    return InvokeResult.success("更新协议附件成功！");
                }else{
                    return InvokeResult.failure("更新协议附件失败！");
                }
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
            }

            boolean success = xyxxService.updateById(xyxx);
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

    @ApiOperation(value = "企业协议管理-获取详情", notes = "企业协议管理-获取详情")
    @GetMapping("getById")
    public InvokeResult getById(@RequestParam String id) {
        Xyxx xyxx = xyxxService.getById(id);
        return InvokeResult.success(null, xyxx);
    }

    @ApiOperation(value = "企业协议管理-分页查询列表", notes = "企业协议管理-分页查询列表")
    @GetMapping("listPage")
    public InvokeResult listPage(@ModelAttribute XyxxPageQuery pageQuery) {
        IPage<Xyxx> page = MyQueryHelper.createPage(pageQuery, Xyxx.class);
        QueryWrapper<Xyxx> queryWrapper = MyQueryHelper.createQueryWrapper(pageQuery, Xyxx.class);
        IPage<Xyxx> pageList = xyxxService.page(page, queryWrapper);
        return InvokeResult.success(null, pageList);
    }

    @ApiOperation(value = "企业协议管理-删除", notes = "企业协议管理-删除")
    @DeleteMapping("deleteById")
    public InvokeResult deleteById(@RequestParam String id) {
        try {
            boolean success = xyxxService.removeById(id);
            if (success) {
                return InvokeResult.success("删除成功", id);
            } else {
                return InvokeResult.failure("删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("删除企业协议信息失败", e);
            return InvokeResult.failure("删除企业协议信息失败");
        }
    }

    @ApiOperation(value = "企业协议管理-批量删除", notes = "企业协议管理-批量删除")
    @DeleteMapping("deleteBatchByIds")
    public InvokeResult deleteBatchByIds(@RequestParam String ids) {
        try {
            List<String> idList = CollectionUtil.string2List(ids, ",");
            boolean success = xyxxService.removeByIds(idList);
            if (success) {
                return InvokeResult.success("批量删除成功", idList);
            } else {
                return InvokeResult.failure("批量删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("批量删除企业协议信息失败", e);
            return InvokeResult.failure("批量删除企业协议信息失败");
        }
    }
}
