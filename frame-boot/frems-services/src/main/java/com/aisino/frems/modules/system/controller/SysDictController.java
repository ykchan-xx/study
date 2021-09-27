package com.aisino.frems.modules.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aisino.frems.common.model.DictModel;
import com.aisino.frems.common.query.QueryGenerator;
import org.asframework.base.model.InvokeResult;
import com.aisino.frems.common.constant.CacheConstant;
import com.aisino.frems.common.constant.CommonConstant;
import com.aisino.frems.common.util.SqlInjectionUtil;
import com.aisino.frems.modules.system.entity.SysDict;
import com.aisino.frems.modules.system.model.SysDictTree;
import com.aisino.frems.modules.system.model.TreeSelectModel;
import com.aisino.frems.modules.system.service.ISysDictItemService;
import com.aisino.frems.modules.system.service.ISysDictService;
import org.asframework.core.util.LangUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/sys/dict")
@Slf4j
public class SysDictController {

	@Autowired
	private ISysDictService sysDictService;

	@Autowired
	private ISysDictItemService sysDictItemService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public InvokeResult queryPageList(SysDict sysDict,@RequestParam(name="page.pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="page.pageSize", defaultValue="10") Integer pageSize,HttpServletRequest req) {
		QueryWrapper<SysDict> queryWrapper = QueryGenerator.initQueryWrapper(sysDict, req.getParameterMap());
		Page<SysDict> page = new Page<SysDict>(pageNo, pageSize);
		IPage<SysDict> pageList = sysDictService.page(page, queryWrapper);
		log.debug("查询当前页："+pageList.getCurrent());
		log.debug("查询当前页数量："+pageList.getSize());
		log.debug("查询结果数量："+pageList.getRecords().size());
		log.debug("数据总数："+pageList.getTotal());
		return InvokeResult.success(null, pageList);
	}

	/**
	 * @功能：获取树形字典数据
	 * @param sysDict
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/treeList", method = RequestMethod.GET)
	public InvokeResult treeList(SysDict sysDict,@RequestParam(name="page.pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="page.pageSize", defaultValue="10") Integer pageSize,HttpServletRequest req) {

		LambdaQueryWrapper<SysDict> query = new LambdaQueryWrapper<>();
		// 构造查询条件
		String dictName = sysDict.getDictName();
		if(LangUtil.isNotEmpty(dictName)) {
			query.like(true, SysDict::getDictName, dictName);
		}
		query.orderByDesc(true, SysDict::getCreateTime);
		List<SysDict> list = sysDictService.list(query);
		List<SysDictTree> treeList = new ArrayList<>();
		for (SysDict node : list) {
			treeList.add(new SysDictTree(node));
		}
		return InvokeResult.success(null, treeList);
	}

	/**
	 * 获取字典数据
	 * @param dictCode 字典code
	 * @param dictCode 表名,文本字段,code字段  | 举例：sys_user,realname,id
	 * @return
	 */
	@RequestMapping(value = "/getDictItems/{dictCode}", method = RequestMethod.GET)
	public InvokeResult getDictItems(@PathVariable String dictCode) {
		log.info(" dictCode : "+ dictCode);

		List<DictModel> ls = null;
		try {
			if(dictCode.indexOf(",")!=-1) {
				//关联表字典（举例：sys_user,realname,id）
				String[] params = dictCode.split(",");

				if(params.length<3) {
					return InvokeResult.failure("500", "字典Code格式不正确！");
				}
				//SQL注入校验（只限制非法串改数据库）
				final String[] sqlInjCheck = {params[0],params[1],params[2]};
				SqlInjectionUtil.filterContent(sqlInjCheck);

				if(params.length==4) {
					//SQL注入校验（查询条件SQL 特殊check，此方法仅供此处使用）
					SqlInjectionUtil.specialFilterContent(params[3]);
					ls = sysDictService.queryTableDictItemsByCodeAndFilter(params[0],params[1],params[2],params[3]);
				}else if (params.length==3) {
					ls = sysDictService.queryTableDictItemsByCode(params[0],params[1],params[2]);
				}else{
					return InvokeResult.failure("500", "字典Code格式不正确！");
				}
			}else {
				//字典表
				 ls = sysDictService.queryDictItemsByCode(dictCode);
			}

			return InvokeResult.success(null, ls);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	 * 获取字典数据
	 * @param dictCode
	 * @return
	 */
	@RequestMapping(value = "/getDictText/{dictCode}/{key}", method = RequestMethod.GET)
	public InvokeResult getDictItems(@PathVariable("dictCode") String dictCode, @PathVariable("key") String key) {
		log.info(" dictCode : "+ dictCode);

		String text = null;
		try {
			text = sysDictService.queryDictTextByKey(dictCode, key);
			return InvokeResult.success(null, text);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	 * @功能：新增
	 * @param sysDict
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public InvokeResult add(@RequestBody SysDict sysDict) {

		try {
			sysDict.setCreateTime(new Date());
			sysDict.setDelFlag(CommonConstant.DEL_FLAG_0);
			sysDictService.save(sysDict);
			return InvokeResult.success("保存成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	 * @功能：编辑
	 * @param sysDict
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public InvokeResult edit(@RequestBody SysDict sysDict) {
		SysDict sysdict = sysDictService.getById(sysDict.getId());
		if(sysdict==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			sysDict.setUpdateTime(new Date());
			boolean ok = sysDictService.updateById(sysDict);
			//TODO 返回false说明什么？
			if(ok) {
				return InvokeResult.success("编辑成功!");
			}
		}
		return InvokeResult.failure("error");
	}

	/**
	 * @功能：删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@CacheEvict(value=CacheConstant.SYS_DICT_CACHE, allEntries=true)
	public InvokeResult delete(@RequestParam(name="id",required=true) String id) {

		boolean ok = sysDictService.removeById(id);
		if(ok) {
			return InvokeResult.success("删除成功!");
		}else{
			return InvokeResult.failure("500", "删除失败!");
		}
	}

	/**
	 * @功能：批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	@CacheEvict(value= CacheConstant.SYS_DICT_CACHE, allEntries=true)
	public InvokeResult deleteBatch(@RequestParam(name="ids",required=true) String ids) {

		if(LangUtil.isEmpty(ids)) {
			return InvokeResult.failure("500", "参数不识别！");
		}else {
			sysDictService.removeByIds(Arrays.asList(ids.split(",")));
			return InvokeResult.success("删除成功!");
		}
	}

	/**
	 * 大数据量的字典表 走异步加载  即前端输入内容过滤数据
	 * @param dictCode
	 * @return
	 */
	@RequestMapping(value = "/loadDict/{dictCode}", method = RequestMethod.GET)
	public InvokeResult loadDict(@PathVariable String dictCode,@RequestParam(name="keyword") String keyword) {
		log.info(" 加载字典表数据,加载关键字: "+ keyword);

		List<DictModel> ls = null;
		try {
			if(dictCode.indexOf(",")!=-1) {
				String[] params = dictCode.split(",");
				if(params.length!=3) {
					return InvokeResult.failure("500", "字典Code格式不正确！");
				}
				ls = sysDictService.queryTableDictItems(params[0],params[1],params[2],keyword);
				return InvokeResult.success(null, ls);
			}else {
				return InvokeResult.failure("500", "字典Code格式不正确！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	 * 根据字典code加载字典text 返回
	 */
	@RequestMapping(value = "/loadDictItem/{dictCode}", method = RequestMethod.GET)
	public InvokeResult loadDictItem(@PathVariable String dictCode,@RequestParam(name="key") String key) {

		try {
			if(dictCode.indexOf(",")!=-1) {
				String[] params = dictCode.split(",");
				if(params.length!=3) {
					return InvokeResult.failure("500", "字典Code格式不正确！");
				}
				List<String> texts = sysDictService.queryTableDictByKeys(params[0], params[1], params[2], key.split(","));
				return InvokeResult.success(null, texts);
			}else {
				return InvokeResult.failure("500", "字典Code格式不正确！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	 * 根据表名——显示字段-存储字段 pid 加载树形数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadTreeData", method = RequestMethod.GET)
	public InvokeResult loadDict(@RequestParam(name="pid") String pid,@RequestParam(name="pidField") String pidField,
			@RequestParam(name="tableName") String tbname,
			@RequestParam(name="text") String text,
			@RequestParam(name="code") String code,
			@RequestParam(name="hasChildField") String hasChildField,
			@RequestParam(name="condition") String condition) {
		Map<String, String> query = null;
		if(LangUtil.isNotEmpty(condition)) {
			query = JSON.parseObject(condition, Map.class);
		}
		List<TreeSelectModel> ls = sysDictService.queryTreeList(query,tbname, text, code, pidField, pid,hasChildField);
		return InvokeResult.success(null, ls);
	}


	/**
	 * 查询被删除的列表
	 * @return
	 */
	@RequestMapping(value = "/deleteList", method = RequestMethod.GET)
	public InvokeResult deleteList() {
		List<SysDict> list = this.sysDictService.queryDeleteList();
		return InvokeResult.success(null, list);
	}

	/**
	 * 物理删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deletePhysic/{id}", method = RequestMethod.DELETE)
	public InvokeResult deletePhysic(@PathVariable String id) {
		try {
			sysDictService.deleteOneDictPhysically(id);
			return InvokeResult.success("删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return InvokeResult.failure("删除失败!");
		}
	}

	/**
	 * 取回
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/back/{id}", method = RequestMethod.PUT)
	public InvokeResult back(@PathVariable String id) {
		try {
			sysDictService.updateDictDelFlag(0,id);
			return InvokeResult.success("操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return InvokeResult.failure("操作失败!");
		}
	}

}
