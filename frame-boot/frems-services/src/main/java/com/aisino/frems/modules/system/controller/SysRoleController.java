package com.aisino.frems.modules.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aisino.frems.common.query.QueryGenerator;
import org.asframework.base.model.InvokeResult;
import com.aisino.frems.common.constant.CommonConstant;
import com.aisino.frems.modules.system.entity.SysPermission;
import com.aisino.frems.modules.system.entity.SysPermissionDataRule;
import com.aisino.frems.modules.system.entity.SysRole;
import com.aisino.frems.modules.system.entity.SysRolePermission;
import com.aisino.frems.modules.system.model.TreeModel;
import com.aisino.frems.modules.system.service.ISysPermissionDataRuleService;
import com.aisino.frems.modules.system.service.ISysPermissionService;
import com.aisino.frems.modules.system.service.ISysRolePermissionService;
import com.aisino.frems.modules.system.service.ISysRoleService;
import org.asframework.core.util.LangUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @Author scott
 * @since 2018-12-19
 */
@RestController
@RequestMapping("/sys/role")
@Slf4j
public class SysRoleController {
	@Autowired
	private ISysRoleService sysRoleService;

	@Autowired
	private ISysPermissionDataRuleService sysPermissionDataRuleService;

	@Autowired
	private ISysRolePermissionService sysRolePermissionService;

	@Autowired
	private ISysPermissionService sysPermissionService;

	/**
	  * 分页列表查询
	 * @param role
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public InvokeResult queryPageList(SysRole role,
									  @RequestParam(name="page.pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="page.pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {

		QueryWrapper<SysRole> queryWrapper = QueryGenerator.initQueryWrapper(role, req.getParameterMap());
		Page<SysRole> page = new Page<SysRole>(pageNo, pageSize);
		IPage<SysRole> pageList = sysRoleService.page(page, queryWrapper);
		return InvokeResult.success(null, pageList);
	}

	/**
	  *   添加
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public InvokeResult add(@RequestBody SysRole role) {
		try {
			role.setCreateTime(new Date());
			sysRoleService.save(role);
			return InvokeResult.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", "操作失败");
		}
	}

	/**
	  *  编辑
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public InvokeResult edit(@RequestBody SysRole role) {

		SysRole sysrole = sysRoleService.getById(role.getId());
		if(sysrole==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			role.setUpdateTime(new Date());
			boolean ok = sysRoleService.updateById(role);
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
		sysRoleService.deleteRole(id);
		return InvokeResult.success("删除角色成功");
	}

	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public InvokeResult deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		if(LangUtil.isEmpty(ids)) {
			return InvokeResult.failure("500", "未选中角色！");
		}else {
			sysRoleService.deleteBatchRole(ids.split(","));
			return InvokeResult.success("删除角色成功!");
		}
	}

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public InvokeResult queryById(@RequestParam(name="id",required=true) String id) {
		SysRole sysrole = sysRoleService.getById(id);
		if(sysrole==null) {
			return InvokeResult.failure("500", "未找到对应实体");
		}else {
			return InvokeResult.success(null, sysrole);
		}
	}

	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public InvokeResult queryall() {
		List<SysRole> list = sysRoleService.list();
		if(list==null||list.size()<=0) {
			return InvokeResult.failure("500", "未找到角色信息");
		}else {
			return InvokeResult.success(null, list);
		}
	}

	/**
	  * 校验角色编码唯一
	 */
	@RequestMapping(value = "/checkRoleCode", method = RequestMethod.GET)
	public InvokeResult checkUsername(String id,String roleCode) {
		log.info("--验证角色编码是否唯一---id:"+id+"--roleCode:"+roleCode);
		try {
			SysRole role = null;
			if(LangUtil.isNotEmpty(id)) {
				role = sysRoleService.getById(id);
			}
			SysRole newRole = sysRoleService.getOne(new QueryWrapper<SysRole>().lambda().eq(SysRole::getRoleCode, roleCode));
			if(newRole!=null) {
				//如果根据传入的roleCode查询到信息了，那么就需要做校验了。
				if(role==null) {
					//role为空=>新增模式=>只要roleCode存在则返回false
					return InvokeResult.failure("角色编码已存在");
				}else if(!id.equals(newRole.getId())) {
					//否则=>编辑模式=>判断两者ID是否一致-
					return InvokeResult.failure("角色编码已存在");
				}
			}
		} catch (Exception e) {
			return InvokeResult.failure(e.getMessage());
		}
		return InvokeResult.success();
	}

	/**
	 * 查询数据规则数据
	 */
	@GetMapping(value = "/datarule/{permissionId}/{roleId}")
	public InvokeResult loadDatarule(@PathVariable("permissionId") String permissionId,@PathVariable("roleId") String roleId) {
		List<SysPermissionDataRule> list = sysPermissionDataRuleService.getPermRuleListByPermId(permissionId);
		if(list==null || list.size()==0) {
			return InvokeResult.failure("未找到权限配置信息");
		}else {
			Map<String,Object> map = new HashMap<>();
			map.put("datarule", list);
			LambdaQueryWrapper<SysRolePermission> query = new LambdaQueryWrapper<SysRolePermission>()
					.eq(SysRolePermission::getPermissionId, permissionId)
					.eq(SysRolePermission::getRoleId,roleId);
			SysRolePermission sysRolePermission = sysRolePermissionService.getOne(query);
			if(sysRolePermission==null) {
				//return InvokeResult.failure("未找到角色菜单配置信息");
			}else {
				String drChecked = sysRolePermission.getDataRuleIds();
				if(LangUtil.isNotEmpty(drChecked)) {
					map.put("drChecked", drChecked.endsWith(",")?drChecked.substring(0, drChecked.length()-1):drChecked);
				}
			}
			return InvokeResult.success(null, map);
			//TODO 以后按钮权限的查询也走这个请求 无非在map中多加两个key
		}
	}

	/**
	 * 保存数据规则至角色菜单关联表
	 */
	@PostMapping(value = "/datarule")
	public InvokeResult saveDatarule(@RequestBody JSONObject jsonObject) {
		try {
			String permissionId = jsonObject.getString("permissionId");
			String roleId = jsonObject.getString("roleId");
			String dataRuleIds = jsonObject.getString("dataRuleIds");
			log.info("保存数据规则>>"+"菜单ID:"+permissionId+"角色ID:"+ roleId+"数据权限ID:"+dataRuleIds);
			LambdaQueryWrapper<SysRolePermission> query = new LambdaQueryWrapper<SysRolePermission>()
					.eq(SysRolePermission::getPermissionId, permissionId)
					.eq(SysRolePermission::getRoleId,roleId);
			SysRolePermission sysRolePermission = sysRolePermissionService.getOne(query);
			if(sysRolePermission==null) {
				return InvokeResult.failure("请先保存角色菜单权限!");
			}else {
				sysRolePermission.setDataRuleIds(dataRuleIds);
				this.sysRolePermissionService.updateById(sysRolePermission);
			}
		} catch (Exception e) {
			log.error("SysRoleController.saveDatarule()发生异常：" + e.getMessage(),e);
			return InvokeResult.failure("保存失败");
		}
		return InvokeResult.success("保存成功!");
	}


	/**
	 * 用户角色授权功能，查询菜单权限树
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryTreeList", method = RequestMethod.GET)
	public InvokeResult queryTreeList(HttpServletRequest request) {

		//全部权限ids
		List<String> ids = new ArrayList<>();
		try {
			LambdaQueryWrapper<SysPermission> query = new LambdaQueryWrapper<SysPermission>();
			query.eq(SysPermission::getDelFlag, CommonConstant.DEL_FLAG_0);
			query.orderByAsc(SysPermission::getSortNo);
			List<SysPermission> list = sysPermissionService.list(query);
			for(SysPermission sysPer : list) {
				ids.add(sysPer.getId());
			}
			List<TreeModel> treeList = new ArrayList<>();
			getTreeModelList(treeList, list, null);
			Map<String,Object> resMap = new HashMap<String,Object>();
			resMap.put("treeList", treeList); //全部树节点数据
			resMap.put("ids", ids);//全部树ids
			return InvokeResult.success(null, resMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return InvokeResult.failure("error");
	}

	private void getTreeModelList(List<TreeModel> treeList,List<SysPermission> metaList,TreeModel temp) {
		for (SysPermission permission : metaList) {
			String tempPid = permission.getParentId();
			TreeModel tree = new TreeModel(permission.getId(), tempPid, permission.getName(),permission.getRuleFlag(), permission.isLeaf());
			if(temp==null && LangUtil.isEmpty(tempPid)) {
				treeList.add(tree);
				if(!tree.getIsLeaf()) {
					getTreeModelList(treeList, metaList, tree);
				}
			}else if(temp!=null && tempPid!=null && tempPid.equals(temp.getKey())){
				temp.getChildren().add(tree);
				if(!tree.getIsLeaf()) {
					getTreeModelList(treeList, metaList, tree);
				}
			}

		}
	}


}
