package com.aisino.frems.modules.qygl.controller;

import com.aisino.frems.common.model.LoginUser;
import com.aisino.frems.common.util.DateUtils;
import com.aisino.frems.modules.qygl.entity.Archiveinfo;
import com.aisino.frems.modules.qygl.service.IArchiveinfoService;
import com.aisino.frems.modules.qygl.vo.ArchiveinfoPageQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 企业信息Controller控制类
 *
 * @author create by caochundi
 * @date 2021-06-22
 */
@Slf4j
@Api(tags = "企业信息")
@RestController
@RequestMapping("qygl/qyxx")
public class ArchiveinfoController {
    @Resource
    private IArchiveinfoService archiveinfoService;

    @ApiOperation(value = "企业信息-保存", notes = "企业信息-保存")
    @PostMapping("save")
    public InvokeResult save(@RequestBody Archiveinfo entity) {
        try {
            if (StringUtils.isNotEmpty(entity.getZfsj()) && entity.getZfsj().length() > 19) {
                entity.setZfsj(entity.getZfsj().substring(0, 19).replace("T"," "));
            }
            if (StringUtils.isNotEmpty(entity.getHtyxq()) && entity.getHtyxq().length() > 10) {
                entity.setHtyxq(entity.getHtyxq().substring(0, 10));
            }
            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            entity.setLrr(user.getUsername());
            entity.setLrsj(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat));
            boolean success = archiveinfoService.save(entity);
            if (success) {
                return InvokeResult.success("保存成功", entity);
            } else {
                return InvokeResult.failure("保存失败");
            }
        } catch (Exception e) {
            log.error("保存企业信息失败", e);
            return InvokeResult.failure("保存企业信息失败");
        }
    }

    @ApiOperation(value = "企业信息-更新", notes = "企业信息-更新")
    @PutMapping("update")
    public InvokeResult update(@RequestBody Archiveinfo entity) {
        try {
            if (StringUtils.isNotEmpty(entity.getZfsj()) && entity.getZfsj().length() > 19) {
                entity.setZfsj(entity.getZfsj().substring(0, 19).replace("T"," "));
            }
            if (StringUtils.isNotEmpty(entity.getHtyxq()) && entity.getHtyxq().length() > 10) {
                entity.setHtyxq(entity.getHtyxq().substring(0, 10));
            }
            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            entity.setZhxgr(user.getUsername());
            entity.setZhxgsj(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat));
            boolean success = archiveinfoService.updateById(entity);
            if (success) {
                return InvokeResult.success("更新成功");
            } else {
                return InvokeResult.failure("更新失败");
            }
        } catch (Exception e) {
            log.error("更新企业信息失败", e);
            return InvokeResult.failure("更新企业信息失败");
        }
    }

    @ApiOperation(value = "企业信息-获取详情", notes = "企业信息-获取详情")
    @GetMapping("getById")
    public InvokeResult getById(@RequestParam String id) {
        Archiveinfo archiveinfo = archiveinfoService.getById(id);
        return InvokeResult.success(null, archiveinfo);
    }

    @ApiOperation(value = "企业信息-分页查询列表", notes = "企业信息-分页查询列表")
    @GetMapping("listPage")
    public InvokeResult listPage(@ModelAttribute ArchiveinfoPageQuery pageQuery) {
        IPage<Archiveinfo> page = MyQueryHelper.createPage(pageQuery, Archiveinfo.class);
        QueryWrapper<Archiveinfo> queryWrapper = MyQueryHelper.createQueryWrapper(pageQuery, Archiveinfo.class);
        IPage<Archiveinfo> pageList = archiveinfoService.page(page, queryWrapper);
        return InvokeResult.success(null, pageList);
    }

    @ApiOperation(value = "企业信息-删除", notes = "企业信息-删除")
    @DeleteMapping("deleteById")
    public InvokeResult deleteById(@RequestParam String id) {
        try {
            boolean success = archiveinfoService.removeById(id);
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
            boolean success = archiveinfoService.removeByIds(idList);
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
    public InvokeResult queryDictInfo(){
        try {
            List<Map> resList = archiveinfoService.queryDictInfo();
            return InvokeResult.success("获取企业数据字典信息成功", resList);
        } catch (Exception e) {
            log.error("批量删除企业信息失败", e);
            return InvokeResult.failure("获取企业数据字典信息失败");
        }

        }

    }
