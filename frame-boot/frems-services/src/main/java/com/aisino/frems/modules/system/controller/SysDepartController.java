package com.aisino.frems.modules.system.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.aisino.frems.modules.system.util.JwtUtil;
import org.asframework.base.model.InvokeResult;
import com.aisino.frems.common.constant.CacheConstant;
import com.aisino.frems.modules.system.entity.SysDepart;
import com.aisino.frems.modules.system.model.DepartIdModel;
import com.aisino.frems.modules.system.model.SysDepartTreeModel;
import com.aisino.frems.modules.system.service.ISysDepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 部门表 前端控制器
 * <p>
 *
 * @Author: Steve @Since： 2019-01-22
 */
@RestController
@RequestMapping("/sys/sysDepart")
@Slf4j
public class SysDepartController {

	@Autowired
	private ISysDepartService sysDepartService;

	/**
	 * 查询数据 查出所有部门,并以树结构数据格式响应给前端
	 *
	 * @return
	 */
	@RequestMapping(value = "/queryTreeList", method = RequestMethod.GET)
	public InvokeResult queryTreeList() {
		try {
			// 从内存中读取
//			List<SysDepartTreeModel> list =FindsDepartsChildrenUtil.getSysDepartTreeList();
//			if (CollectionUtils.isEmpty(list)) {
//				list = sysDepartService.queryTreeList();
//			}
			List<SysDepartTreeModel> list = sysDepartService.queryTreeList();
			return InvokeResult.success(null, list);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return InvokeResult.failure("error");
	}

	/**
	 * 添加新数据 添加用户新建的部门对象数据,并保存到数据库
	 *
	 * @param sysDepart
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@CacheEvict(value= {CacheConstant.SYS_DEPARTS_CACHE,CacheConstant.SYS_DEPART_IDS_CACHE}, allEntries=true)
	public InvokeResult add(@RequestBody SysDepart sysDepart, HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		try {
			sysDepart.setCreateBy(username);
			sysDepartService.saveDepartData(sysDepart, username);
			//清除部门树内存
			// FindsDepartsChildrenUtil.clearSysDepartTreeList();
			// FindsDepartsChildrenUtil.clearDepartIdModel();
			return InvokeResult.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	 * 编辑数据 编辑部门的部分数据,并保存到数据库
	 *
	 * @param sysDepart
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	@CacheEvict(value= {CacheConstant.SYS_DEPARTS_CACHE,CacheConstant.SYS_DEPART_IDS_CACHE}, allEntries=true)
	public InvokeResult edit(@RequestBody SysDepart sysDepart, HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		sysDepart.setUpdateBy(username);

		SysDepart sysDepartEntity = sysDepartService.getById(sysDepart.getId());
		if (sysDepartEntity == null) {
			return InvokeResult.failure("500", "未找到对应实体");
		} else {
			boolean ok = sysDepartService.updateDepartDataById(sysDepart, username);
			// TODO 返回false说明什么？
			if (ok) {
				//清除部门树内存
				//FindsDepartsChildrenUtil.clearSysDepartTreeList();
				//FindsDepartsChildrenUtil.clearDepartIdModel();
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
	@CacheEvict(value= {CacheConstant.SYS_DEPARTS_CACHE,CacheConstant.SYS_DEPART_IDS_CACHE}, allEntries=true)
   public InvokeResult delete(@RequestParam(name="id",required=true) String id) {
       SysDepart sysDepart = sysDepartService.getById(id);
       if(sysDepart==null) {
           return InvokeResult.failure("500", "未找到对应实体");
       }else {
           boolean ok = sysDepartService.delete(id);
           if(ok) {
	            //清除部门树内存
	   		   //FindsDepartsChildrenUtil.clearSysDepartTreeList();
	   		   // FindsDepartsChildrenUtil.clearDepartIdModel();
               return InvokeResult.success("删除成功!");
           }
       }
		return InvokeResult.failure("error");
   }


	/**
	 * 批量删除 根据前端请求的多个ID,对数据库执行删除相关部门数据的操作
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	@CacheEvict(value= {CacheConstant.SYS_DEPARTS_CACHE,CacheConstant.SYS_DEPART_IDS_CACHE}, allEntries=true)
	public InvokeResult deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		if (ids == null || "".equals(ids.trim())) {
			return InvokeResult.failure("500", "参数不识别！");
		} else {
			this.sysDepartService.deleteBatchWithChildren(Arrays.asList(ids.split(",")));
			return InvokeResult.success("删除成功!");
		}
	}

	/**
	 * 查询数据 添加或编辑页面对该方法发起请求,以树结构形式加载所有部门的名称,方便用户的操作
	 *
	 * @return
	 */
	@RequestMapping(value = "/queryIdTree", method = RequestMethod.GET)
	public InvokeResult queryIdTree() {
//
//		List<DepartIdModel> idList;
//		try {
//			idList = FindsDepartsChildrenUtil.wrapDepartIdModel();
//			if (idList != null && idList.size() > 0) {
//				result.setResult(idList);
//				result.setSuccess(true);
//			} else {
//				sysDepartService.queryTreeList();
//				idList = FindsDepartsChildrenUtil.wrapDepartIdModel();
//				result.setResult(idList);
//				result.setSuccess(true);
//			}
//			return result;
//		} catch (Exception e) {
//			log.error(e.getMessage(),e);
//			result.setSuccess(false);
//			return result;
//		}

		try {
			List<DepartIdModel> list = sysDepartService.queryDepartIdTreeList();
			return InvokeResult.success(null, list);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return InvokeResult.failure("error");
	}

	/**
	 * <p>
	 * 部门搜索功能方法,根据关键字模糊搜索相关部门
	 * </p>
	 *
	 * @param keyWord
	 * @return
	 */
	@RequestMapping(value = "/searchBy", method = RequestMethod.GET)
	public InvokeResult searchBy(@RequestParam(name = "keyWord", required = true) String keyWord) {

		try {
			List<SysDepartTreeModel> treeList = this.sysDepartService.searhBy(keyWord);
			if (treeList.size() == 0 || treeList == null) {
				throw new Exception();
			}
			return InvokeResult.success(null, treeList);
		} catch (Exception e) {
			e.fillInStackTrace();
			return InvokeResult.failure("500","查询失败或没有您想要的任何数据!");
		}
	}
}
