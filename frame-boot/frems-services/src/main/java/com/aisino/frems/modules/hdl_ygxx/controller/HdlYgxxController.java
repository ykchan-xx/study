package com.aisino.frems.modules.hdl_ygxx.controller;

import com.aisino.frems.common.model.LoginUser;
import com.aisino.frems.common.util.DateUtils;
import com.aisino.frems.modules.hdl_qyxx.entity.HdlQyfj;
import com.aisino.frems.modules.hdl_ygxx.entity.HdlYgfj;
import com.aisino.frems.modules.hdl_ygxx.service.IHdlYgfjService;
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
import com.aisino.frems.modules.hdl_ygxx.entity.HdlYgxx;
import com.aisino.frems.modules.hdl_ygxx.service.IHdlYgxxService;
import com.aisino.frems.modules.hdl_ygxx.vo.HdlYgxxPageQuery;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 员工信息Controller控制类
 *
 * @author create by huangdanlei
 * @date 2021-07-23
 */
@Slf4j
@Api(tags = "员工信息")
@RestController
@RequestMapping("qygl/hdl_ygxx")
public class HdlYgxxController {
    @Resource
    private IHdlYgxxService hdlYgxxService;
    private IHdlYgfjService hdlYgfjService;

    @ApiOperation(value = "员工信息-保存", notes = "员工信息-保存")
    @PostMapping("save")
    public InvokeResult save(@RequestBody JSONObject jsonObject) {
        try {
            String ygname = jsonObject.getString("ygname");
            String ygsex = jsonObject.getString("ygsex");
            String ygqy = jsonObject.getString("ygqy");
            String ygbirth = jsonObject.getString("ygbirth");
            String ygsfzid = jsonObject.getString("ygsfzid");
            String ygyx = jsonObject.getString("ygyx");
            String ygqyid = jsonObject.getString("ygqyid");
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

            HdlYgxx entity = new HdlYgxx();
            entity.setYgname(ygname);
            entity.setYgsex(ygsex);
            entity.setYgqy(ygqy);
            entity.setYgbirth(ygbirth);
            entity.setYgsfzid(ygsfzid);
            entity.setYgyx(ygyx);
            entity.setYgqyid(ygqyid);

            if (StringUtils.isNotEmpty(entity.getYgbirth()) && entity.getYgbirth().length() > 19) {
                entity.setYgbirth(entity.getYgbirth().substring(0, 19).replace("T", " "));
            }

            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            entity.setLrr(user.getUsername());
            entity.setLrsj(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat));

            boolean success = hdlYgxxService.save(entity);
            if (success) {
                return InvokeResult.success("保存成功", entity);
            } else {
                return InvokeResult.failure("保存失败");
            }

        } catch (Exception e) {
            log.error("保存员工信息失败", e);
            return InvokeResult.failure("保存员工信息失败");
        }
    }
    @ApiOperation(value = "员工获取附件", notes = "员工获取附件")
    @GetMapping("getYgfj")
    public InvokeResult getYgfj(@RequestParam(required = false) String ygqyid) {
        try {
            BaseMapper<HdlYgfj> baseMapper = hdlYgfjService.getBaseMapper();
            QueryWrapper<HdlYgfj> wrapper = new QueryWrapper<>();
            wrapper.eq("ygqyid", ygqyid);
            HdlYgfj hdlYgfj = baseMapper.selectOne(wrapper);
            String tx= hdlYgfj.getTx();
            List<Map<String, Object>> fileList = JSON.parseObject(tx, List.class);
            return InvokeResult.success("获取协议附件成功！", fileList);
        } catch (Exception e) {
            return InvokeResult.failure("获取协议附件失败");
        }
    }


    @ApiOperation(value = "员工信息-更新", notes = "员工信息-更新")
    @PutMapping("update")
    public InvokeResult update(@RequestBody JSONObject jsonObject) {
        try {
            String id=jsonObject.getString("id");
            String ygname = jsonObject.getString("ygname");
            String ygsex = jsonObject.getString("ygsex");
            String ygqy = jsonObject.getString("ygqy");
            String ygbirth = jsonObject.getString("ygbirth");
            String ygsfzid = jsonObject.getString("ygsfzid");
            String ygyx = jsonObject.getString("ygyx");
            String ygqyid = jsonObject.getString("ygqyid");
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
            HdlYgxx entity = new HdlYgxx();
            entity.setId(Long.valueOf(id));
            entity.setYgname(ygname);
            entity.setYgsex(ygsex);
            entity.setYgqy(ygqy);
            entity.setYgbirth(ygbirth);
            entity.setYgsfzid(ygsfzid);
            entity.setYgyx(ygyx);
            entity.setYgqyid(ygqyid);

            if (StringUtils.isNotEmpty(entity.getYgbirth()) && entity.getYgbirth().length() > 19) {
                entity.setYgbirth(entity.getYgbirth().substring(0, 19).replace("T", " "));
            }
            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            entity.setZhxgr(user.getUsername());
            entity.setZhxgsj(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat));

            try {
                UpdateWrapper<HdlYgfj> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("ygqyid", Long.valueOf(ygqyid)).set("tx", JSON.toJSONString(fileList));
                boolean flag = hdlYgfjService.update(null, updateWrapper);
                if (flag) {
                    return InvokeResult.success("更新协议附件成功！");
                } else {
                    return InvokeResult.failure("更新协议附件失败！");
                }
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
            }

            boolean success = hdlYgxxService.updateById(entity);
            if (success) {
                return InvokeResult.success("更新成功");
            } else {
                return InvokeResult.failure("更新失败");
            }
        } catch (Exception e) {
            log.error("更新员工信息失败", e);
            return InvokeResult.failure("更新员工信息失败");
        }
    }
    @ApiOperation(value = "员工信息-获取详情", notes = "员工信息-获取详情")
    @GetMapping("getById")
    public InvokeResult getById(@RequestParam String id) {
        HdlYgxx hdlYgxx = hdlYgxxService.getById(id);
        return InvokeResult.success(null, hdlYgxx);
    }

    @ApiOperation(value = "员工信息-分页查询列表", notes = "员工信息-分页查询列表")
    @GetMapping("listPage")
    public InvokeResult listPage(@ModelAttribute HdlYgxxPageQuery pageQuery) {
        IPage<HdlYgxx> page = MyQueryHelper.createPage(pageQuery, HdlYgxx.class);
        QueryWrapper<HdlYgxx> queryWrapper = MyQueryHelper.createQueryWrapper(pageQuery, HdlYgxx.class);
        IPage<HdlYgxx> pageList = hdlYgxxService.page(page, queryWrapper);
        return InvokeResult.success(null, pageList);
    }

    @ApiOperation(value = "员工信息-删除", notes = "员工信息-删除")
    @DeleteMapping("deleteById")
    public InvokeResult deleteById(@RequestParam String id) {
        try {
            boolean success = hdlYgxxService.removeById(id);
            if (success) {
                return InvokeResult.success("删除成功", id);
            } else {
                return InvokeResult.failure("删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("删除员工信息失败", e);
            return InvokeResult.failure("删除员工信息失败");
        }
    }

    @ApiOperation(value = "员工信息-批量删除", notes = "员工信息-批量删除")
    @DeleteMapping("deleteBatchByIds")
    public InvokeResult deleteBatchByIds(@RequestParam String ids) {
        try {
            List<String> idList = CollectionUtil.string2List(ids, ",");
            boolean success = hdlYgxxService.removeByIds(idList);
            if (success) {
                return InvokeResult.success("批量删除成功", idList);
            } else {
                return InvokeResult.failure("批量删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("批量删除员工信息失败", e);
            return InvokeResult.failure("批量删除员工信息失败");
        }
    }

    @ApiOperation(value = "员工信息-字典获取", notes = "员工信息-字典获取")
    @GetMapping("queryDictInfo")
    public InvokeResult queryDictInfo(){
        try{
            List<Map> resList = hdlYgxxService.queryDictInfo();
            return InvokeResult.success("获取员工数据字典信息成功", resList);
        }catch (Exception e){
            log.error("批量删除员工信息失败", e);
            return InvokeResult.failure("获取企业数据字典信息失败");
        }
    }





}
