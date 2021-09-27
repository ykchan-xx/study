package com.aisino.frems.modules.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aisino.frems.common.model.DictModel;
import com.aisino.frems.common.query.QueryGenerator;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.asframework.base.model.InvokeResult;
import com.aisino.frems.modules.system.entity.SysCategory;
import com.aisino.frems.modules.system.model.TreeSelectModel;
import com.aisino.frems.modules.system.service.ISysCategoryService;
import org.asframework.core.util.LangUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

 /**
 * @Description: 分类字典
 * @Author: jeecg-boot
 * @Date:   2019-05-29
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sys/category")
@Slf4j
public class SysCategoryController {
	@Autowired
	private ISysCategoryService sysCategoryService;

	/**
	  * 分页列表查询
	 * @param sysCategory
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/rootList")
	public InvokeResult queryPageList(SysCategory sysCategory,
									  @RequestParam(name="page.pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="page.pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		if(LangUtil.isEmpty(sysCategory.getPid())){
			sysCategory.setPid("0");
		}


		//--author:os_chengtgen---date:20190804 -----for: 分类字典页面显示错误,issues:377--------start
		//QueryWrapper<SysCategory> queryWrapper = QueryGenerator.initQueryWrapper(sysCategory, req.getParameterMap());
		QueryWrapper<SysCategory> queryWrapper = new QueryWrapper<SysCategory>();
		queryWrapper.eq("pid", sysCategory.getPid());
		//--author:os_chengtgen---date:20190804 -----for: 分类字典页面显示错误,issues:377--------end

		Page<SysCategory> page = new Page<SysCategory>(pageNo, pageSize);
		IPage<SysCategory> pageList = sysCategoryService.page(page, queryWrapper);
		return InvokeResult.success(null, pageList);
	}

	@GetMapping(value = "/childList")
	public InvokeResult queryPageList(SysCategory sysCategory,HttpServletRequest req) {

		QueryWrapper<SysCategory> queryWrapper = QueryGenerator.initQueryWrapper(sysCategory, req.getParameterMap());
		List<SysCategory> list = sysCategoryService.list(queryWrapper);
		return InvokeResult.success(null, list);
	}


	/**
	  *   添加
	 * @param sysCategory
	 * @return
	 */
	@PostMapping(value = "/add")
	public InvokeResult add(@RequestBody SysCategory sysCategory) {
		try {
			sysCategoryService.addSysCategory(sysCategory);
			return InvokeResult.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	  *  编辑
	 * @param sysCategory
	 * @return
	 */
	@PutMapping(value = "/edit")
	public InvokeResult edit(@RequestBody SysCategory sysCategory) {

		SysCategory sysCategoryEntity = sysCategoryService.getById(sysCategory.getId());
		if(sysCategoryEntity==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			sysCategoryService.updateSysCategory(sysCategory);
			return InvokeResult.success("修改成功!");
		}
	}

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public InvokeResult delete(@RequestParam(name="id",required=true) String id) {
		SysCategory sysCategory = sysCategoryService.getById(id);
		if(sysCategory==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			boolean ok = sysCategoryService.removeById(id);
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
			this.sysCategoryService.removeByIds(Arrays.asList(ids.split(",")));
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

		SysCategory sysCategory = sysCategoryService.getById(id);
		if(sysCategory==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			return InvokeResult.success(null, sysCategory);
		}
	}


  /**
     * 加载单个数据 用于回显
   */
    @RequestMapping(value = "/loadOne", method = RequestMethod.GET)
 	public InvokeResult loadOne(@RequestParam(name="field") String field,@RequestParam(name="val") String val) {

 		try {

 			QueryWrapper<SysCategory> query = new QueryWrapper<SysCategory>();
 			query.eq(field, val);
 			List<SysCategory> ls = this.sysCategoryService.list(query);
 			if(ls==null || ls.size()==0) {
				return InvokeResult.failure("500", "查询无果");
 			}else if(ls.size()>1) {
				return InvokeResult.failure("500", "查询数据异常,["+field+"]存在多个值:"+val);
 			}else {
				return InvokeResult.success(null, ls.get(0));
 			}
 		} catch (Exception e) {
 			e.printStackTrace();
			return InvokeResult.failure("500", e.getMessage());
 		}
 	}

    /**
          * 加载节点的子数据
     */
    @RequestMapping(value = "/loadTreeChildren", method = RequestMethod.GET)
	public InvokeResult loadTreeChildren(@RequestParam(name="pid") String pid) {

		try {
			List<TreeSelectModel> ls = this.sysCategoryService.queryListByPid(pid);
			return InvokeResult.success(null, ls);
		} catch (Exception e) {
			e.printStackTrace();
			return InvokeResult.failure("500", e.getMessage());
		}
	}

    /**
         * 加载一级节点/如果是同步 则所有数据
     */
    @RequestMapping(value = "/loadTreeRoot", method = RequestMethod.GET)
   	public InvokeResult loadTreeRoot(@RequestParam(name="async") Boolean async,@RequestParam(name="pcode") String pcode) {
   		try {
   			List<TreeSelectModel> ls = this.sysCategoryService.queryListByCode(pcode);
   			if(!async) {
   				loadAllCategoryChildren(ls);
   			}
			return InvokeResult.success(null, ls);
   		} catch (Exception e) {
   			e.printStackTrace();
			return InvokeResult.failure("500", e.getMessage());
   		}
   	}

    /**
         * 递归求子节点 同步加载用到
     */
  	private void loadAllCategoryChildren(List<TreeSelectModel> ls) {
  		for (TreeSelectModel tsm : ls) {
			List<TreeSelectModel> temp = this.sysCategoryService.queryListByPid(tsm.getKey());
			if(temp!=null && temp.size()>0) {
				tsm.setChildren(temp);
				loadAllCategoryChildren(temp);
			}
		}
  	}

	 /**
	  * 校验编码
	  * @param pid
	  * @param code
	  * @return
	  */
	 @GetMapping(value = "/checkCode")
	 public InvokeResult checkCode(@RequestParam(name="pid",required = false) String pid,@RequestParam(name="code",required = false) String code) {
		if(LangUtil.isEmpty(code)){
			return InvokeResult.failure("错误,类型编码为空!");
		}
		if(LangUtil.isEmpty(pid)){
			return InvokeResult.success();
		}
		SysCategory parent = this.sysCategoryService.getById(pid);
		if(code.startsWith(parent.getCode())){
			return InvokeResult.success();
		}else{
			return InvokeResult.failure("编码不符合规范,须以\""+parent.getCode()+"\"开头!");
		}

	 }


	 /**
	  * 分类字典树控件 加载节点
	  * @param pid
	  * @param pcode
	  * @param condition
	  * @return
	  */
	 @RequestMapping(value = "/loadTreeData", method = RequestMethod.GET)
	 public InvokeResult loadDict(@RequestParam(name="pid",required = false) String pid,@RequestParam(name="pcode",required = false) String pcode, @RequestParam(name="condition",required = false) String condition) {
		 //pid如果传值了 就忽略pcode的作用
		 if(LangUtil.isEmpty(pid)){
		 	if(LangUtil.isEmpty(pcode)){
				return InvokeResult.failure("500", "加载分类字典树参数有误.[null]!");
			}else{
		 		if(sysCategoryService.ROOT_PID_VALUE.equals(pcode)){
					pid = sysCategoryService.ROOT_PID_VALUE;
				}else{
					pid = this.sysCategoryService.queryIdByCode(pcode);
				}
				if(LangUtil.isEmpty(pid)){
					return InvokeResult.failure("500", "加载分类字典树参数有误.[code]!");
				}
			}
		 }
		 Map<String, String> query = null;
		 if(LangUtil.isNotEmpty(condition)) {
			 query = JSON.parseObject(condition, Map.class);
		 }
		 List<TreeSelectModel> ls = sysCategoryService.queryListByPid(pid,query);
		 return InvokeResult.success(null, ls);
	 }

	 /**
	  * 分类字典控件数据回显[表单页面]
	  * @param ids
	  * @return
	  */
	 @RequestMapping(value = "/loadDictItem", method = RequestMethod.GET)
	 public InvokeResult loadDictItem(@RequestParam(name="ids") String ids) {
		 LambdaQueryWrapper<SysCategory> query = new LambdaQueryWrapper<SysCategory>().in(SysCategory::getId,ids);
		 List<SysCategory> list = this.sysCategoryService.list(query);
		 List<String> textList = new ArrayList<String>();
		 for (String id : ids.split(",")) {
			 for (SysCategory c : list) {
				if(id.equals(c.getId())){
					textList.add(c.getName());
					break;
				}
			 }
		 }
		 return InvokeResult.success(null, textList);
	 }


	 /**
	  * [列表页面]加载分类字典数据 用于值的替换
	  * @param code
	  * @return
	  */
	 @RequestMapping(value = "/loadAllData", method = RequestMethod.GET)
	 public InvokeResult loadAllData(@RequestParam(name="code",required = true) String code) {
		 LambdaQueryWrapper<SysCategory> query = new LambdaQueryWrapper<SysCategory>();
		 if(LangUtil.isNotEmpty(code) && !"0".equals(code)){
			 query.likeRight(SysCategory::getCode,code);
		 }
		 List<SysCategory> list = this.sysCategoryService.list(query);
		 if(list==null || list.size()==0) {
			 return InvokeResult.failure("500", "无数据,参数有误.[code]");
		 }
		 List<DictModel> rdList = new ArrayList<DictModel>();
		 for (SysCategory c : list) {
			 rdList.add(new DictModel(c.getId(),c.getName()));
		 }
		 return InvokeResult.success(null, rdList);
	 }




}
