package com.aisino.frems.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.asframework.base.model.InvokeResult;
import org.asframework.core.util.CollectionUtil;
import org.asframework.data.mybatis.util.MyQueryHelper;
import com.aisino.frems.modules.system.entity.SysConfig;
import com.aisino.frems.modules.system.service.ISysConfigService;
import com.aisino.frems.modules.system.vo.SysConfigPageQuery;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统参数Controller控制类
 *
 * @author zhuxiaoyan
 * @date 2020-03-31
 */
@Slf4j
@Api(tags = "系统参数")
@RestController
@RequestMapping("system/config")
public class SysConfigController {
    @Resource
    private ISysConfigService sysConfigService;

    @ApiOperation(value = "系统参数-保存", notes = "系统参数-保存")
    @PostMapping("save")
    public InvokeResult save(@RequestBody SysConfig entity) {
        try {
            boolean success = sysConfigService.save(entity);
            if (success) {
                return InvokeResult.success("保存成功", entity);
            } else {
                return InvokeResult.failure("保存失败");
            }
        } catch (Exception e) {
            log.error("保存系统参数失败", e);
            return InvokeResult.failure("保存系统参数失败");
        }
    }

    @ApiOperation(value = "系统参数-更新", notes = "系统参数-更新")
    @PutMapping("update")
    public InvokeResult update(@RequestBody SysConfig entity) {
        try {
            boolean success = sysConfigService.updateById(entity);
            if (success) {
                return InvokeResult.success("更新成功");
            } else {
                return InvokeResult.failure("更新失败");
            }
        } catch (Exception e) {
            log.error("更新系统参数失败", e);
            return InvokeResult.failure("更新系统参数失败");
        }
    }

    @ApiOperation(value = "系统参数-获取详情", notes = "系统参数-获取详情")
    @GetMapping("getById")
    public InvokeResult getById(@RequestParam String id) {
        SysConfig sysConfig = sysConfigService.getById(id);
        return InvokeResult.success(null, sysConfig);
    }

    @ApiOperation(value = "系统参数-分页查询列表", notes = "系统参数-分页查询列表")
    @GetMapping("listPage")
    public InvokeResult listPage(@ModelAttribute SysConfigPageQuery pageQuery) {
        IPage<SysConfig> page = MyQueryHelper.createPage(pageQuery, SysConfig.class);
        QueryWrapper<SysConfig> queryWrapper = MyQueryHelper.createQueryWrapper(pageQuery, SysConfig.class);
        IPage<SysConfig> pageList = sysConfigService.page(page, queryWrapper);
        return InvokeResult.success(null, pageList);
    }

    @ApiOperation(value = "系统参数-删除", notes = "系统参数-删除")
    @DeleteMapping("deleteById")
    public InvokeResult deleteById(@RequestParam String id) {
        try {
            boolean success = sysConfigService.removeById(id);
            if (success) {
                return InvokeResult.success("删除成功", id);
            } else {
                return InvokeResult.failure("删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("删除系统参数失败", e);
            return InvokeResult.failure("删除系统参数失败");
        }
    }

    @ApiOperation(value = "系统参数-批量删除", notes = "系统参数-批量删除")
    @DeleteMapping("deleteBatchByIds")
    public InvokeResult deleteBatchByIds(@RequestParam String ids) {
        try {
            List<String> idList = CollectionUtil.string2List(ids, ",");
            boolean success = sysConfigService.removeByIds(idList);
            if (success) {
                return InvokeResult.success("批量删除成功", idList);
            } else {
                return InvokeResult.failure("批量删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("批量删除系统参数失败", e);
            return InvokeResult.failure("批量删除系统参数失败");
        }
    }
}
