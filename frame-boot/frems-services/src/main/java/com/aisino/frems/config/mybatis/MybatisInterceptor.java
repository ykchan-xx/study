package com.aisino.frems.config.mybatis;

import com.aisino.frems.common.constant.CommonConstant;
import com.aisino.frems.common.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.shiro.SecurityUtils;
import org.asframework.common.util.AsBeanUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

/**
 * mybatis拦截器，自动注入创建人、创建时间、修改人、修改时间
 */
@Slf4j
@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MybatisInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		String sqlId = mappedStatement.getId();
		log.debug("sqlId: " + sqlId);
		SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
		Object parameter = invocation.getArgs()[1];

		if (parameter == null) {
			return invocation.proceed();
		}
		if (SqlCommandType.INSERT == sqlCommandType) {
			LoginUser sysUser = this.getLoginUser();
			Field[] fields = AsBeanUtil.getAllFields(parameter.getClass());
			for (Field field : fields) {
				try {
					// 注入创建人
					if ("createBy".equals(field.getName())) {
						field.setAccessible(true);
						Object local_createBy = field.get(parameter);
						field.setAccessible(false);
						if (local_createBy == null || local_createBy.equals("")) {
							if (sysUser != null) {
								// 登录人账号
								field.setAccessible(true);
								field.set(parameter, sysUser.getUsername());
								field.setAccessible(false);
							}
						}
					} // 注入创建时间
					else if ("createTime".equals(field.getName())) {
						field.setAccessible(true);
						Object local_createDate = field.get(parameter);
						field.setAccessible(false);
						if (local_createDate == null || local_createDate.equals("")) {
							field.setAccessible(true);
							field.set(parameter, new Date());
							field.setAccessible(false);
						}
					} //注入部门编码
					else if ("sysOrgCode".equals(field.getName())) {
						field.setAccessible(true);
						Object local_sysOrgCode = field.get(parameter);
						field.setAccessible(false);
						if (local_sysOrgCode == null || local_sysOrgCode.equals("")) {
							// 获取登录用户信息
							if (sysUser != null) {
								field.setAccessible(true);
								field.set(parameter, sysUser.getOrgCode());
								field.setAccessible(false);
							}
						}
					}// 删除状态(0-正常,1-已删除)
					else if("delFlag".equals(field.getName())) {
                        field.setAccessible(true);
                        Object local_del = field.get(parameter);
                        field.setAccessible(false);
                        if (local_del == null || local_del.equals("")) {
                            field.setAccessible(true);
                            field.set(parameter, CommonConstant.DEL_FLAG_0);
                            field.setAccessible(false);
                        }
                    }// 租户编号
					else if("zhbh".equals(field.getName())) {
						field.setAccessible(true);
						Object local_del = field.get(parameter);
						field.setAccessible(false);
						if (local_del == null || local_del.equals("")) {
							field.setAccessible(true);
							field.set(parameter, sysUser.getZhbh ());
							field.setAccessible(false);
						}
					}// 企业编号
					else if("qybh".equals(field.getName())) {
						field.setAccessible(true);
						Object local_del = field.get(parameter);
						field.setAccessible(false);
						if (local_del == null || local_del.equals("")) {
							field.setAccessible(true);
							field.set(parameter, sysUser.getQybh ());
							field.setAccessible(false);
						}
					}// 部门编号
					else if("bmbh".equals(field.getName())) {
						field.setAccessible(true);
						Object local_del = field.get(parameter);
						field.setAccessible(false);
						if (local_del == null || local_del.equals("")) {
							field.setAccessible(true);
							field.set(parameter, sysUser.getBmbh ());
							field.setAccessible(false);
						}
					}
				} catch (Exception e) {
				}
			}
		}
		if (SqlCommandType.UPDATE == sqlCommandType) {
			LoginUser sysUser = this.getLoginUser();
			Field[] fields;
			if (parameter instanceof ParamMap) {
				ParamMap<?> p = (ParamMap<?>) parameter;
				if (p.containsKey("et")) {
					parameter = p.get("et");
				} else {
					parameter = p.get("param1");
				}
				if (parameter == null) {
					return invocation.proceed();
				}
				fields = AsBeanUtil.getAllFields(parameter.getClass());
			} else {
				fields = AsBeanUtil.getAllFields(parameter.getClass());
			}

			for (Field field : fields) {
				try {
					if ("updateBy".equals(field.getName())) {
						//获取登录用户信息
						if (sysUser != null) {
							// 登录账号
							field.setAccessible(true);
							field.set(parameter, sysUser.getUsername());
							field.setAccessible(false);
						}
					}
					if ("updateTime".equals(field.getName())) {
						field.setAccessible(true);
						field.set(parameter, new Date());
						field.setAccessible(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// do nothing
	}

	private LoginUser getLoginUser() {
		LoginUser sysUser = null;
		try {
			sysUser = SecurityUtils.getSubject().getPrincipal() != null ? (LoginUser) SecurityUtils.getSubject().getPrincipal() : null;
		} catch (Exception e) {
			sysUser = null;
		}
		return sysUser;
	}

}
