package com.aisino.frems.modules.system.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.aisino.frems.common.query.QueryGenerator;
import org.asframework.base.model.InvokeResult;
import com.aisino.frems.modules.system.entity.SysUserAgent;
import com.aisino.frems.modules.system.service.ISysUserAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

 /**
 * @Title: Controller
 * @Description: 用户代理人设置
 * @Author: jeecg-boot
 * @Date:  2019-04-17
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sys/sysUserAgent")
@Slf4j
public class SysUserAgentController {
	@Autowired
	private ISysUserAgentService sysUserAgentService;

	/**
	  * 分页列表查询
	 * @param sysUserAgent
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public InvokeResult queryPageList(SysUserAgent sysUserAgent,
									  @RequestParam(name="page.pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="page.pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		QueryWrapper<SysUserAgent> queryWrapper = QueryGenerator.initQueryWrapper(sysUserAgent, req.getParameterMap());
		Page<SysUserAgent> page = new Page<SysUserAgent>(pageNo, pageSize);
		IPage<SysUserAgent> pageList = sysUserAgentService.page(page, queryWrapper);
		return InvokeResult.success(null, pageList);
	}

	/**
	  *   添加
	 * @param sysUserAgent
	 * @return
	 */
	@PostMapping(value = "/add")
	public InvokeResult add(@RequestBody SysUserAgent sysUserAgent) {
		try {
			sysUserAgentService.save(sysUserAgent);
			return InvokeResult.success("代理人设置成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	  *  编辑
	 * @param sysUserAgent
	 * @return
	 */
	@PutMapping(value = "/edit")
	public InvokeResult edit(@RequestBody SysUserAgent sysUserAgent) {
		SysUserAgent sysUserAgentEntity = sysUserAgentService.getById(sysUserAgent.getId());
		if(sysUserAgentEntity==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			boolean ok = sysUserAgentService.updateById(sysUserAgent);
			//TODO 返回false说明什么？
			if(ok) {
				return InvokeResult.success("代理人设置成功!");
			}
		}
		return InvokeResult.failure("error");
	}

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public InvokeResult delete(@RequestParam(name="id",required=true) String id) {

		SysUserAgent sysUserAgent = sysUserAgentService.getById(id);
		if(sysUserAgent==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			boolean ok = sysUserAgentService.removeById(id);
			if(ok) {
				return InvokeResult.success("删除成功!");
			}
		}
		return InvokeResult.failure("error");
	}

	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public InvokeResult deleteBatch(@RequestParam(name="ids",required=true) String ids) {

		if(ids==null || "".equals(ids.trim())) {
			return InvokeResult.failure("500", "参数不识别！");
		}else {
			this.sysUserAgentService.removeByIds(Arrays.asList(ids.split(",")));
			return InvokeResult.success("删除成功!");
		}
	}

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public InvokeResult queryById(@RequestParam(name="id",required=true) String id) {

		SysUserAgent sysUserAgent = sysUserAgentService.getById(id);
		if(sysUserAgent==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			return InvokeResult.success(null, sysUserAgent);
		}
	}

	/**
	  * 通过userName查询
	 * @param userName
	 * @return
	 */
	@GetMapping(value = "/queryByUserName")
	public InvokeResult queryByUserName(@RequestParam(name="userName",required=true) String userName) {

		LambdaQueryWrapper<SysUserAgent> queryWrapper = new LambdaQueryWrapper<SysUserAgent>();
		queryWrapper.eq(SysUserAgent::getUserName, userName);
		SysUserAgent sysUserAgent = sysUserAgentService.getOne(queryWrapper);
		if(sysUserAgent==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			return InvokeResult.success(null, sysUserAgent);
		}
	}

}
