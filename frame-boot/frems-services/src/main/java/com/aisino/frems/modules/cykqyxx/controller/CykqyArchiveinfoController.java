package com.aisino.frems.modules.cykqyxx.controller;

import com.aisino.frems.common.model.LoginUser;
import com.aisino.frems.common.util.DateUtils;
import com.alibaba.fastjson.JSONObject;
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
import com.aisino.frems.modules.cykqyxx.entity.CykqyArchiveinfo;
import com.aisino.frems.modules.cykqyxx.service.ICykqyArchiveinfoService;
import com.aisino.frems.modules.cykqyxx.vo.CykqyArchiveinfoPageQuery;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * cyk企业信息Controller控制类
 *
 * @author create by cyk
 * @date 2021-09-24
 */
@Slf4j
@Api(tags = "cyk企业信息")
@RestController
@RequestMapping("qygl/cykqyxx")
public class CykqyArchiveinfoController {
    @Resource
    private ICykqyArchiveinfoService cykqyArchiveinfoService;

    @ApiOperation(value = "cyk企业信息-保存", notes = "cyk企业信息-保存")
    @PostMapping("save")
    public InvokeResult save(@RequestBody CykqyArchiveinfo entity) {


       try {
//            if (StringUtils.isNotEmpty(entity.getZfsj()) && entity.getZfsj().length() > 19) {
//                entity.setZfsj(entity.getZfsj().substring(0, 19).replace("T"," "));
//            }
//            if (StringUtils.isNotEmpty(entity.getHtyxq()) && entity.getHtyxq().length() > 10) {
//                entity.setHtyxq(entity.getHtyxq().substring(0, 10));
//            }
//            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//            entity.setLrr(user.getUsername());
//            entity.setLrsj(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat));

            boolean success = cykqyArchiveinfoService.save(entity);
            if (success) {
                return InvokeResult.success("保存成功", entity);
            } else {
                return InvokeResult.failure("保存失败");
            }
        } catch (Exception e) {
            log.error("保存cyk企业信息失败", e);
            return InvokeResult.failure("保存cyk企业信息失败");
        }
    }

    @ApiOperation(value = "cyk企业信息-更新", notes = "cyk企业信息-更新")
    @PutMapping("update")
    public InvokeResult update(@RequestBody CykqyArchiveinfo entity) {
        try {
            boolean success = cykqyArchiveinfoService.updateById(entity);
            if (success) {
                return InvokeResult.success("更新成功");
            } else {
                return InvokeResult.failure("更新失败");
            }
        } catch (Exception e) {
            log.error("更新cyk企业信息失败", e);
            return InvokeResult.failure("更新cyk企业信息失败");
        }
    }

    @ApiOperation(value = "cyk企业信息-获取详情", notes = "cyk企业信息-获取详情")
    @GetMapping("getById")
    public InvokeResult getById(@RequestParam String id) {
        CykqyArchiveinfo cykqyArchiveinfo = cykqyArchiveinfoService.getById(id);
        return InvokeResult.success(null, cykqyArchiveinfo);
    }

    @ApiOperation(value = "cyk企业信息-分页查询列表", notes = "cyk企业信息-分页查询列表")
    @GetMapping("listPage")
    public InvokeResult listPage(@ModelAttribute CykqyArchiveinfoPageQuery pageQuery) {
        IPage<CykqyArchiveinfo> page = MyQueryHelper.createPage(pageQuery, CykqyArchiveinfo.class);
        QueryWrapper<CykqyArchiveinfo> queryWrapper = MyQueryHelper.createQueryWrapper(pageQuery, CykqyArchiveinfo.class);
        IPage<CykqyArchiveinfo> pageList = cykqyArchiveinfoService.page(page, queryWrapper);
        return InvokeResult.success(null, pageList);
    }

    @ApiOperation(value = "cyk企业信息-删除", notes = "cyk企业信息-删除")
    @DeleteMapping("deleteById")
    public InvokeResult deleteById(@RequestParam String id) {
        try {
            boolean success = cykqyArchiveinfoService.removeById(id);
            if (success) {
                return InvokeResult.success("删除成功", id);
            } else {
                return InvokeResult.failure("删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("删除cyk企业信息失败", e);
            return InvokeResult.failure("删除cyk企业信息失败");
        }
    }

    @ApiOperation(value = "cyk企业信息-批量删除", notes = "cyk企业信息-批量删除")
    @DeleteMapping("deleteBatchByIds")
    public InvokeResult deleteBatchByIds(@RequestParam String ids) {
        try {
            List<String> idList = CollectionUtil.string2List(ids, ",");
            boolean success = cykqyArchiveinfoService.removeByIds(idList);
            if (success) {
                return InvokeResult.success("批量删除成功", idList);
            } else {
                return InvokeResult.failure("批量删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("批量删除cyk企业信息失败", e);
            return InvokeResult.failure("批量删除cyk企业信息失败");
        }
    }
}
