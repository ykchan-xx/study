package com.aisino.frems.modules.system.controller;


import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.aisino.frems.common.query.QueryGenerator;
import org.asframework.base.model.InvokeResult;
import com.aisino.frems.modules.system.entity.SysLog;
import com.aisino.frems.modules.system.service.ISysLogService;
import org.asframework.core.util.LangUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 系统日志表 前端控制器
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-26
 */
@RestController
@RequestMapping("/sys/log")
@Slf4j
public class SysLogController {

	@Autowired
	private ISysLogService sysLogService;

	/**
	 * @功能：查询日志记录
	 * @param syslog
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public InvokeResult queryPageList(SysLog syslog,@RequestParam(name="page.pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="page.pageSize", defaultValue="10") Integer pageSize,HttpServletRequest req) {

		QueryWrapper<SysLog> queryWrapper = QueryGenerator.initQueryWrapper(syslog, req.getParameterMap());
		Page<SysLog> page = new Page<SysLog>(pageNo, pageSize);
		//日志关键词
		String keyWord = req.getParameter("keyWord");
		if(LangUtil.isNotEmpty(keyWord)) {
			queryWrapper.like("log_content",keyWord);
		}
		//TODO 过滤逻辑处理
		//TODO begin、end逻辑处理
		//TODO 一个强大的功能，前端传一个字段字符串，后台只返回这些字符串对应的字段
		//创建时间/创建人的赋值
		IPage<SysLog> pageList = sysLogService.page(page, queryWrapper);
		log.info("查询当前页："+pageList.getCurrent());
		log.info("查询当前页数量："+pageList.getSize());
		log.info("查询结果数量："+pageList.getRecords().size());
		log.info("数据总数："+pageList.getTotal());
		return InvokeResult.success(null, pageList);
	}

	/**
	 * @功能：删除单个日志记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public InvokeResult delete(@RequestParam(name="id",required=true) String id) {
		SysLog sysLog = sysLogService.getById(id);
		if(sysLog==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			boolean ok = sysLogService.removeById(id);
			if(ok) {
				return InvokeResult.success("删除成功!");
			}
		}
		return InvokeResult.failure("error");
	}

	/**
	 * @功能：批量，全部清空日志记录
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public InvokeResult deleteBatch(@RequestParam(name="ids",required=true) String ids) {

		if(ids==null || "".equals(ids.trim())) {
			return InvokeResult.failure("500", "参数不识别！");
		}else {
			if("allclear".equals(ids)) {
				this.sysLogService.removeAll();
				return InvokeResult.success("清除成功!");
			}
			this.sysLogService.removeByIds(Arrays.asList(ids.split(",")));
			return InvokeResult.success("删除成功!");
		}
	}
}
