package com.aisino.frems.modules.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.aisino.frems.modules.system.util.JwtUtil;
import org.asframework.base.model.InvokeResult;
import com.aisino.frems.common.constant.CommonConstant;
import com.aisino.frems.common.util.MD5Util;
import com.aisino.frems.modules.system.entity.SysPermission;
import com.aisino.frems.modules.system.entity.SysPermissionDataRule;
import com.aisino.frems.modules.system.entity.SysRolePermission;
import com.aisino.frems.modules.system.model.SysPermissionTree;
import com.aisino.frems.modules.system.model.TreeModel;
import com.aisino.frems.modules.system.service.ISysPermissionDataRuleService;
import com.aisino.frems.modules.system.service.ISysPermissionService;
import com.aisino.frems.modules.system.service.ISysRolePermissionService;
import com.aisino.frems.modules.system.util.PermissionDataUtil;
import org.asframework.core.util.LangUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @Author scott
 * @since 2018-12-21
 */
@Slf4j
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController {

	@Autowired
	private ISysPermissionService sysPermissionService;

	@Autowired
	private ISysRolePermissionService sysRolePermissionService;

	@Autowired
	private ISysPermissionDataRuleService sysPermissionDataRuleService;

	/**
	 * 加载数据节点
	 *
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public InvokeResult list() {
        long start = System.currentTimeMillis();
		try {
			LambdaQueryWrapper<SysPermission> query = new LambdaQueryWrapper<SysPermission>();
			query.eq(SysPermission::getDelFlag, CommonConstant.DEL_FLAG_0);
			query.orderByAsc(SysPermission::getSortNo);
			List<SysPermission> list = sysPermissionService.list(query);
			List<SysPermissionTree> treeList = new ArrayList<>();
			getTreeList(treeList, list, null);
            log.info("======获取全部菜单数据=====耗时:" + (System.currentTimeMillis() - start) + "毫秒");
			return InvokeResult.success(null, treeList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return InvokeResult.failure("error");
	}

	/*update_begin author:wuxianquan date:20190908 for:先查询一级菜单，当用户点击展开菜单时加载子菜单 */
	/**
	 * 系统菜单列表(一级菜单)
	 *
	 * @return
	 */
	@RequestMapping(value = "/getSystemMenuList", method = RequestMethod.GET)
	public InvokeResult getSystemMenuList() {
        long start = System.currentTimeMillis();
		try {
			LambdaQueryWrapper<SysPermission> query = new LambdaQueryWrapper<SysPermission>();
			query.eq(SysPermission::getMenuType,CommonConstant.MENU_TYPE_0);
			query.eq(SysPermission::getDelFlag, CommonConstant.DEL_FLAG_0);
			query.orderByAsc(SysPermission::getSortNo);
			List<SysPermission> list = sysPermissionService.list(query);
			List<SysPermissionTree> sysPermissionTreeList = new ArrayList<SysPermissionTree>();
			for(SysPermission sysPermission : list){
				SysPermissionTree sysPermissionTree = new SysPermissionTree(sysPermission);
				sysPermissionTreeList.add(sysPermissionTree);
			}
        	log.info("======获取一级菜单数据=====耗时:" + (System.currentTimeMillis() - start) + "毫秒");
			return InvokeResult.success(null, sysPermissionTreeList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return InvokeResult.failure("error");
	}


	/**
	 * 查询子菜单
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "/getSystemSubmenu", method = RequestMethod.GET)
	public InvokeResult getSystemSubmenu(@RequestParam("parentId") String parentId){
		try{
			LambdaQueryWrapper<SysPermission> query = new LambdaQueryWrapper<SysPermission>();
			query.eq(SysPermission::getParentId,parentId);
			query.eq(SysPermission::getDelFlag, CommonConstant.DEL_FLAG_0);
			query.orderByAsc(SysPermission::getSortNo);
			List<SysPermission> list = sysPermissionService.list(query);
			List<SysPermissionTree> sysPermissionTreeList = new ArrayList<SysPermissionTree>();
			for(SysPermission sysPermission : list){
				SysPermissionTree sysPermissionTree = new SysPermissionTree(sysPermission);
				sysPermissionTreeList.add(sysPermissionTree);
			}
			return InvokeResult.success(null, sysPermissionTreeList);
		}catch (Exception e){
			log.error(e.getMessage(), e);
		}
		return InvokeResult.failure("error");
	}

	/**
	 * 查询用户拥有的菜单权限和按钮权限（根据TOKEN）
	 *
	 * @return
	 */
	@RequestMapping(value = "/getUserPermissionByToken", method = RequestMethod.GET)
	public InvokeResult getUserPermissionByToken(@RequestParam(name = "token", required = true) String token) {

		try {
			if (LangUtil.isEmpty(token)) {
				return InvokeResult.failure("TOKEN不允许为空！");
			}
			log.info(" ------ 通过令牌获取用户拥有的访问菜单 ---- TOKEN ------ " + token);
			String username = JwtUtil.getUsername(token);
			List<SysPermission> metaList = sysPermissionService.queryByUser(username);
			//添加首页路由
			PermissionDataUtil.addIndexPage(metaList);
			JSONObject json = new JSONObject();
			JSONArray menujsonArray = new JSONArray();
			this.getPermissionJsonArray(menujsonArray, metaList, null);
			JSONArray authjsonArray = new JSONArray();
			this.getAuthJsonArray(authjsonArray, metaList);
			//查询所有的权限
			LambdaQueryWrapper<SysPermission> query = new LambdaQueryWrapper<SysPermission>();
			query.eq(SysPermission::getDelFlag, CommonConstant.DEL_FLAG_0);
			query.eq(SysPermission::getMenuType, CommonConstant.MENU_TYPE_2);
			//query.eq(SysPermission::getStatus, "1");
			List<SysPermission> allAuthList = sysPermissionService.list(query);
			JSONArray allauthjsonArray = new JSONArray();
			this.getAllAuthJsonArray(allauthjsonArray, allAuthList);
			//路由菜单
			json.put("menu", menujsonArray);
			//按钮权限
			json.put("auth", authjsonArray);
			//全部权限配置（按钮权限，访问权限）
			json.put("allAuth", allauthjsonArray);
			return InvokeResult.success(null, json);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", "查询失败:" + e.getMessage());
		}
	}

	/**
	  * 添加菜单
	 * @param permission
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public InvokeResult add(@RequestBody SysPermission permission) {

		try {
			permission = PermissionDataUtil.intelligentProcessData(permission);
			sysPermissionService.addPermission(permission);
			return InvokeResult.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	  * 编辑菜单
	 * @param permission
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.PUT, RequestMethod.POST })
	public InvokeResult edit(@RequestBody SysPermission permission) {

		try {
			permission = PermissionDataUtil.intelligentProcessData(permission);
			sysPermissionService.editPermission(permission);
			return InvokeResult.success("修改成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	  * 删除菜单
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public InvokeResult delete(@RequestParam(name = "id", required = true) String id) {
		try {
			sysPermissionService.deletePermission(id);
			sysPermissionService.deletePermRuleByPermId(id);
			return InvokeResult.success("删除成功!");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", e.getMessage());
		}
	}

	/**
	  * 批量删除菜单
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public InvokeResult deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		try {
            String[] arr = ids.split(",");
			for (String id : arr) {
				if (LangUtil.isNotEmpty(id)) {
					sysPermissionService.deletePermission(id);
				}
			}
			return InvokeResult.success("删除成功!");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", "删除成功!");
		}
	}

	/**
	 * 获取全部的权限树
	 *
	 * @return
	 */
	@RequestMapping(value = "/queryTreeList", method = RequestMethod.GET)
	public InvokeResult queryTreeList() {

		// 全部权限ids
		List<String> ids = new ArrayList<>();
		try {
			LambdaQueryWrapper<SysPermission> query = new LambdaQueryWrapper<SysPermission>();
			query.eq(SysPermission::getDelFlag, CommonConstant.DEL_FLAG_0);
			query.orderByAsc(SysPermission::getSortNo);
			List<SysPermission> list = sysPermissionService.list(query);
			for (SysPermission sysPer : list) {
				ids.add(sysPer.getId());
			}
			List<TreeModel> treeList = new ArrayList<>();
			getTreeModelList(treeList, list, null);

			Map<String, Object> resMap = new HashMap<String, Object>();
			resMap.put("treeList", treeList); // 全部树节点数据
			resMap.put("ids", ids);// 全部树ids
			return InvokeResult.success(null, resMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return InvokeResult.failure("error");
	}

	/**
	 * 异步加载数据节点
	 *
	 * @return
	 */
	@RequestMapping(value = "/queryListAsync", method = RequestMethod.GET)
	public InvokeResult queryAsync(@RequestParam(name = "pid", required = false) String parentId) {
		try {
			List<TreeModel> list = sysPermissionService.queryListByParentId(parentId);
			if (list == null || list.size() <= 0) {
				return InvokeResult.failure("500", "未找到角色信息");
			} else {
				return InvokeResult.success(null, list);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return InvokeResult.failure("error");
	}

	/**
	 * 查询角色授权
	 *
	 * @return
	 */
	@RequestMapping(value = "/queryRolePermission", method = RequestMethod.GET)
	public InvokeResult queryRolePermission(@RequestParam(name = "roleId", required = true) String roleId) {
		try {
			List<SysRolePermission> list = sysRolePermissionService.list(new QueryWrapper<SysRolePermission>().lambda().eq(SysRolePermission::getRoleId, roleId));
			return InvokeResult.success(null, list.stream().map(SysRolePermission -> String.valueOf(SysRolePermission.getPermissionId())).collect(Collectors.toList()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return InvokeResult.failure("error");
	}

	/**
	 * 保存角色授权
	 *
	 * @return
	 */
	@RequestMapping(value = "/saveRolePermission", method = RequestMethod.POST)
	public InvokeResult saveRolePermission(@RequestBody JSONObject json) {
		long start = System.currentTimeMillis();
		try {
			String roleId = json.getString("roleId");
			String permissionIds = json.getString("permissionIds");
			String lastPermissionIds = json.getString("lastpermissionIds");
			this.sysRolePermissionService.saveRolePermission(roleId, permissionIds, lastPermissionIds);
			log.info("======角色授权成功=====耗时:" + (System.currentTimeMillis() - start) + "毫秒");
			return InvokeResult.success("保存成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", "授权失败！");
		}
	}

	private void getTreeList(List<SysPermissionTree> treeList, List<SysPermission> metaList, SysPermissionTree temp) {
		for (SysPermission permission : metaList) {
			String tempPid = permission.getParentId();
			SysPermissionTree tree = new SysPermissionTree(permission);
			if (temp == null && LangUtil.isEmpty(tempPid)) {
				treeList.add(tree);
				if (!tree.getIsLeaf()) {
					getTreeList(treeList, metaList, tree);
				}
			} else if (temp != null && tempPid != null && tempPid.equals(temp.getId())) {
				temp.getChildren().add(tree);
				if (!tree.getIsLeaf()) {
					getTreeList(treeList, metaList, tree);
				}
			}
		}
	}

	private void getTreeModelList(List<TreeModel> treeList, List<SysPermission> metaList, TreeModel temp) {
		for (SysPermission permission : metaList) {
			String tempPid = permission.getParentId();
			TreeModel tree = new TreeModel(permission);
			if (temp == null && LangUtil.isEmpty(tempPid)) {
				treeList.add(tree);
				if (!tree.getIsLeaf()) {
					getTreeModelList(treeList, metaList, tree);
				}
			} else if (temp != null && tempPid != null && tempPid.equals(temp.getKey())) {
				temp.getChildren().add(tree);
				if (!tree.getIsLeaf()) {
					getTreeModelList(treeList, metaList, tree);
				}
			}

		}
	}

	/**
	  *  获取权限JSON数组
	 * @param jsonArray
	 * @param allList
	 */
	private void getAllAuthJsonArray(JSONArray jsonArray,List<SysPermission> allList) {
		JSONObject json = null;
		for (SysPermission permission : allList) {
			json = new JSONObject();
			json.put("action", permission.getPerms());
			json.put("status", permission.getStatus());
			json.put("type", permission.getPermsType());
			json.put("describe", permission.getName());
			jsonArray.add(json);
		}
	}

	/**
	  *  获取权限JSON数组
	 * @param jsonArray
	 * @param metaList
	 */
	private void getAuthJsonArray(JSONArray jsonArray,List<SysPermission> metaList) {
		for (SysPermission permission : metaList) {
			if(permission.getMenuType()==null) {
				continue;
			}
			JSONObject json = null;
			if(permission.getMenuType().equals(CommonConstant.MENU_TYPE_2) &&CommonConstant.STATUS_1.equals(permission.getStatus())) {
				json = new JSONObject();
				json.put("action", permission.getPerms());
				json.put("type", permission.getPermsType());
				json.put("describe", permission.getName());
				jsonArray.add(json);
			}
		}
	}
	/**
	  *  获取菜单JSON数组
	 * @param jsonArray
	 * @param metaList
	 * @param parentJson
	 */
	private void getPermissionJsonArray(JSONArray jsonArray, List<SysPermission> metaList, JSONObject parentJson) {
		for (SysPermission permission : metaList) {
			if (permission.getMenuType() == null) {
				continue;
			}
			String tempPid = permission.getParentId();
			JSONObject json = getPermissionJsonObject(permission);
			if(json==null) {
				continue;
			}
			if (parentJson == null && LangUtil.isEmpty(tempPid)) {
				jsonArray.add(json);
				if (!permission.isLeaf()) {
					getPermissionJsonArray(jsonArray, metaList, json);
				}
			} else if (parentJson != null && LangUtil.isNotEmpty(tempPid) && tempPid.equals(parentJson.getString("id"))) {
				// 类型( 0：一级菜单 1：子菜单 2：按钮 )
				if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_2)) {
					JSONObject metaJson = parentJson.getJSONObject("meta");
					if (metaJson.containsKey("permissionList")) {
						metaJson.getJSONArray("permissionList").add(json);
					} else {
						JSONArray permissionList = new JSONArray();
						permissionList.add(json);
						metaJson.put("permissionList", permissionList);
					}
					// 类型( 0：一级菜单 1：子菜单 2：按钮 )
				} else if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_1) || permission.getMenuType().equals(CommonConstant.MENU_TYPE_0)) {
					if (parentJson.containsKey("children")) {
						parentJson.getJSONArray("children").add(json);
					} else {
						JSONArray children = new JSONArray();
						children.add(json);
						parentJson.put("children", children);
					}

					if (!permission.isLeaf()) {
						getPermissionJsonArray(jsonArray, metaList, json);
					}
				}
			}

		}
	}

	/**
	 * 根据菜单配置生成路由json
	 * @param permission
	 * @return
	 */
		private JSONObject getPermissionJsonObject(SysPermission permission) {
		JSONObject json = new JSONObject();
		// 类型(0：一级菜单 1：子菜单 2：按钮)
		if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_2)) {
			//json.put("action", permission.getPerms());
			//json.put("type", permission.getPermsType());
			//json.put("describe", permission.getName());
			return null;
		} else if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_0) || permission.getMenuType().equals(CommonConstant.MENU_TYPE_1)) {
			json.put("id", permission.getId());
			if (permission.isRoute()) {
				json.put("route", "1");// 表示生成路由
			} else {
				json.put("route", "0");// 表示不生成路由
			}

			if (isWWWHttpUrl(permission.getUrl())) {
				json.put("path", MD5Util.MD5Encode(permission.getUrl(), "utf-8"));
			} else {
				json.put("path", permission.getUrl());
			}

			// 重要规则：路由name (通过URL生成路由name,路由name供前端开发，页面跳转使用)
			if (LangUtil.isNotEmpty(permission.getComponentName())) {
				json.put("name", permission.getComponentName());
			} else {
				json.put("name", urlToRouteName(permission.getUrl()));
			}

			// 是否隐藏路由，默认都是显示的
			if (permission.isHidden()) {
				json.put("hidden", true);
			}
			// 聚合路由
			if (permission.isAlwaysShow()) {
				json.put("alwaysShow", true);
			}
			json.put("component", permission.getComponent());
			JSONObject meta = new JSONObject();
			// 由用户设置是否缓存页面 用布尔值
			if (permission.isKeepAlive()) {
				meta.put("keepAlive", true);
			} else {
				meta.put("keepAlive", false);
			}

			/*update_begin author:wuxianquan date:20190908 for:往菜单信息里添加外链菜单打开方式 */
			//外链菜单打开方式
			if (permission.isInternalOrExternal()) {
				meta.put("internalOrExternal", true);
			} else {
				meta.put("internalOrExternal", false);
			}
			/* update_end author:wuxianquan date:20190908 for: 往菜单信息里添加外链菜单打开方式*/

			meta.put("title", permission.getName());
			if (LangUtil.isEmpty(permission.getParentId())) {
				// 一级菜单跳转地址
				json.put("redirect", permission.getRedirect());
				if (LangUtil.isNotEmpty(permission.getIcon())) {
					meta.put("icon", permission.getIcon());
				}
			} else {
				if (LangUtil.isNotEmpty(permission.getIcon())) {
					meta.put("icon", permission.getIcon());
				}
			}
			if (isWWWHttpUrl(permission.getUrl())) {
				meta.put("url", permission.getUrl());
			}
			json.put("meta", meta);
		}

		return json;
	}

	/**
	 * 判断是否外网URL 例如： http://localhost:8080/jeecg-boot/swagger-ui.html#/ 支持特殊格式： {{
	 * window._CONFIG['domianURL'] }}/druid/ {{ JS代码片段 }}，前台解析会自动执行JS代码片段
	 *
	 * @return
	 */
	private boolean isWWWHttpUrl(String url) {
		if (url != null && (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("{{"))) {
			return true;
		}
		return false;
	}

	/**
	 * 通过URL生成路由name（去掉URL前缀斜杠，替换内容中的斜杠‘/’为-） 举例： URL = /isystem/role RouteName =
	 * isystem-role
	 *
	 * @return
	 */
	private String urlToRouteName(String url) {
		if (LangUtil.isNotEmpty(url)) {
			if (url.startsWith("/")) {
				url = url.substring(1);
			}
			url = url.replace("/", "-");

			// 特殊标记
			url = url.replace(":", "@");
			return url;
		} else {
			return null;
		}
	}

	/**
	 * 根据菜单id来获取其对应的权限数据
	 *
	 * @param sysPermissionDataRule
	 * @return
	 */
	@RequestMapping(value = "/getPermRuleListByPermId", method = RequestMethod.GET)
	public InvokeResult getPermRuleListByPermId(SysPermissionDataRule sysPermissionDataRule) {
		List<SysPermissionDataRule> permRuleList = sysPermissionDataRuleService.getPermRuleListByPermId(sysPermissionDataRule.getPermissionId());
		return InvokeResult.success(null, permRuleList);
	}

	/**
	 * 添加菜单权限数据
	 *
	 * @param sysPermissionDataRule
	 * @return
	 */
	@RequestMapping(value = "/addPermissionRule", method = RequestMethod.POST)
	public InvokeResult addPermissionRule(@RequestBody SysPermissionDataRule sysPermissionDataRule) {
		try {
			sysPermissionDataRule.setCreateTime(new Date());
			sysPermissionDataRuleService.savePermissionDataRule(sysPermissionDataRule);
			return InvokeResult.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	@RequestMapping(value = "/editPermissionRule", method = { RequestMethod.PUT, RequestMethod.POST })
	public InvokeResult editPermissionRule(@RequestBody SysPermissionDataRule sysPermissionDataRule) {
		try {
			sysPermissionDataRuleService.saveOrUpdate(sysPermissionDataRule);
			return InvokeResult.success("更新成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	 * 删除菜单权限数据
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deletePermissionRule", method = RequestMethod.DELETE)
	public InvokeResult deletePermissionRule(@RequestParam(name = "id", required = true) String id) {
		try {
			sysPermissionDataRuleService.deletePermissionDataRule(id);
			return InvokeResult.success("删除成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	 * 查询菜单权限数据
	 *
	 * @param sysPermissionDataRule
	 * @return
	 */
	@RequestMapping(value = "/queryPermissionRule", method = RequestMethod.GET)
	public InvokeResult queryPermissionRule(SysPermissionDataRule sysPermissionDataRule) {
		try {
			List<SysPermissionDataRule> permRuleList = sysPermissionDataRuleService.queryPermissionRule(sysPermissionDataRule);
			return InvokeResult.success("查询成功！", permRuleList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

}
