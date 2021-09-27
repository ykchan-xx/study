package com.aisino.frems.modules.yjxx.controller;

import com.aisino.frems.modules.system.service.ISysDictService;
import com.aisino.frems.modules.yjxx.entity.QyYjxxb;
import com.aisino.frems.modules.yjxx.service.IQyYjxxbService;
import com.aisino.frems.modules.yjxx.vo.QyYjxxbPageQuery;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.asframework.base.model.InvokeResult;
import org.asframework.core.util.CollectionUtil;
import org.asframework.data.mybatis.util.MyQueryHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 预警信息Controller控制类
 *
 * @author create by luozheng
 * @date 2021-07-02
 */
@Slf4j
@Api(tags = "预警信息")
@RestController
@RequestMapping("qygl/yjxx")
public class QyYjxxbController {
    @Resource
    private IQyYjxxbService qyYjxxbService;
    @Resource
    private ISysDictService dictService;

    @ApiOperation(value = "预警信息-保存", notes = "预警信息-保存")
    @PostMapping("save")
    public InvokeResult save(@RequestBody QyYjxxb entity) {
        try {
            boolean success = qyYjxxbService.save(entity);
            if (success) {
                return InvokeResult.success("保存成功", entity);
            } else {
                return InvokeResult.failure("保存失败");
            }
        } catch (Exception e) {
            log.error("保存预警信息失败", e);
            return InvokeResult.failure("保存预警信息失败");
        }
    }

    @ApiOperation(value = "预警信息-更新", notes = "预警信息-更新")
    @PutMapping("update")
    public InvokeResult update(@RequestBody QyYjxxb entity) {
        try {
            boolean success = qyYjxxbService.updateById(entity);
            if (success) {

                return InvokeResult.success("更新成功");
            } else {
                return InvokeResult.failure("更新失败");
            }
        } catch (Exception e) {
            log.error("更新预警信息失败", e);
            return InvokeResult.failure("更新预警信息失败");
        }
    }

    @ApiOperation(value = "预警信息-获取详情", notes = "预警信息-获取详情")
    @GetMapping("getById")
    public InvokeResult getById(@RequestParam String id) {
        QyYjxxb qyYjxxb = qyYjxxbService.getById(id);
        return InvokeResult.success(null, qyYjxxb);
    }

    @ApiOperation(value = "预警信息-分页查询列表", notes = "预警信息-分页查询列表")
    @GetMapping("listPage")
    public InvokeResult listPage(@ModelAttribute QyYjxxbPageQuery pageQuery) {
        IPage<QyYjxxb> page = MyQueryHelper.createPage(pageQuery, QyYjxxb.class);
        QueryWrapper<QyYjxxb> queryWrapper = MyQueryHelper.createQueryWrapper(pageQuery, QyYjxxb.class);
        IPage<QyYjxxb> pageList = qyYjxxbService.page(page, queryWrapper);
        if (pageList.getRecords() != null && pageList.getRecords().size()>0) {
            pageList.getRecords().stream().forEach(item -> {
                item.setGjdq(dictService.queryDictTextByKey("gjdq",item.getGjdq()));
                item.setSex(dictService.queryDictTextByKey("sex",item.getSex()));
                item.setZjzl(dictService.queryDictTextByKey("zjzl",item.getZjzl()));
                item.setYjlx(dictService.queryDictTextByKey("yjlx",item.getYjlx()));
                item.setYjzt(dictService.queryDictTextByKey("yjzt",item.getYjzt()));
            });
        }
        return InvokeResult.success(null, pageList);
    }

    @ApiOperation(value = "预警信息-删除", notes = "预警信息-删除")
    @DeleteMapping("deleteById")
    public InvokeResult deleteById(@RequestParam String id) {
        try {
            boolean success = qyYjxxbService.removeById(id);
            if (success) {
                return InvokeResult.success("删除成功", id);
            } else {
                return InvokeResult.failure("删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("删除预警信息失败", e);
            return InvokeResult.failure("删除预警信息失败");
        }
    }

    @ApiOperation(value = "预警信息-批量删除", notes = "预警信息-批量删除")
    @DeleteMapping("deleteBatchByIds")
    public InvokeResult deleteBatchByIds(@RequestParam String ids) {
        try {
            List<String> idList = CollectionUtil.string2List(ids, ",");
            boolean success = qyYjxxbService.removeByIds(idList);
            if (success) {
                return InvokeResult.success("批量删除成功", idList);
            } else {
                return InvokeResult.failure("批量删除失败，数据可能已经不存在");
            }
        } catch (Exception e) {
            log.error("批量删除预警信息失败", e);
            return InvokeResult.failure("批量删除预警信息失败");
        }
    }

    //getYjXqById
    @ApiOperation(value = "预警信息-获取详情", notes = "预警信息-获取详情")
    @GetMapping("getYjXqById")
    public InvokeResult getYjXqById(@RequestParam String id){
        try {
            BaseMapper<QyYjxxb> baseMapper = qyYjxxbService.getBaseMapper();
            QueryWrapper<QyYjxxb> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",Long.valueOf(id));
            QyYjxxb qyYjxxb = baseMapper.selectOne(queryWrapper);
            return InvokeResult.success("获取预警详情成功！", qyYjxxb);
        } catch (Exception e) {
            return InvokeResult.failure("获取预警详情信息失败！");
        }

    }

    @ApiOperation(value = "境外人员高风险人员列表", notes = "境外人员高风险人员列表")
    @GetMapping("getGfxryList")
    public InvokeResult getGfxryList() {
        try {
            //todo 高风险的表还没设计，先用人员表制造数据
            IPage<QyYjxxb> page = new Page(0, 5);
            Wrapper<QyYjxxb> ryxxWrapper = new QueryWrapper<QyYjxxb>();
            IPage<Map<String, Object>> mapIPage = qyYjxxbService.pageMaps(page, ryxxWrapper);
            return InvokeResult.success("查询境外人员高风险人员列表成功",  mapIPage.getRecords());
        } catch (Exception e) {
            return InvokeResult.failure("查询境外人员高风险人员列表失败");
        }
    }
}
