package com.aisino.frems.modules.system.controller;


import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.aisino.frems.common.query.QueryGenerator;
import org.asframework.base.model.InvokeResult;
import com.aisino.frems.common.constant.CacheConstant;
import com.aisino.frems.modules.system.entity.SysDictItem;
import com.aisino.frems.modules.system.service.ISysDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/sys/dictItem")
@Slf4j
public class SysDictItemController {

	@Autowired
	private ISysDictItemService sysDictItemService;

	/**
	 * @功能：查询字典数据
	 * @param sysDictItem
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public InvokeResult queryPageList(SysDictItem sysDictItem,@RequestParam(name="page.pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="page.pageSize", defaultValue="10") Integer pageSize,HttpServletRequest req) {

		QueryWrapper<SysDictItem> queryWrapper = QueryGenerator.initQueryWrapper(sysDictItem, req.getParameterMap());
		queryWrapper.orderByAsc("sort_order");
		Page<SysDictItem> page = new Page<SysDictItem>(pageNo, pageSize);
		IPage<SysDictItem> pageList = sysDictItemService.page(page, queryWrapper);
		return InvokeResult.success(null, pageList);
	}

	/**
	 * @功能：新增
	 * @param sysDictItem
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@CacheEvict(value= CacheConstant.SYS_DICT_CACHE, allEntries=true)
	public InvokeResult add(@RequestBody SysDictItem sysDictItem) {

		try {
			sysDictItem.setCreateTime(new Date());
			sysDictItemService.save(sysDictItem);
			return InvokeResult.success("保存成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	 * @功能：编辑
	 * @param sysDictItem
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	@CacheEvict(value=CacheConstant.SYS_DICT_CACHE, allEntries=true)
	public InvokeResult edit(@RequestBody SysDictItem sysDictItem) {

		SysDictItem sysdict = sysDictItemService.getById(sysDictItem.getId());
		if(sysdict==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			sysDictItem.setUpdateTime(new Date());
			boolean ok = sysDictItemService.updateById(sysDictItem);
			//TODO 返回false说明什么？
			if(ok) {
				return InvokeResult.success("编辑成功!");
			}
		}
		return InvokeResult.failure("error");
	}

	/**
	 * @功能：删除字典数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@CacheEvict(value=CacheConstant.SYS_DICT_CACHE, allEntries=true)
	public InvokeResult delete(@RequestParam(name="id",required=true) String id) {

		SysDictItem joinSystem = sysDictItemService.getById(id);
		if(joinSystem==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			boolean ok = sysDictItemService.removeById(id);
			if(ok) {
				return InvokeResult.success("删除成功!");
			}
		}
		return InvokeResult.failure("error");
	}

	/**
	 * @功能：批量删除字典数据
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	@CacheEvict(value=CacheConstant.SYS_DICT_CACHE, allEntries=true)
	public InvokeResult deleteBatch(@RequestParam(name="ids",required=true) String ids) {

		if(ids==null || "".equals(ids.trim())) {
			return InvokeResult.failure("500", "参数不识别！");
		}else {
			this.sysDictItemService.removeByIds(Arrays.asList(ids.split(",")));
			return InvokeResult.success("删除成功!");
		}
	}

}
