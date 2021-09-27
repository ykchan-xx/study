package com.aisino.frems.modules.system.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.aisino.frems.common.constant.CommonSendStatus;
import com.aisino.frems.common.model.LoginUser;
import com.aisino.frems.modules.system.util.JwtUtil;
import com.aisino.frems.modules.system.websocket.WebSocket;
import org.apache.shiro.SecurityUtils;
import org.asframework.base.model.InvokeResult;
import com.aisino.frems.common.constant.CommonConstant;
import com.aisino.frems.common.util.oConvertUtils;
import com.aisino.frems.modules.system.entity.SysAnnouncement;
import com.aisino.frems.modules.system.entity.SysAnnouncementSend;
import com.aisino.frems.modules.system.service.ISysAnnouncementSendService;
import com.aisino.frems.modules.system.service.ISysAnnouncementService;
import org.asframework.core.util.LangUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: Controller
 * @Description: 系统通告表
 * @Author: jeecg-boot
 * @Date: 2019-01-02
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sys/annountCement")
@Slf4j
public class SysAnnouncementController {
	@Autowired
	private ISysAnnouncementService sysAnnouncementService;
	@Autowired
	private ISysAnnouncementSendService sysAnnouncementSendService;
	@Resource
    private WebSocket webSocket;

	/**
	  * 分页列表查询
	 * @param sysAnnouncement
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public InvokeResult queryPageList(SysAnnouncement sysAnnouncement,
									  @RequestParam(name="page.pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="page.pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {

		sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_0.toString());
		QueryWrapper<SysAnnouncement> queryWrapper = new QueryWrapper<SysAnnouncement>(sysAnnouncement);
		Page<SysAnnouncement> page = new Page<SysAnnouncement>(pageNo,pageSize);
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
		IPage<SysAnnouncement> pageList = sysAnnouncementService.page(page, queryWrapper);
		log.info("查询当前页："+pageList.getCurrent());
		log.info("查询当前页数量："+pageList.getSize());
		log.info("查询结果数量："+pageList.getRecords().size());
		log.info("数据总数："+pageList.getTotal());
		return InvokeResult.success(null, pageList);
	}

	/**
	  *   添加
	 * @param sysAnnouncement
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public InvokeResult add(@RequestBody SysAnnouncement sysAnnouncement) {

		try {
			sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_0.toString());
			sysAnnouncement.setSendStatus(CommonSendStatus.UNPUBLISHED_STATUS_0);//未发布
			sysAnnouncementService.saveAnnouncement(sysAnnouncement);
			return InvokeResult.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	  *  编辑
	 * @param sysAnnouncement
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public InvokeResult eidt(@RequestBody SysAnnouncement sysAnnouncement) {

		SysAnnouncement sysAnnouncementEntity = sysAnnouncementService.getById(sysAnnouncement.getId());
		if(sysAnnouncementEntity==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			boolean ok = sysAnnouncementService.upDateAnnouncement(sysAnnouncement);
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
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public InvokeResult delete(@RequestParam(name="id",required=true) String id) {

		SysAnnouncement sysAnnouncement = sysAnnouncementService.getById(id);
		if(sysAnnouncement==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_1.toString());
			boolean ok = sysAnnouncementService.updateById(sysAnnouncement);
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
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public InvokeResult deleteBatch(@RequestParam(name="ids",required=true) String ids) {

		if(ids==null || "".equals(ids.trim())) {
			return InvokeResult.failure("500", "参数不识别！");
		}else {
			String[] id = ids.split(",");
			for(int i=0;i<id.length;i++) {
				SysAnnouncement announcement = sysAnnouncementService.getById(id[i]);
				announcement.setDelFlag(CommonConstant.DEL_FLAG_1.toString());
				sysAnnouncementService.updateById(announcement);
			}
			return InvokeResult.success("删除成功!");
		}
	}

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public InvokeResult queryById(@RequestParam(name="id",required=true) String id) {

		SysAnnouncement sysAnnouncement = sysAnnouncementService.getById(id);
		if(sysAnnouncement==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			return InvokeResult.success(null, sysAnnouncement);
		}
	}

	/**
	 *	 更新发布操作
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/doReleaseData", method = RequestMethod.GET)
	public InvokeResult doReleaseData(@RequestParam(name="id",required=true) String id, HttpServletRequest request) {

		SysAnnouncement sysAnnouncement = sysAnnouncementService.getById(id);
		if(sysAnnouncement==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			sysAnnouncement.setSendStatus(CommonSendStatus.PUBLISHED_STATUS_1);//发布中
			sysAnnouncement.setSendTime(new Date());
			String currentUserName = JwtUtil.getUserNameByToken(request);
			sysAnnouncement.setSender(currentUserName);
			boolean ok = sysAnnouncementService.updateById(sysAnnouncement);
			if(ok) {
				if(sysAnnouncement.getMsgType().equals(CommonConstant.MSG_TYPE_ALL)) {
					JSONObject obj = new JSONObject();
			    	obj.put("cmd", "topic");
					obj.put("msgId", sysAnnouncement.getId());
					obj.put("msgTxt", sysAnnouncement.getTitile());
			    	webSocket.sendAllMessage(obj.toJSONString());
				}else {
					// 2.插入用户通告阅读标记表记录
					String userId = sysAnnouncement.getUserIds();
					String[] userIds = userId.substring(0, (userId.length()-1)).split(",");
					String anntId = sysAnnouncement.getId();
					Date refDate = new Date();
					JSONObject obj = new JSONObject();
			    	obj.put("cmd", "user");
					obj.put("msgId", sysAnnouncement.getId());
					obj.put("msgTxt", sysAnnouncement.getTitile());
			    	webSocket.sendMoreMessage(userIds, obj.toJSONString());
				}
				return InvokeResult.success("该系统通知发布成功");
			}
		}
		return InvokeResult.failure("error");
	}

	/**
	 *	 更新撤销操作
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/doReovkeData", method = RequestMethod.GET)
	public InvokeResult doReovkeData(@RequestParam(name="id",required=true) String id, HttpServletRequest request) {

		SysAnnouncement sysAnnouncement = sysAnnouncementService.getById(id);
		if(sysAnnouncement==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			sysAnnouncement.setSendStatus(CommonSendStatus.REVOKE_STATUS_2);//撤销发布
			sysAnnouncement.setCancelTime(new Date());
			boolean ok = sysAnnouncementService.updateById(sysAnnouncement);
			if(ok) {
				return InvokeResult.success("该系统通知撤销成功");
			}
		}
		return InvokeResult.failure("error");
	}

	/**
	 * @功能：补充用户数据，并返回系统消息
	 * @return
	 */
	@RequestMapping(value = "/listByUser", method = RequestMethod.GET)
	public InvokeResult listByUser() {

		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		String userId = sysUser.getId();
		// 1.将系统消息补充到用户通告阅读标记表中
		Collection<String> anntIds = sysAnnouncementSendService.queryByUserId(userId);
		LambdaQueryWrapper<SysAnnouncement> querySaWrapper = new LambdaQueryWrapper<SysAnnouncement>();
		querySaWrapper.eq(SysAnnouncement::getMsgType,CommonConstant.MSG_TYPE_ALL); // 全部人员
		querySaWrapper.eq(SysAnnouncement::getDelFlag,CommonConstant.DEL_FLAG_0.toString());  // 未删除
		querySaWrapper.eq(SysAnnouncement::getSendStatus, CommonConstant.HAS_SEND); //已发布
		querySaWrapper.ge(SysAnnouncement::getEndTime, sysUser.getCreateTime()); //新注册用户不看结束通知
		if(anntIds!=null&&anntIds.size()>0) {
			querySaWrapper.notIn(SysAnnouncement::getId, anntIds);
		}
		List<SysAnnouncement> announcements = sysAnnouncementService.list(querySaWrapper);
		if(announcements.size()>0) {
			for(int i=0;i<announcements.size();i++) {
				SysAnnouncementSend announcementSend = new SysAnnouncementSend();
				announcementSend.setAnntId(announcements.get(i).getId());
				announcementSend.setUserId(userId);
				announcementSend.setReadFlag(CommonConstant.NO_READ_FLAG);
				sysAnnouncementSendService.save(announcementSend);
			}
		}
		// 2.查询用户未读的系统消息
		Page<SysAnnouncement> anntMsgList = new Page<SysAnnouncement>(0,5);
		anntMsgList = sysAnnouncementService.querySysCementPageByUserId(anntMsgList,userId,"1");//通知公告消息
		Page<SysAnnouncement> sysMsgList = new Page<SysAnnouncement>(0,5);
		sysMsgList = sysAnnouncementService.querySysCementPageByUserId(sysMsgList,userId,"2");//系统消息
		Map<String,Object> sysMsgMap = new HashMap<String, Object>();
		sysMsgMap.put("sysMsgList", sysMsgList.getRecords());
		sysMsgMap.put("sysMsgTotal", sysMsgList.getTotal());
		sysMsgMap.put("anntMsgList", anntMsgList.getRecords());
		sysMsgMap.put("anntMsgTotal", anntMsgList.getTotal());
		return InvokeResult.success(null, sysMsgMap);
	}
}
