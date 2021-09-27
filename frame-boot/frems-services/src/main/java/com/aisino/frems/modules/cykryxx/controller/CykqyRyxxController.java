package com.aisino.frems.modules.cykryxx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.asframework.base.model.InvokeResult;
import org.asframework.core.util.CollectionUtil;
import org.asframework.data.mybatis.util.MyQueryHelper;
import com.aisino.frems.modules.cykryxx.entity.CykqyRyxx;
import com.aisino.frems.modules.cykryxx.service.ICykqyRyxxService;
import com.aisino.frems.modules.cykryxx.vo.CykqyRyxxPageQuery;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * cyk人员信息Controller控制类
 *
 * @author create by cyk
 * @date 2021-09-27
 */
@Slf4j
@Api(tags = "cyk人员信息")
@RestController
@RequestMapping("qygl/cykryxx")
public class CykqyRyxxController {
    @Resource
    private ICykqyRyxxService cykqyRyxxService;

    @ApiOperation(value = "cyk人员信息-保存", notes = "cyk人员信息-保存")
    @PostMapping("save")
    public InvokeResult save(@RequestBody CykqyRyxx entity) {
        try {
            boolean success = cykqyRyxxService.save(entity);
            if (success) {
                return InvokeResult.success("保存成功", entity);
            } else {
                return InvokeResult.failure("保存失败");
            }
        } catch (Exception e) {
            log.error("保存cyk人员信息失败", e);
            return InvokeResult.failure("保存cyk人员信息失败");
        }
    }

    @ApiOperation(value = "cyk人员信息-更新", notes = "cyk人员信息-更新")
    @PutMapping("update")
    public InvokeResult update(@RequestBody CykqyRyxx entity) {
        try {
            boolean success = cykqyRyxxService.updateById(entity);
            if (success) {
                return InvokeResult.success("更新成功");
            } else {
                return InvokeResult.failure("更新失败");
            }
        } catch (Exception e) {
            log.error("更新cyk人员信息失败", e);
            return InvokeResult.failure("更新cyk人员信息失败");
        }
    }

    @ApiOperation(value = "cyk人员信息-获取详情", notes = "cyk人员信息-获取详情")
    @GetMapping("getById")
    public InvokeResult getById(@RequestParam String id) {
        CykqyRyxx cykqyRyxx = cykqyRyxxService.getById(id);
        return InvokeResult.success(null, cykqyRyxx);
    }

    @ApiOperation(value = "cyk人员信息-分页查询列表", notes = "cyk人员信息-分页查询列表")
    @GetMapping("listPage")
    public InvokeResult listPage(@ModelAttribute CykqyRyxxPageQuery pageQuery) {
        IPage<CykqyRyxx> page = MyQueryHelper.createPage(pageQuery, CykqyRyxx.class);
        QueryWrapper<CykqyRyxx> queryWrapper = MyQueryHelper.createQueryWrapper(pageQuery, CykqyRyxx.class);
        IPage<CykqyRyxx> pageList = cykqyRyxxService.page(page, queryWrapper);
        return InvokeResult.success(null, pageList);
    }

    @ApiOperation(value = "cyk人员信息-删除", notes = "cyk人员信息-删除")
    @DeleteMapping("deleteById")
    public InvokeResult deleteById(@RequestParam String id) {
        try {
            boolean success = cykqyRyxxService.removeById(id);
            if (success) {
                return InvokeResult.success("删除成功", id);
            } else {
                return InvokeResult.failure("删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("删除cyk人员信息失败", e);
            return InvokeResult.failure("删除cyk人员信息失败");
        }
    }

    @ApiOperation(value = "cyk人员信息-批量删除", notes = "cyk人员信息-批量删除")
    @DeleteMapping("deleteBatchByIds")
    public InvokeResult deleteBatchByIds(@RequestParam String ids) {
        try {
            List<String> idList = CollectionUtil.string2List(ids, ",");
            boolean success = cykqyRyxxService.removeByIds(idList);
            if (success) {
                return InvokeResult.success("批量删除成功", idList);
            } else {
                return InvokeResult.failure("批量删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("批量删除cyk人员信息失败", e);
            return InvokeResult.failure("批量删除cyk人员信息失败");
        }
    }
}
