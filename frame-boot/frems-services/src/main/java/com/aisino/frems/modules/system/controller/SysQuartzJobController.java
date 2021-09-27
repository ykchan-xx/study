package com.aisino.frems.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.asframework.base.model.InvokeResult;
import org.asframework.core.util.CollectionUtil;
import org.asframework.data.mybatis.util.MyQueryHelper;
import com.aisino.frems.modules.system.entity.SysQuartzJob;
import com.aisino.frems.modules.system.service.ISysQuartzJobService;
import com.aisino.frems.modules.system.vo.SysQuartzJobPageQuery;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务配置Controller控制类
 *
 * @author hxq
 * @date 2020-04-23
 */
@Slf4j
@Api(tags = "定时任务配置")
@RestController
@RequestMapping("sys/quartzJob")
public class SysQuartzJobController {
    @Resource
    private ISysQuartzJobService sysQuartzJobService;

    @ApiOperation(value = "定时任务配置-保存", notes = "定时任务配置-保存")
    @PostMapping("save")
    public InvokeResult save(@RequestBody SysQuartzJob entity) {
        try {
            boolean success = sysQuartzJobService.save(entity);
            if (success) {
                return InvokeResult.success("保存成功", entity);
            } else {
                return InvokeResult.failure("保存失败");
            }
        } catch (Exception e) {
            log.error("保存定时任务配置失败", e);
            return InvokeResult.failure("保存定时任务配置失败");
        }
    }

    @ApiOperation(value = "定时任务配置-更新", notes = "定时任务配置-更新")
    @PutMapping("update")
    public InvokeResult update(@RequestBody SysQuartzJob entity) {
        try {
            boolean success = sysQuartzJobService.updateById(entity);
            if (success) {
                return InvokeResult.success("更新成功");
            } else {
                return InvokeResult.failure("更新失败");
            }
        } catch (Exception e) {
            log.error("更新定时任务配置失败", e);
            return InvokeResult.failure("更新定时任务配置失败");
        }
    }

    @ApiOperation(value = "定时任务配置-获取详情", notes = "定时任务配置-获取详情")
    @GetMapping("getById")
    public InvokeResult getById(@RequestParam String id) {
        SysQuartzJob sysQuartzJob = sysQuartzJobService.getById(id);
        return InvokeResult.success(null, sysQuartzJob);
    }

    @ApiOperation(value = "定时任务配置-分页查询列表", notes = "定时任务配置-分页查询列表")
    @GetMapping("listPage")
    public InvokeResult listPage(@ModelAttribute SysQuartzJobPageQuery pageQuery) {
        IPage<SysQuartzJob> page = MyQueryHelper.createPage(pageQuery, SysQuartzJob.class);
        QueryWrapper<SysQuartzJob> queryWrapper = MyQueryHelper.createQueryWrapper(pageQuery, SysQuartzJob.class);
        IPage<SysQuartzJob> pageList = sysQuartzJobService.page(page, queryWrapper);
        return InvokeResult.success(null, pageList);
    }

    @ApiOperation(value = "定时任务配置-删除", notes = "定时任务配置-删除")
    @DeleteMapping("deleteById")
    public InvokeResult deleteById(@RequestParam String id) {
        try {
            boolean success = sysQuartzJobService.removeById(id);
            if (success) {
                return InvokeResult.success("删除成功", id);
            } else {
                return InvokeResult.failure("删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("删除定时任务配置失败", e);
            return InvokeResult.failure("删除定时任务配置失败");
        }
    }

    @ApiOperation(value = "定时任务配置-批量删除", notes = "定时任务配置-批量删除")
    @DeleteMapping("deleteBatchByIds")
    public InvokeResult deleteBatchByIds(@RequestParam String ids) {
        try {
            List<String> idList = CollectionUtil.string2List(ids, ",");
            boolean success = sysQuartzJobService.removeByIds(idList);
            if (success) {
                return InvokeResult.success("批量删除成功", idList);
            } else {
                return InvokeResult.failure("批量删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("批量删除定时任务配置失败", e);
            return InvokeResult.failure("批量删除定时任务配置失败");
        }
    }
}
