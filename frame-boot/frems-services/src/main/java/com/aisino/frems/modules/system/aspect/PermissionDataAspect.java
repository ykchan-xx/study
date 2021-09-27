package com.aisino.frems.modules.system.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.aisino.frems.common.annotation.PermissionData;
import com.aisino.frems.common.model.SysUserCacheInfo;
import com.aisino.frems.modules.system.model.SysPermissionDataRuleModel;
import com.aisino.frems.modules.system.util.JeecgDataAutorUtils;
import com.aisino.frems.modules.system.util.JwtUtil;
import org.asframework.core.util.LangUtil;
import org.asframework.web.common.config.AppContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import com.aisino.frems.common.util.oConvertUtils;
import com.aisino.frems.modules.system.entity.SysPermission;
import com.aisino.frems.modules.system.entity.SysPermissionDataRule;
import com.aisino.frems.modules.system.service.ISysPermissionDataRuleService;
import com.aisino.frems.modules.system.service.ISysPermissionService;
import com.aisino.frems.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import lombok.extern.slf4j.Slf4j;

/**
  * 数据权限切面处理类
  *  当被请求的方法有注解PermissionData时,会在往当前request中写入数据权限信息
 * @Date 2019年4月10日
 * @Version: 1.0
 */
@Aspect
@Component
@Slf4j
public class PermissionDataAspect {

	@Autowired
	private ISysPermissionService sysPermissionService;

	@Autowired
	private ISysPermissionDataRuleService sysPermissionDataRuleService;

	@Autowired
	private ISysUserService sysUserService;

	@Pointcut("@annotation(com.aisino.frems.common.annotation.PermissionData)")
	public void pointCut() {

	}

	@Around("pointCut()")
	public Object arround(ProceedingJoinPoint point) throws  Throwable{
		HttpServletRequest request = AppContext.getRequest();
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		PermissionData pd = method.getAnnotation(PermissionData.class);
		String component = pd.pageComponent();
		List<SysPermission> currentSyspermission = null;
		if(LangUtil.isNotEmpty(component)) {
			//1.通过注解属性pageComponent 获取菜单
			LambdaQueryWrapper<SysPermission> query = new LambdaQueryWrapper<SysPermission>();
			query.eq(SysPermission::getDelFlag,0);
			query.eq(SysPermission::getComponent, component);
			currentSyspermission = sysPermissionService.list(query);
		}else {
			String requestMethod = request.getMethod();
			String requestPath = request.getRequestURI().substring(request.getContextPath().length());
			requestPath = filterUrl(requestPath);
			log.info("拦截请求>>"+requestPath+";请求类型>>"+requestMethod);
			//1.直接通过前端请求地址查询菜单
			LambdaQueryWrapper<SysPermission> query = new LambdaQueryWrapper<SysPermission>();
			query.eq(SysPermission::getMenuType,2);
			query.eq(SysPermission::getDelFlag,0);
			query.eq(SysPermission::getUrl, requestPath);
			currentSyspermission = sysPermissionService.list(query);
			//2.未找到 再通过正则匹配获取菜单
			if(currentSyspermission==null || currentSyspermission.size()==0) {
				String regUrl = getRegexpUrl(requestPath);
				if(regUrl!=null) {
					currentSyspermission = sysPermissionService.list(new LambdaQueryWrapper<SysPermission>().eq(SysPermission::getMenuType,2).eq(SysPermission::getUrl, regUrl).eq(SysPermission::getDelFlag,0));
				}
			}
		}
		//3.通过用户名+菜单ID 找到权限配置信息 放到request中去
		if(currentSyspermission!=null && currentSyspermission.size()>0) {
			String username = JwtUtil.getUserNameByToken(request);
			List<SysPermissionDataRuleModel> dataRules = new ArrayList<SysPermissionDataRuleModel>();
			for (SysPermission sysPermission : currentSyspermission) {
				// update-begin--Author:scott Date:20191119 for：数据权限规则编码不规范，项目存在相同包名和类名 #722
				List<SysPermissionDataRule> temp = sysPermissionDataRuleService.queryPermissionDataRules(username, sysPermission.getId());
				if(temp!=null && temp.size()>0) {
					//dataRules.addAll(temp);
					dataRules = oConvertUtils.entityListToModelList(temp,SysPermissionDataRuleModel.class);
				}
				// update-end--Author:scott Date:20191119 for：数据权限规则编码不规范，项目存在相同包名和类名 #722
			}
			if(dataRules!=null && dataRules.size()>0) {
				JeecgDataAutorUtils.installDataSearchConditon(request, dataRules);
				SysUserCacheInfo userinfo = sysUserService.getCacheUser(username);
				JeecgDataAutorUtils.installUserInfo(request, userinfo);
			}
		}
		return  point.proceed();
	}

	private String filterUrl(String requestPath){
		String url = "";
		if(LangUtil.isNotEmpty(requestPath)){
			url = requestPath.replace("\\", "/");
			url = requestPath.replace("//", "/");
			if(url.indexOf("//")>=0){
				url = filterUrl(url);
			}
			/*if(url.startsWith("/")){
				url=url.substring(1);
			}*/
		}
		return url;
	}

	/**
	 * 获取请求地址
	 * @param request
	 * @return
	 */
	private String getJgAuthRequsetPath(HttpServletRequest request) {
		String queryString = request.getQueryString();
		String requestPath = request.getRequestURI();
		if(LangUtil.isNotEmpty(queryString)){
			requestPath += "?" + queryString;
		}
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数(保留一个参数) 例如：loginController.do?login
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		if(requestPath.indexOf("=")!=-1){
			if(requestPath.indexOf(".do")!=-1){
				requestPath = requestPath.substring(0,requestPath.indexOf(".do")+3);
			}else{
				requestPath = requestPath.substring(0,requestPath.indexOf("?"));
			}
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return filterUrl(requestPath);
	}

	private boolean moHuContain(List<String> list,String key){
		for(String str : list){
			if(key.contains(str)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 匹配前端传过来的地址 匹配成功返回正则地址
	 * AntPathMatcher匹配地址
	 *()* 匹配0个或多个字符
	 *()**匹配0个或多个目录
	 */
	private String getRegexpUrl(String url) {
		List<String> list = sysPermissionService.queryPermissionUrlWithStar();
		if(list!=null && list.size()>0) {
			for (String p : list) {
				PathMatcher matcher = new AntPathMatcher();
				if(matcher.match(p, url)) {
					return p;
				}
			}
		}
		return null;
	}

}
