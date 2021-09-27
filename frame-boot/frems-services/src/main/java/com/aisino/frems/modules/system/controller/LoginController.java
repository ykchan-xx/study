package com.aisino.frems.modules.system.controller;

import cn.hutool.core.util.RandomUtil;
import com.aisino.frems.common.constant.DySmsEnum;
import com.aisino.frems.common.model.LoginUser;
import com.aisino.frems.modules.shiro.vo.DefContants;
import com.aisino.frems.modules.system.api.ISysBaseAPI;
import com.aisino.frems.modules.system.util.JwtUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.asframework.base.model.InvokeResult;
import com.aisino.frems.common.constant.CommonConstant;
import com.aisino.frems.common.util.*;
import com.aisino.frems.common.util.encryption.EncryptedString;
import com.aisino.frems.modules.system.entity.SysDepart;
import com.aisino.frems.modules.system.entity.SysUser;
import com.aisino.frems.modules.system.model.SysLoginModel;
import com.aisino.frems.modules.system.service.ISysDepartService;
import com.aisino.frems.modules.system.service.ISysLogService;
import com.aisino.frems.modules.system.service.ISysUserService;
import org.asframework.core.util.LangUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author scott
 * @since 2018-12-17
 */
@RestController
@RequestMapping("/sys")
@Api(tags="用户登录")
@Slf4j
public class LoginController {
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysBaseAPI sysBaseAPI;
	@Autowired
	private ISysLogService logService;
	@Autowired
    private RedisUtil redisUtil;
	@Autowired
    private ISysDepartService sysDepartService;

	private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

	@ApiOperation("登录接口")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public InvokeResult login(@RequestBody SysLoginModel sysLoginModel){

		String username = sysLoginModel.getUsername();
		String password = sysLoginModel.getPassword();
		//update-begin--Author:scott  Date:20190805 for：暂时注释掉密码加密逻辑，有点问题
		//前端密码加密，后端进行密码解密
		//password = AesEncryptUtil.desEncrypt(sysLoginModel.getPassword().replaceAll("%2B", "\\+")).trim();//密码解密
		//update-begin--Author:scott  Date:20190805 for：暂时注释掉密码加密逻辑，有点问题

		//update-begin-author:taoyan date:20190828 for:校验验证码
		/*Object checkCode = redisUtil.get(sysLoginModel.getCheckKey());
		if(checkCode==null) {
			return InvokeResult.failure("500", "验证码失效");
		}
		if(!checkCode.equals(sysLoginModel.getCaptcha())) {
			return InvokeResult.failure("500", "验证码错误");
		}*/
		//update-end-author:taoyan date:20190828 for:校验验证码

		//1. 校验用户是否有效
		SysUser sysUser = sysUserService.getUserByName(username);
		InvokeResult result = sysUserService.checkUserIsEffective(sysUser);
		if(result.getSuccess()==0) {
			return result;
		}

		//2. 校验用户名或密码是否正确
		String userpassword = PasswordUtil.encrypt(username, password, sysUser.getSalt());
		String syspassword = sysUser.getPassword();
		if (!syspassword.equals(userpassword)) {
			return InvokeResult.failure("500", "用户名或密码错误");
		}

		//用户登录信息
		userInfo(sysUser, result);
		sysBaseAPI.addLog("用户名: " + username + ",登录成功！", CommonConstant.LOG_TYPE_1, null);

		return result;
	}

	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public InvokeResult logout(HttpServletRequest request,HttpServletResponse response) {
		//用户退出逻辑
	    String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
	    if(LangUtil.isEmpty(token)) {
	    	return InvokeResult.failure("退出登录失败！");
	    }
	    String username = JwtUtil.getUsername(token);
		LoginUser sysUser = sysBaseAPI.getUserByName(username);
	    if(sysUser!=null) {
	    	sysBaseAPI.addLog("用户名: "+sysUser.getRealname()+",退出成功！", CommonConstant.LOG_TYPE_1, null);
	    	log.info(" 用户名:  "+sysUser.getRealname()+",退出成功！ ");
	    	//清空用户登录Token缓存
	    	/*redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
	    	//清空用户登录Shiro权限缓存
			redisUtil.del(CommonConstant.PREFIX_USER_SHIRO_CACHE + sysUser.getId());
			//清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
			redisUtil.del(String.format("%s::%s", CacheConstant.SYS_USERS_CACHE, sysUser.getUsername()));*/
			//调用shiro的logout
			SecurityUtils.getSubject().logout();
	    	return InvokeResult.success("退出登录成功！");
	    }else {
	    	return InvokeResult.failure("Token无效!");
	    }
	}

	/**
	 * 获取访问量
	 * @return
	 */
	@GetMapping("loginfo")
	public InvokeResult loginfo() {

		JSONObject obj = new JSONObject();
		//update-begin--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
		// 获取一天的开始和结束时间
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date dayStart = calendar.getTime();
		calendar.add(Calendar.DATE, 1);
		Date dayEnd = calendar.getTime();
		// 获取系统访问记录
		Long totalVisitCount = logService.findTotalVisitCount();
		obj.put("totalVisitCount", totalVisitCount);
		Long todayVisitCount = logService.findTodayVisitCount(dayStart,dayEnd);
		obj.put("todayVisitCount", todayVisitCount);
		Long todayIp = logService.findTodayIp(dayStart,dayEnd);
		//update-end--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
		obj.put("todayIp", todayIp);
		return InvokeResult.success("登录成功", obj);
	}

	/**
	 * 获取访问量
	 * @return
	 */
	@GetMapping("visitInfo")
	public InvokeResult visitInfo() {

		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dayEnd = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date dayStart = calendar.getTime();
        List<Map<String,Object>> list = logService.findVisitCount(dayStart, dayEnd);
		return InvokeResult.success(null, oConvertUtils.toLowerCasePageList(list));
	}


	/**
	 * 登陆成功选择用户当前部门
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/selectDepart", method = RequestMethod.PUT)
	public InvokeResult selectDepart(@RequestBody SysUser user) {

		String username = user.getUsername();
		if(LangUtil.isEmpty(username)) {
			LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
			username = sysUser.getUsername();
		}
		String orgCode= user.getOrgCode();
		this.sysUserService.updateUserDepart(username, orgCode);
		SysUser sysUser = sysUserService.getUserByName(username);
		JSONObject obj = new JSONObject();
		obj.put("userInfo", sysUser);
		return InvokeResult.success(null,obj);
	}

	/**
	 * 短信登录接口
	 *
	 * @param jsonObject
	 * @return
	 */
	@PostMapping(value = "/sms")
	public InvokeResult sms(@RequestBody JSONObject jsonObject) {

		String mobile = jsonObject.get("mobile").toString();
		String smsmode=jsonObject.get("smsmode").toString();
		log.info(mobile);
		Object object = redisUtil.get(mobile);
		if (object != null) {
			return InvokeResult.failure("验证码10分钟内，仍然有效！");
		}

		//随机数
		String captcha = RandomUtil.randomNumbers(6);
		JSONObject obj = new JSONObject();
    	obj.put("code", captcha);
		try {
			boolean b = false;
			//注册模板
			if (CommonConstant.SMS_TPL_TYPE_1.equals(smsmode)) {
				SysUser sysUser = sysUserService.getUserByPhone(mobile);
				if(sysUser!=null) {
					sysBaseAPI.addLog("手机号已经注册，请直接登录！", CommonConstant.LOG_TYPE_1, null);
					return InvokeResult.failure("500", " 手机号已经注册，请直接登录！");
				}
				b = DySmsHelper.sendSms(mobile, obj, DySmsEnum.REGISTER_TEMPLATE_CODE);
			}else {
				//登录模式，校验用户有效性
				SysUser sysUser = sysUserService.getUserByPhone(mobile);
				InvokeResult result = sysUserService.checkUserIsEffective(sysUser);
				if(result.getSuccess()==0) {
					return result;
				}

				/**
				 * smsmode 短信模板方式  0 .登录模板、1.注册模板、2.忘记密码模板
				 */
				if (CommonConstant.SMS_TPL_TYPE_0.equals(smsmode)) {
					//登录模板
					b = DySmsHelper.sendSms(mobile, obj, DySmsEnum.LOGIN_TEMPLATE_CODE);
				} else if(CommonConstant.SMS_TPL_TYPE_2.equals(smsmode)) {
					//忘记密码模板
					b = DySmsHelper.sendSms(mobile, obj, DySmsEnum.FORGET_PASSWORD_TEMPLATE_CODE);
				}
			}

			if (b == false) {
				return InvokeResult.failure("短信验证码发送失败,请稍后重试");
			}
			//验证码10分钟内有效
			redisUtil.set(mobile, captcha, 600);
			//update-begin--Author:scott  Date:20190812 for：issues#391
			//result.setResult(captcha);
			//update-end--Author:scott  Date:20190812 for：issues#391
		} catch (ClientException e) {
			e.printStackTrace();
			return InvokeResult.failure("500", " 短信接口未配置，请联系管理员！");
		}
		return InvokeResult.success();
	}


	/**
	 * 手机号登录接口
	 *
	 * @param jsonObject
	 * @return
	 */
	@ApiOperation("手机号登录接口")
	@PostMapping("/phoneLogin")
	public InvokeResult phoneLogin(@RequestBody JSONObject jsonObject) {

		String phone = jsonObject.getString("mobile");

		//校验用户有效性
		SysUser sysUser = sysUserService.getUserByPhone(phone);
		InvokeResult result = sysUserService.checkUserIsEffective(sysUser);
		if(result.getSuccess()==0) {
			return result;
		}

		String smscode = jsonObject.getString("captcha");
		Object code = redisUtil.get(phone);
		if (!smscode.equals(code)) {
			result.setMessage("手机验证码错误");
			return result;
		}
		//用户信息
		userInfo(sysUser, result);
		//添加日志
		sysBaseAPI.addLog("用户名: " + sysUser.getUsername() + ",登录成功！", CommonConstant.LOG_TYPE_1, null);

		return result;
	}


	/**
	 * 用户信息
	 *
	 * @param sysUser
	 * @param result
	 * @return
	 */
	private void userInfo(SysUser sysUser, InvokeResult result) {
		String syspassword = sysUser.getPassword();
		String username = sysUser.getUsername();
		// 生成token
		String token = JwtUtil.sign(username, syspassword);
        // 设置token缓存有效时间
		/*redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
		redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);*/

		// 获取用户部门信息
		JSONObject obj = new JSONObject();
		List<SysDepart> departs = sysDepartService.queryUserDeparts(sysUser.getId());
		obj.put("departs", departs);
		if (departs == null || departs.size() == 0) {
			obj.put("multi_depart", 0);
		} else if (departs.size() == 1) {
			sysUserService.updateUserDepart(username, departs.get(0).getOrgCode());
			obj.put("multi_depart", 1);
		} else {
			obj.put("multi_depart", 2);
		}
		obj.put("token", token);
		obj.put("userInfo", sysUser);
		result.setData(obj);
	}

	/**
	 * 获取加密字符串
	 * @return
	 */
	@GetMapping(value = "/getEncryptedString")
	public InvokeResult getEncryptedString(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("key", EncryptedString.key);
		map.put("iv",EncryptedString.iv);
		return InvokeResult.success(null, map);
	}

	/**
	 * 获取校验码
	 */
	@ApiOperation("获取验证码")
	@GetMapping(value = "/getCheckCode")
	public InvokeResult getCheckCode(){

		Map<String,String> map = new HashMap<String,String>();
		try {
			String code = RandomUtil.randomString(BASE_CHECK_CODES,4);
			String key = MD5Util.MD5Encode(code+System.currentTimeMillis(), "utf-8");
			//redisUtil.set(key, code, 60);
			map.put("key", key);
			map.put("code",code);
			return InvokeResult.success(null, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InvokeResult.failure("error");
	}

	/**
	 * app登录
	 * @param sysLoginModel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mLogin", method = RequestMethod.POST)
	public InvokeResult mLogin(@RequestBody SysLoginModel sysLoginModel) throws Exception {

		String username = sysLoginModel.getUsername();
		String password = sysLoginModel.getPassword();

		//1. 校验用户是否有效
		SysUser sysUser = sysUserService.getUserByName(username);
		InvokeResult result = sysUserService.checkUserIsEffective(sysUser);
		if(result.getSuccess()==0) {
			return result;
		}

		//2. 校验用户名或密码是否正确
		String userpassword = PasswordUtil.encrypt(username, password, sysUser.getSalt());
		String syspassword = sysUser.getPassword();
		if (!syspassword.equals(userpassword)) {
			return InvokeResult.failure("500", "用户名或密码错误");
		}

		String orgCode = sysUser.getOrgCode();
		if(LangUtil.isEmpty(orgCode)) {
			//如果当前用户无选择部门 查看部门关联信息
			List<SysDepart> departs = sysDepartService.queryUserDeparts(sysUser.getId());
			if (departs == null || departs.size() == 0) {
				return InvokeResult.failure("500", "用户暂未归属部门,不可登录!");
			}
			orgCode = departs.get(0).getOrgCode();
			sysUser.setOrgCode(orgCode);
			this.sysUserService.updateUserDepart(username, orgCode);
		}
		JSONObject obj = new JSONObject();
		//用户登录信息
		obj.put("userInfo", sysUser);

		// 生成token
		String token = JwtUtil.sign(username, syspassword);
		// 设置超时时间
		redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
		redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);
		//token 信息
		obj.put("token", token);
		sysBaseAPI.addLog("用户名: " + username + ",登录成功[移动端]！", CommonConstant.LOG_TYPE_1, null);
		return InvokeResult.success(null, obj);
	}

	/**
	 * 获取当前登录账号信息
	 */
	@ApiOperation("获取在线用户信息")
	@GetMapping(value = "/getLoginUser")
	public InvokeResult getLoginUser(){

		try {
			LoginUser user = (LoginUser)SecurityUtils.getSubject().getPrincipal();
			return InvokeResult.success(null, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InvokeResult.failure("error");
	}

}
