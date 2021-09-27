package com.aisino.frems.modules.statistics.controller;

import com.aisino.frems.common.base.controller.BaseController;
import com.aisino.frems.modules.hmd.service.IHmdxxService;
import com.aisino.frems.modules.qygl.service.IArchiveinfoService;
import com.aisino.frems.modules.ryxx.service.IRyxxService;
import com.aisino.frems.modules.xyxx.service.IXyxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.asframework.base.model.InvokeResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "首页")
@RestController
@RequestMapping("statistics/index")
public class QyIndexController extends BaseController {
    @Resource
    private IArchiveinfoService archiveinfoService;
    @Resource
    private IHmdxxService hmdxxService;
    @Resource
    private IRyxxService ryxxService;
    @Resource
    private IXyxxService xyxxService;


    @ApiOperation(value = "统计企业总数", notes = "统计企业总数")
    @GetMapping("getQyCount")
    public InvokeResult getQyCount() {
        try {
            int count = archiveinfoService.count();
            return InvokeResult.success("统计企业总数成功", count);
        } catch (Exception e) {
            return InvokeResult.failure("统计企业总数失败");
        }
    }

    @ApiOperation(value = "统计企业人数", notes = "统计企业人数")
    @GetMapping("getQyrsCount")
    public InvokeResult getQyrsCount() {
        try {
            int count = ryxxService.count();
            return InvokeResult.success("统计企业人数成功", count);
        } catch (Exception e) {
            return InvokeResult.failure("统计企业人数失败");
        }
    }

    @ApiOperation(value = "统计协议总数", notes = "统计协议总数")
    @GetMapping("getXyzsCount")
    public InvokeResult getXyzsCount() {
        try {
            int count = xyxxService.count();
            return InvokeResult.success("统计协议总数成功", count);
        } catch (Exception e) {
            return InvokeResult.failure("统计协议总数失败");
        }
    }

    @ApiOperation(value = "统计黑名单数", notes = "统计黑名单数")
    @GetMapping("getHmdCount")
    public InvokeResult getHmdCount() {
        try {
            int count = hmdxxService.count();
            return InvokeResult.success("统计黑名单数成功", count);
        } catch (Exception e) {
            return InvokeResult.failure("统计黑名单数失败");
        }
    }


}
