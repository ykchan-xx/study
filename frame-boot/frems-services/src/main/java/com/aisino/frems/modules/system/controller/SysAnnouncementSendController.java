package com.aisino.frems.modules.system.controller;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.aisino.frems.common.model.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.asframework.base.model.InvokeResult;
import com.aisino.frems.common.constant.CommonConstant;
import com.aisino.frems.common.util.oConvertUtils;
import com.aisino.frems.modules.system.entity.SysAnnouncementSend;
import com.aisino.frems.modules.system.model.AnnouncementSendModel;
import com.aisino.frems.modules.system.service.ISysAnnouncementSendService;
import org.asframework.core.util.LangUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

 /**
 * @Title: Controller
 * @Description: 用户通告阅读标记表
 * @Author: jeecg-boot
 * @Date:  2019-02-21
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sys/sysAnnouncementSend")
@Slf4j
public class SysAnnouncementSendController {
	@Autowired
	private ISysAnnouncementSendService sysAnnouncementSendService;

	/**
	  * 分页列表查询
	 * @param sysAnnouncementSend
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public InvokeResult queryPageList(SysAnnouncementSend sysAnnouncementSend,
									  @RequestParam(name="page.pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="page.pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {

		QueryWrapper<SysAnnouncementSend> queryWrapper = new QueryWrapper<SysAnnouncementSend>(sysAnnouncementSend);
		Page<SysAnnouncementSend> page = new Page<SysAnnouncementSend>(pageNo,pageSize);
		//排序逻辑 处理
		String column = req.getParameter("column");
		String order = req.getParameter("order");
		if(LangUtil.isNotEmpty(column) && LangUtil.isNotEmpty(order)) {
			if("asc".equals(order)) {
				queryWrapper.orderByAsc(oConvertUtils.camelToUnderline(column));
			}else {
				queryWrapper.orderByDesc(oConvertUtils.camelToUnderline(column));
			}
		}
		IPage<SysAnnouncementSend> pageList = sysAnnouncementSendService.page(page, queryWrapper);
		//log.info("查询当前页："+pageList.getCurrent());
		//log.info("查询当前页数量："+pageList.getSize());
		//log.info("查询结果数量："+pageList.getRecords().size());
		//log.info("数据总数："+pageList.getTotal());
		return InvokeResult.success(null, pageList);
	}

	/**
	  *   添加
	 * @param sysAnnouncementSend
	 * @return
	 */
	@PostMapping(value = "/add")
	public InvokeResult add(@RequestBody SysAnnouncementSend sysAnnouncementSend) {
		try {
			sysAnnouncementSendService.save(sysAnnouncementSend);
			return InvokeResult.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	  *  编辑
	 * @param sysAnnouncementSend
	 * @return
	 */
	@PutMapping(value = "/edit")
	public InvokeResult eidt(@RequestBody SysAnnouncementSend sysAnnouncementSend) {

		SysAnnouncementSend sysAnnouncementSendEntity = sysAnnouncementSendService.getById(sysAnnouncementSend.getId());
		if(sysAnnouncementSendEntity==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			boolean ok = sysAnnouncementSendService.updateById(sysAnnouncementSend);
			//TODO 返回false说明什么？
			if(ok) {
				return InvokeResult.success("修改成功!");
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

		SysAnnouncementSend sysAnnouncementSend = sysAnnouncementSendService.getById(id);
		if(sysAnnouncementSend==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			boolean ok = sysAnnouncementSendService.removeById(id);
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
			this.sysAnnouncementSendService.removeByIds(Arrays.asList(ids.split(",")));
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

		SysAnnouncementSend sysAnnouncementSend = sysAnnouncementSendService.getById(id);
		if(sysAnnouncementSend==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			return InvokeResult.success(null, sysAnnouncementSend);
		}
	}

	/**
	 * @功能：更新用户系统消息阅读状态
	 * @param json
	 * @return
	 */
	@PutMapping(value = "/editByAnntIdAndUserId")
	public InvokeResult editById(@RequestBody JSONObject json) {
		String anntId = json.getString("anntId");
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		String userId = sysUser.getId();
		LambdaUpdateWrapper<SysAnnouncementSend> updateWrapper = new UpdateWrapper().lambda();
		updateWrapper.set(SysAnnouncementSend::getReadFlag, CommonConstant.HAS_READ_FLAG);
		updateWrapper.set(SysAnnouncementSend::getReadTime, new Date());
		updateWrapper.last("where annt_id ='"+anntId+"' and user_id ='"+userId+"'");
		SysAnnouncementSend announcementSend = new SysAnnouncementSend();
		sysAnnouncementSendService.update(announcementSend, updateWrapper);
		return InvokeResult.success();
	}

	/**
	 * @功能：获取我的消息
	 * @return
	 */
	@GetMapping(value = "/getMyAnnouncementSend")
	public InvokeResult getMyAnnouncementSend(AnnouncementSendModel announcementSendModel,
			@RequestParam(name="page.pageNo", defaultValue="1") Integer pageNo,
			  @RequestParam(name="page.pageSize", defaultValue="10") Integer pageSize) {
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		String userId = sysUser.getId();
		announcementSendModel.setUserId(userId);
		announcementSendModel.setPageNo((pageNo-1)*pageSize);
		announcementSendModel.setPageSize(pageSize);
		Page<AnnouncementSendModel> pageList = new Page<AnnouncementSendModel>(pageNo,pageSize);
		pageList = sysAnnouncementSendService.getMyAnnouncementSendPage(pageList, announcementSendModel);
		return InvokeResult.success(null, pageList);
	}

	/**
	 * @功能：一键已读
	 * @return
	 */
	@PutMapping(value = "/readAll")
	public InvokeResult readAll() {
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		String userId = sysUser.getId();
		LambdaUpdateWrapper<SysAnnouncementSend> updateWrapper = new UpdateWrapper().lambda();
		updateWrapper.set(SysAnnouncementSend::getReadFlag, CommonConstant.HAS_READ_FLAG);
		updateWrapper.set(SysAnnouncementSend::getReadTime, new Date());
		updateWrapper.last("where user_id ='"+userId+"'");
		SysAnnouncementSend announcementSend = new SysAnnouncementSend();
		sysAnnouncementSendService.update(announcementSend, updateWrapper);
		return InvokeResult.success("全部已读");
	}
}
