package com.aisino.frems.modules.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.aisino.frems.common.model.LoginUser;
import com.aisino.frems.common.query.QueryGenerator;
import com.aisino.frems.common.util.RedisUtil;
import com.aisino.frems.modules.system.api.ISysBaseAPI;
import com.aisino.frems.modules.system.util.JwtUtil;
import com.alibaba.fastjson.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.asframework.base.model.InvokeResult;
import com.aisino.frems.common.constant.CommonConstant;
import com.aisino.frems.common.util.PasswordUtil;
import com.aisino.frems.common.util.oConvertUtils;
import com.aisino.frems.modules.system.entity.*;
import com.aisino.frems.modules.system.model.DepartIdModel;
import com.aisino.frems.modules.system.model.SysUserSysDepartModel;
import com.aisino.frems.modules.system.service.ISysDepartService;
import com.aisino.frems.modules.system.service.ISysUserDepartService;
import com.aisino.frems.modules.system.service.ISysUserRoleService;
import com.aisino.frems.modules.system.service.ISysUserService;
import com.aisino.frems.modules.system.vo.SysDepartUsersVO;
import com.aisino.frems.modules.system.vo.SysUserRoleVO;
import org.asframework.core.util.LangUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * ????????? ???????????????
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
@Slf4j
@RestController
@RequestMapping("/sys/user")
public class SysUserController {
	@Autowired
	private ISysBaseAPI sysBaseAPI;

	@Autowired
	private ISysUserService sysUserService;

    @Autowired
    private ISysDepartService sysDepartService;

	@Autowired
	private ISysUserRoleService sysUserRoleService;

	@Autowired
	private ISysUserDepartService sysUserDepartService;

	@Autowired
	private ISysUserRoleService userRoleService;

	@Autowired
	private RedisUtil redisUtil;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public InvokeResult queryPageList(SysUser user,@RequestParam(name="page.pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="page.pageSize", defaultValue="10") Integer pageSize,HttpServletRequest req) {

		QueryWrapper queryWrapper = QueryGenerator.initQueryWrapper(user, req.getParameterMap());
		Page page = new Page(pageNo, pageSize);
		IPage pageList = sysUserService.page(page, queryWrapper);
        return InvokeResult.success(null, pageList);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public InvokeResult add(@RequestBody JSONObject jsonObject) {
		String selectedRoles = jsonObject.getString("selectedroles");
		String selectedDeparts = jsonObject.getString("selecteddeparts");
		try {
			SysUser user = JSON.parseObject(jsonObject.toJSONString(), SysUser.class);
			user.setCreateTime(new Date());//??????????????????
			String salt = oConvertUtils.randomGen(8);
			user.setSalt(salt);
			String passwordEncode = PasswordUtil.encrypt(user.getUsername(), user.getPassword(), salt);
			user.setPassword(passwordEncode);
			user.setStatus(1);
			user.setDelFlag(0);
			sysUserService.addUserWithRole(user, selectedRoles);
            sysUserService.addUserWithDepart(user, selectedDeparts);
            return InvokeResult.success("???????????????");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            return InvokeResult.failure("500", "????????????");
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public InvokeResult edit(@RequestBody JSONObject jsonObject) {
		try {
			SysUser sysUser = sysUserService.getById(jsonObject.getString("id"));
			sysBaseAPI.addLog("???????????????id??? " +jsonObject.getString("id") ,CommonConstant.LOG_TYPE_2, 2);
			if(sysUser==null) {
				return InvokeResult.failure("500", "?????????????????????");
			}else {
				SysUser user = JSON.parseObject(jsonObject.toJSONString(), SysUser.class);
				user.setUpdateTime(new Date());
				//String passwordEncode = PasswordUtil.encrypt(user.getUsername(), user.getPassword(), sysUser.getSalt());
				user.setPassword(sysUser.getPassword());
				String roles = jsonObject.getString("selectedroles");
                String departs = jsonObject.getString("selecteddeparts");
				sysUserService.editUserWithRole(user, roles);
                sysUserService.editUserWithDepart(user, departs);
                return InvokeResult.success("????????????!");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", "????????????");
		}
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public InvokeResult delete(@RequestParam(name="id",required=true) String id) {
		sysBaseAPI.addLog("???????????????id??? " +id ,CommonConstant.LOG_TYPE_2, 3);
		this.sysUserService.deleteUser(id);
		return InvokeResult.success("??????????????????");
	}

	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public InvokeResult deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		sysBaseAPI.addLog("????????????????????? ids??? " +ids ,CommonConstant.LOG_TYPE_2, 3);
		this.sysUserService.deleteBatchUsers(ids);
		return InvokeResult.success("????????????????????????");
	}

	/**
	  * ??????&????????????
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(value = "/frozenBatch", method = RequestMethod.PUT)
	public InvokeResult frozenBatch(@RequestBody JSONObject jsonObject) {
		try {
			String ids = jsonObject.getString("ids");
			String status = jsonObject.getString("status");
			String[] arr = ids.split(",");
			for (String id : arr) {
				if(LangUtil.isNotEmpty(id)) {
                    SysUser sysUser = new SysUser();
                    sysUser.setStatus(Integer.parseInt(status));
					this.sysUserService.update(sysUser,
							new UpdateWrapper<SysUser>().lambda().eq(SysUser::getId,id));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", "????????????"+e.getMessage());
		}
        return InvokeResult.success("????????????!");

    }

    @RequestMapping(value = "/queryById", method = RequestMethod.GET)
    public InvokeResult queryById(@RequestParam(name = "id", required = true) String id) {
        SysUser sysUser = sysUserService.getById(id);
        if (sysUser == null) {
            return InvokeResult.failure("500", "?????????????????????");
        } else {
            return InvokeResult.success(null, sysUser);
        }
    }

    @RequestMapping(value = "/queryUserRole", method = RequestMethod.GET)
    public InvokeResult queryUserRole(@RequestParam(name = "userid", required = true) String userid) {
        List<String> list = new ArrayList<>();
        List<SysUserRole> userRole = sysUserRoleService.list(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserId, userid));
        if (userRole == null || userRole.size() <= 0) {
            return InvokeResult.failure("500", "?????????????????????????????????");
        } else {
            for (SysUserRole sysUserRole : userRole) {
                list.add(sysUserRole.getRoleId());
            }
            return InvokeResult.success(null, list);
        }
    }


    /**
	  *  ??????????????????????????????<br>
	  *  ?????????????????? ???????????????????????????????????????
     *
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/checkOnlyUser", method = RequestMethod.GET)
    public InvokeResult checkOnlyUser(SysUser sysUser) {
        //??????????????????false?????????????????????
        try {
            //??????????????????????????????????????????
            SysUser user = sysUserService.getOne(new QueryWrapper(sysUser));
            if (user != null) {
                return InvokeResult.failure("?????????????????????");
            }
        } catch (Exception e) {
            return InvokeResult.failure(e.getMessage());
        }
        return InvokeResult.success();
    }

    /**
     * ????????????
     */
    @RequestMapping(value = "/changPassword", method = RequestMethod.PUT)
    public InvokeResult changPassword(@RequestBody SysUser sysUser) {
        SysUser u = this.sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, sysUser.getUsername()));
        if (u == null) {
            return InvokeResult.failure("??????????????????");
        }
        sysUser.setId(u.getId());
        return sysUserService.changePassword(sysUser);
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/userDepartList", method = RequestMethod.GET)
    public InvokeResult getUserDepartsList(@RequestParam(name = "userId", required = true) String userId) {
        try {
            List<DepartIdModel> depIdModelList = this.sysUserDepartService.queryDepartIdsOfUser(userId);
            if (depIdModelList != null && depIdModelList.size() > 0) {
                return InvokeResult.success("????????????", depIdModelList);
            } else {
                return InvokeResult.failure("????????????");
            }
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
            return InvokeResult.failure("??????????????????????????????: " + e.getMessage());
        }

    }

    /**
     * ???????????????????????????????????????????????????,???????????????,?????????id??????????????????
     *
     * @return
     */
    @RequestMapping(value = "/generateUserId", method = RequestMethod.GET)
    public InvokeResult generateUserId() {
        String userId = UUID.randomUUID().toString().replace("-", "");
        return InvokeResult.success(null, userId);
    }

    /**
     * ????????????id??????????????????
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryUserByDepId", method = RequestMethod.GET)
    public InvokeResult queryUserByDepId(@RequestParam(name = "id", required = true) String id) {
        List userList = sysUserDepartService.queryUserByDepId(id);
        try {
            return InvokeResult.success(null, userList);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
            return InvokeResult.failure("error");
        }
    }

    /**
     * ??????????????????????????????????????????
     *
     * @return
     */
    @RequestMapping(value = "/queryUserRoleMap", method = RequestMethod.GET)
    public InvokeResult queryUserRole() {
        Map<String, String> map = userRoleService.queryUserRole();
        return InvokeResult.success(null, map);
    }

    /**
	 * @???????????????id ????????????
	 * @param userIds
	 * @return
	 */
	@RequestMapping(value = "/queryByIds", method = RequestMethod.GET)
	public InvokeResult queryByIds(@RequestParam String userIds) {

		String[] userId = userIds.split(",");
		Collection<String> idList = Arrays.asList(userId);
		Collection userRole = sysUserService.listByIds(idList);
        return InvokeResult.success(null, userRole);
	}

	/**
	 * ????????????????????????
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
	public InvokeResult changPassword(@RequestBody JSONObject json) {
		String username = json.getString("username");
		String oldpassword = json.getString("oldpassword");
		String password = json.getString("password");
		String confirmpassword = json.getString("confirmpassword");
		SysUser user = this.sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
		if(user==null) {
			return InvokeResult.failure("??????????????????");
		}
		return sysUserService.resetPassword(username,oldpassword,password,confirmpassword);
	}

    @RequestMapping(value = "/userRoleList", method = RequestMethod.GET)
    public InvokeResult userRoleList(@RequestParam(name="page.pageNo", defaultValue="1") Integer pageNo,
                                               @RequestParam(name="page.pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req) {
        Page page = new Page(pageNo, pageSize);
        String roleId = req.getParameter("roleId");
        String username = req.getParameter("username");
        IPage pageList = sysUserService.getUserByRoleId(page,roleId,username);
        return InvokeResult.success(null, pageList);
    }

    /**
     * ???????????????????????????
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/addSysUserRole", method = RequestMethod.POST)
    public InvokeResult addSysUserRole(@RequestBody SysUserRoleVO sysUserRoleVO) {
        try {
            String sysRoleId = sysUserRoleVO.getRoleId();
            for(String sysUserId:sysUserRoleVO.getUserIdList()) {
                SysUserRole sysUserRole = new SysUserRole(sysUserId,sysRoleId);
                QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<SysUserRole>();
                queryWrapper.eq("role_id", sysRoleId).eq("user_id",sysUserId);
                SysUserRole one = sysUserRoleService.getOne(queryWrapper);
                if(one==null){
                    sysUserRoleService.save(sysUserRole);
                }

            }
            return InvokeResult.success("????????????!");
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            return InvokeResult.failure("?????????: " + e.getMessage());
        }
    }
    /**
     *   ?????????????????????????????????
     * @param
     * @return
     */
    @RequestMapping(value = "/deleteUserRole", method = RequestMethod.DELETE)
    public InvokeResult deleteUserRole(@RequestParam(name="roleId") String roleId,
                                                    @RequestParam(name="userId",required=true) String userId
    ) {
        try {
            QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<SysUserRole>();
            queryWrapper.eq("role_id", roleId).eq("user_id",userId);
            sysUserRoleService.remove(queryWrapper);
            return InvokeResult.success("????????????!");
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            return InvokeResult.failure("500", "???????????????");
        }
    }

    /**
     * ???????????????????????????????????????
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/deleteUserRoleBatch", method = RequestMethod.DELETE)
    public InvokeResult deleteUserRoleBatch(
            @RequestParam(name="roleId") String roleId,
            @RequestParam(name="userIds",required=true) String userIds) {
        try {
            QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<SysUserRole>();
            queryWrapper.eq("role_id", roleId).in("user_id",Arrays.asList(userIds.split(",")));
            sysUserRoleService.remove(queryWrapper);
            return InvokeResult.success("????????????!");
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            return InvokeResult.failure("500", "???????????????");
        }
    }

    /**
     * ??????????????????
     */
    @RequestMapping(value = "/departUserList", method = RequestMethod.GET)
    public InvokeResult departUserList(@RequestParam(name="page.pageNo", defaultValue="1") Integer pageNo,
                                                 @RequestParam(name="page.pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req) {
        Page page = new Page(pageNo, pageSize);
        String depId = req.getParameter("depId");
        String username = req.getParameter("username");
        IPage pageList = sysUserService.getUserByDepId(page,depId,username);
        return InvokeResult.success(null, pageList);
    }


    /**
     * ?????? orgCode ??????????????????????????????????????????
     * ?????????????????????????????????????????????????????????????????????????????????????????????
     */
    @GetMapping("/queryByOrgCode")
    public InvokeResult queryByDepartId(
            @RequestParam(name = "page.pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "page.pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "orgCode") String orgCode,
            SysUser userParams
    ) {
        IPage<SysUserSysDepartModel> pageList = sysUserService.queryUserByOrgCode(orgCode, userParams, new Page(pageNo, pageSize));
        return InvokeResult.success(null, pageList);
    }

    /**
     * ?????? orgCode ??????????????????????????????????????????
     * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    @GetMapping("/queryByOrgCodeForAddressList")
    public InvokeResult queryByOrgCodeForAddressList(
            @RequestParam(name = "page.pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "page.pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "orgCode") String orgCode,
            SysUser userParams
    ) {
        IPage page = new Page(pageNo, pageSize);
        IPage<SysUserSysDepartModel> pageList = sysUserService.queryUserByOrgCode(orgCode, userParams, page);
        List<SysUserSysDepartModel> list = pageList.getRecords();

        // ???????????????????????? user, key = userId
        Map<String, JSONObject> hasUser = new HashMap<>(list.size());

        JSONArray resultJson = new JSONArray(list.size());

        for (SysUserSysDepartModel item : list) {
            String userId = item.getSysUser().getId();
            // userId
            JSONObject getModel = hasUser.get(userId);
            // ????????????????????????????????????????????????
            if (getModel != null) {
                String departName = getModel.get("departName").toString();
                getModel.put("departName", (departName + " | " + item.getSysDepart().getDepartName()));
            } else {
                // ????????????????????????json???????????????????????????????????? json ???
                JSONObject json = JSON.parseObject(JSON.toJSONString(item.getSysUser()));
                json.remove("id");
                json.put("userId", userId);
                json.put("departId", item.getSysDepart().getId());
                json.put("departName", item.getSysDepart().getDepartName());

                resultJson.add(json);
                hasUser.put(userId, json);
            }
        }

        IPage<JSONObject> result = new Page<>(pageNo, pageSize, pageList.getTotal());
        result.setRecords(resultJson.toJavaList(JSONObject.class));
        return InvokeResult.success(null, result);
    }

    /**
     * ????????????????????????????????????
     */
    @RequestMapping(value = "/editSysDepartWithUser", method = RequestMethod.POST)
    public InvokeResult editSysDepartWithUser(@RequestBody SysDepartUsersVO sysDepartUsersVO) {
        try {
            String sysDepId = sysDepartUsersVO.getDepId();
            for(String sysUserId:sysDepartUsersVO.getUserIdList()) {
                SysUserDepart sysUserDepart = new SysUserDepart(null,sysUserId,sysDepId);
                QueryWrapper<SysUserDepart> queryWrapper = new QueryWrapper<SysUserDepart>();
                queryWrapper.eq("dep_id", sysDepId).eq("user_id",sysUserId);
                SysUserDepart one = sysUserDepartService.getOne(queryWrapper);
                if(one==null){
                    sysUserDepartService.save(sysUserDepart);
                }
            }
            return InvokeResult.success("????????????!");
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            return InvokeResult.failure("?????????: " + e.getMessage());
        }
    }

    /**
     *   ?????????????????????????????????
     */
    @RequestMapping(value = "/deleteUserInDepart", method = RequestMethod.DELETE)
    public InvokeResult deleteUserInDepart(@RequestParam(name="depId") String depId,
                                                    @RequestParam(name="userId",required=true) String userId
    ) {
        try {
            QueryWrapper<SysUserDepart> queryWrapper = new QueryWrapper<SysUserDepart>();
            queryWrapper.eq("dep_id", depId).eq("user_id",userId);
            sysUserDepartService.remove(queryWrapper);
            return InvokeResult.success("????????????!");
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            return InvokeResult.failure("500", "???????????????");
        }
    }

    /**
     * ???????????????????????????????????????
     */
    @RequestMapping(value = "/deleteUserInDepartBatch", method = RequestMethod.DELETE)
    public InvokeResult deleteUserInDepartBatch(
            @RequestParam(name="depId") String depId,
            @RequestParam(name="userIds",required=true) String userIds) {
        try {
            QueryWrapper<SysUserDepart> queryWrapper = new QueryWrapper<SysUserDepart>();
            queryWrapper.eq("dep_id", depId).in("user_id",Arrays.asList(userIds.split(",")));
            sysUserDepartService.remove(queryWrapper);
            return InvokeResult.success("????????????!");
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            return InvokeResult.failure("500", "???????????????");
        }
    }

    /**
         *  ?????????????????????????????????/??????????????????
     * @return
     */
    @RequestMapping(value = "/getCurrentUserDeparts", method = RequestMethod.GET)
    public InvokeResult getCurrentUserDeparts() {
        try {
        	LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
            List<SysDepart> list = this.sysDepartService.queryUserDeparts(sysUser.getId());
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("list", list);
            map.put("orgCode", sysUser.getOrgCode());
            return InvokeResult.success(null, map);
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            return InvokeResult.failure("500", "???????????????");
        }
    }

	/**
	 * ??????????????????
	 *
	 * @param jsonObject
	 * @param user
	 * @return
	 */
	@PostMapping("/register")
	public InvokeResult userRegister(@RequestBody JSONObject jsonObject, SysUser user) {

		String phone = jsonObject.getString("phone");
		String smscode = jsonObject.getString("smscode");
		Object code = redisUtil.get(phone);
		String username = jsonObject.getString("username");
		String password = jsonObject.getString("password");
		String email = jsonObject.getString("email");
		SysUser sysUser1 = sysUserService.getUserByName(username);
		if (sysUser1 != null) {
            return InvokeResult.failure("??????????????????");
		}
		SysUser sysUser2 = sysUserService.getUserByPhone(phone);

		if (sysUser2 != null) {
            return InvokeResult.failure("?????????????????????");
		}
		SysUser sysUser3 = sysUserService.getUserByEmail(email);
		if (sysUser3 != null) {
            return InvokeResult.failure("??????????????????");
		}

		if (!smscode.equals(code)) {
            return InvokeResult.failure("?????????????????????");
		}

		try {
			user.setCreateTime(new Date());// ??????????????????
			String salt = oConvertUtils.randomGen(8);
			String passwordEncode = PasswordUtil.encrypt(username, password, salt);
			user.setSalt(salt);
			user.setUsername(username);
			user.setRealname(username);
			user.setPassword(passwordEncode);
			user.setEmail(email);
			user.setPhone(phone);
			user.setStatus(1);
			user.setDelFlag(CommonConstant.DEL_FLAG_0);
			user.setActivitiSync(CommonConstant.ACT_SYNC_1);
			sysUserService.addUserWithRole(user,"ee8626f80f7c2619917b6236f3a7f02b");//?????????????????? test
			return InvokeResult.success("????????????");
		} catch (Exception e) {
			return InvokeResult.failure("500", "????????????");
		}
	}

	/**
	 *  ?????????????????????????????????????????????
	 * @return
	 */
	@GetMapping("/querySysUser")
	public InvokeResult querySysUser(SysUser sysUser) {
		String phone = sysUser.getPhone();
		String username = sysUser.getUsername();

		Map<String, Object> map = new HashMap<String, Object>();
		if (LangUtil.isNotEmpty(phone)) {
			SysUser user = sysUserService.getUserByPhone(phone);
			if(user!=null) {
				map.put("username",user.getUsername());
				map.put("phone",user.getPhone());
                return InvokeResult.success(null, map);
			}
		}
		if (LangUtil.isNotEmpty(username)) {
			SysUser user = sysUserService.getUserByName(username);
			if(user!=null) {
				map.put("username",user.getUsername());
				map.put("phone",user.getPhone());
                return InvokeResult.success(null, map);
			}
		}
        return InvokeResult.failure("????????????");
	}

	/**
	 * ?????????????????????
	 */
	@PostMapping("/phoneVerification")
	public InvokeResult phoneVerification(@RequestBody JSONObject jsonObject) {

		String phone = jsonObject.getString("phone");
		String smscode = jsonObject.getString("smscode");
		Object code = redisUtil.get(phone);
		if (!smscode.equals(code)) {
            return InvokeResult.failure("?????????????????????");
		}
		redisUtil.set(phone, smscode);
        return InvokeResult.success(null, smscode);
	}

	/**
	 * ??????????????????
	 */
	@GetMapping("/passwordChange")
	public InvokeResult passwordChange(@RequestParam(name="username")String username,
										  @RequestParam(name="password")String password,
			                              @RequestParam(name="smscode")String smscode,
			                              @RequestParam(name="phone") String phone) {
        InvokeResult result = new InvokeResult();
        SysUser sysUser=new SysUser();
        Object object= redisUtil.get(phone);
        if(null==object) {
            return InvokeResult.failure("??????????????????");
        }
        if(!smscode.equals(object)) {
            return InvokeResult.failure("??????????????????");
        }
        sysUser = this.sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,username));
        if (sysUser == null) {
            return InvokeResult.failure("?????????????????????");
        } else {
            String salt = oConvertUtils.randomGen(8);
            sysUser.setSalt(salt);
            String passwordEncode = PasswordUtil.encrypt(sysUser.getUsername(), password, salt);
            sysUser.setPassword(passwordEncode);
            this.sysUserService.updateById(sysUser);
            return InvokeResult.success("?????????????????????");
        }
    }


	/**
	 * ??????TOKEN???????????????????????????????????????????????????????????????????????????????????????
	 *
	 * @return
	 */
	@GetMapping("/getUserSectionInfoByToken")
	public InvokeResult getUserSectionInfoByToken(HttpServletRequest request, @RequestParam(name = "token", required = false) String token) {
		try {
			String username = null;
			// ??????????????????token?????????header?????????token?????????????????????
			if (LangUtil.isEmpty(token)) {
				 username = JwtUtil.getUserNameByToken(request);
			} else {
				 username = JwtUtil.getUsername(token);
			}

			log.info(" ------ ?????????????????????????????????????????????????????? " + username);

			// ?????????????????????????????????
			SysUser sysUser = sysUserService.getUserByName(username);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sysUserId", sysUser.getId());
			map.put("sysUserCode", sysUser.getUsername()); // ??????????????????????????????
			map.put("sysUserName", sysUser.getRealname()); // ??????????????????????????????
			map.put("sysOrgCode", sysUser.getOrgCode()); // ??????????????????????????????

			log.info(" ------ ?????????????????????????????????????????????????????????????????? " + map);

			return InvokeResult.success(null, map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", "????????????:" + e.getMessage());
		}
	}

	/**
	 * ??????????????????  ??????????????????????????? ????????????
	 * @param keyword
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/appUserList")
	public InvokeResult appUserList(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name="page.pageNo", defaultValue="1") Integer pageNo,
			@RequestParam(name="page.pageSize", defaultValue="10") Integer pageSize) {
		try {
			//TODO ??????????????????????????????mp????????????page???????????? ???????????????????????????
			LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper();
			query.eq(SysUser::getActivitiSync, 1);
			query.eq(SysUser::getDelFlag,0);
			query.and(i -> i.like(SysUser::getUsername, keyword).or().like(SysUser::getRealname, keyword));

			Page page = new Page<>(pageNo, pageSize);
			IPage res = this.sysUserService.page(page, query);
			return InvokeResult.success(null, res);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return InvokeResult.failure("500", "????????????:" + e.getMessage());
		}

	}

    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    public InvokeResult getCurrentUser() {
        try {
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            return InvokeResult.success(null, sysUser);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return InvokeResult.failure("500", "???????????????");
        }
    }
}
