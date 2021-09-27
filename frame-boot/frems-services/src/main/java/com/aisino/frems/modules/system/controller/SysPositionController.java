package com.aisino.frems.modules.system.controller;

import com.aisino.frems.common.annotation.AutoLog;
import com.aisino.frems.common.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.asframework.base.model.InvokeResult;
import com.aisino.frems.modules.system.entity.SysPosition;
import com.aisino.frems.modules.system.service.ISysPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Description: 职务表
 * @Author: jeecg-boot
 * @Date: 2019-09-19
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "职务表")
@RestController
@RequestMapping("/sys/position")
public class SysPositionController {

    @Autowired
    private ISysPositionService sysPositionService;

    /**
     * 分页列表查询
     *
     * @param sysPosition
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "职务表-分页列表查询")
    @ApiOperation(value = "职务表-分页列表查询", notes = "职务表-分页列表查询")
    @GetMapping(value = "/list")
    public InvokeResult queryPageList(SysPosition sysPosition,
                                                    @RequestParam(name = "page.pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "page.pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<SysPosition> queryWrapper = QueryGenerator.initQueryWrapper(sysPosition, req.getParameterMap());
        Page<SysPosition> page = new Page<SysPosition>(pageNo, pageSize);
        IPage<SysPosition> pageList = sysPositionService.page(page, queryWrapper);
        return InvokeResult.success(null, pageList);
    }

    /**
     * 添加
     *
     * @param sysPosition
     * @return
     */
    @AutoLog(value = "职务表-添加")
    @ApiOperation(value = "职务表-添加", notes = "职务表-添加")
    @PostMapping(value = "/add")
    public InvokeResult add(@RequestBody SysPosition sysPosition) {
        try {
            sysPositionService.save(sysPosition);
            return InvokeResult.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return InvokeResult.failure("500", "操作失败");
        }
    }

    /**
     * 编辑
     *
     * @param sysPosition
     * @return
     */
    @AutoLog(value = "职务表-编辑")
    @ApiOperation(value = "职务表-编辑", notes = "职务表-编辑")
    @PutMapping(value = "/edit")
    public InvokeResult edit(@RequestBody SysPosition sysPosition) {
        SysPosition sysPositionEntity = sysPositionService.getById(sysPosition.getId());
        if (sysPositionEntity == null) {
            return InvokeResult.failure("500", "未找到对应实体");
        } else {
            boolean ok = sysPositionService.updateById(sysPosition);
            //TODO 返回false说明什么？
            if (ok) {
                return InvokeResult.success("修改成功!");
            }
        }
        return InvokeResult.failure("error");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "职务表-通过id删除")
    @ApiOperation(value = "职务表-通过id删除", notes = "职务表-通过id删除")
    @DeleteMapping(value = "/delete")
    public InvokeResult delete(@RequestParam(name = "id", required = true) String id) {
        try {
            sysPositionService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e.getMessage());
            return InvokeResult.failure("删除失败!");
        }
        return InvokeResult.success("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "职务表-批量删除")
    @ApiOperation(value = "职务表-批量删除", notes = "职务表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public InvokeResult deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        if (ids == null || "".equals(ids.trim())) {
            return InvokeResult.failure("500", "参数不识别！");
        } else {
            this.sysPositionService.removeByIds(Arrays.asList(ids.split(",")));
            return InvokeResult.success("删除成功!");
        }
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "职务表-通过id查询")
    @ApiOperation(value = "职务表-通过id查询", notes = "职务表-通过id查询")
    @GetMapping(value = "/queryById")
    public InvokeResult queryById(@RequestParam(name = "id", required = true) String id) {
        SysPosition sysPosition = sysPositionService.getById(id);
        if (sysPosition == null) {
            return InvokeResult.failure("500", "未找到对应实体");
        } else {
            return InvokeResult.success(null, sysPosition);
        }
    }

}
