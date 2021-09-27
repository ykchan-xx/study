package com.aisino.frems.modules.hmd.controller;

import com.aisino.frems.common.model.LoginUser;
import com.aisino.frems.common.util.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.asframework.base.model.InvokeResult;
import org.asframework.core.util.CollectionUtil;
import org.asframework.data.mybatis.util.MyQueryHelper;
import com.aisino.frems.modules.hmd.entity.Hmdxx;
import com.aisino.frems.modules.hmd.service.IHmdxxService;
import com.aisino.frems.modules.hmd.vo.HmdxxPageQuery;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 企业黑名单管理Controller控制类
 *
 * @author create by yangshunfei
 * @date 2021-06-22
 */
@Slf4j
@Api(tags = "企业黑名单管理")
@RestController
@RequestMapping("qygl/hmd")
public class HmdxxController {
    @Resource
    private IHmdxxService hmdxxService;

    @ApiOperation(value = "企业黑名单管理-保存", notes = "企业黑名单管理-保存")
    @PostMapping("save")
    public InvokeResult save(@RequestBody Hmdxx entity) {
        try {
            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            entity.setLrr(user.getUsername());
            entity.setLrsj(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat));
            boolean success = hmdxxService.save(entity);
            if (success) {
                return InvokeResult.success("保存成功", entity);
            } else {
                return InvokeResult.failure("保存失败");
            }
        } catch (Exception e) {
            log.error("保存企业黑名单管理失败", e);
            return InvokeResult.failure("保存企业黑名单管理失败");
        }
    }

    @ApiOperation(value = "企业黑名单管理-更新", notes = "企业黑名单管理-更新")
    @PutMapping("update")
    public InvokeResult update(@RequestBody Hmdxx entity) {
        try {
            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            entity.setZhxgr(user.getUsername());
            entity.setZhxgsj(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat));
            boolean success = hmdxxService.updateById(entity);
            if (success) {
                return InvokeResult.success("更新成功");
            } else {
                return InvokeResult.failure("更新失败");
            }
        } catch (Exception e) {
            log.error("更新企业黑名单管理失败", e);
            return InvokeResult.failure("更新企业黑名单管理失败");
        }
    }

    @ApiOperation(value = "企业黑名单管理-获取详情", notes = "企业黑名单管理-获取详情")
    @GetMapping("getById")
    public InvokeResult getById(@RequestParam String id) {
        Hmdxx hmdxx = hmdxxService.getById(id);
        return InvokeResult.success(null, hmdxx);
    }

    @ApiOperation(value = "企业黑名单管理-分页查询列表", notes = "企业黑名单管理-分页查询列表")
    @GetMapping("listPage")
    public InvokeResult listPage(@ModelAttribute HmdxxPageQuery pageQuery) {
        IPage<Hmdxx> page = MyQueryHelper.createPage(pageQuery, Hmdxx.class);
        QueryWrapper<Hmdxx> queryWrapper = MyQueryHelper.createQueryWrapper(pageQuery, Hmdxx.class);
        IPage<Hmdxx> pageList = hmdxxService.page(page, queryWrapper);
        return InvokeResult.success(null, pageList);
    }

    @ApiOperation(value = "企业黑名单管理-删除", notes = "企业黑名单管理-删除")
    @DeleteMapping("deleteById")
    public InvokeResult deleteById(@RequestParam String id) {
        try {
            boolean success = hmdxxService.removeById(id);
            if (success) {
                return InvokeResult.success("删除成功", id);
            } else {
                return InvokeResult.failure("删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("删除企业黑名单管理失败", e);
            return InvokeResult.failure("删除企业黑名单管理失败");
        }
    }

    @ApiOperation(value = "企业黑名单管理-批量删除", notes = "企业黑名单管理-批量删除")
    @DeleteMapping("deleteBatchByIds")
    public InvokeResult deleteBatchByIds(@RequestParam String ids) {
        try {
            List<String> idList = CollectionUtil.string2List(ids, ",");
            boolean success = hmdxxService.removeByIds(idList);
            if (success) {
                return InvokeResult.success("批量删除成功", idList);
            } else {
                return InvokeResult.failure("批量删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("批量删除企业黑名单管理失败", e);
            return InvokeResult.failure("批量删除企业黑名单管理失败");
        }
    }
}
