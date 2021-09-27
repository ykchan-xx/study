package com.aisino.frems.common.util;

import com.aisino.frems.common.model.LoginUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.asframework.data.mybatis.model.BasePageQuery;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 查询参数处理
 *
 * @author hxq
 */
@Aspect
@Component
@Slf4j
public class QueryCondUtil {

    /**
     * 查询方法拦截
     *
     * @param joinPoint
     */
    @Before("execution(public * com.aisino.frems.modules.*.*Controller.*(..))")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        if ("listPage".equals(methodName)) {
            Object[] parameters = joinPoint.getArgs();
            if (parameters != null && parameters.length > 0) {
                for (Object o : parameters) {
                    if (o instanceof BasePageQuery) {
                        addUserConditions(o);
                        break;
                    }
                }
            }
        }
        if("queryPageList".equals(methodName)){
            Object[] parameters = joinPoint.getArgs();
            if (parameters != null && parameters.length > 0) {
                for (Object o : parameters) {
                        addUserConditions(o);
                        break;
                }
            }
        }
    }

    /**
     * 用户单位过滤条件
     *
     * @param obj
     */
    private void addUserConditions(Object obj) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (obj == null || loginUser == null) {
            return;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                if ("zhbh".equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(obj, loginUser.getZhbh());
                    field.setAccessible(false);
                }
                if ("qybh".equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(obj, loginUser.getQybh());
                    field.setAccessible(false);
                }
                if ("bmbh".equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(obj, loginUser.getBmbh());
                    field.setAccessible(false);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户单位过滤条件
     *
     * @param queryWrapper
     */
    public static void addUserConditions(QueryWrapper queryWrapper) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (queryWrapper == null || loginUser == null) {
            return;
        }
        if (loginUser.getZhbh() != null) {
            queryWrapper.eq("zhbh", loginUser.getZhbh());
        }
        if (loginUser.getQybh() != null) {
            queryWrapper.eq("qybh", loginUser.getQybh());
        }
        if (loginUser.getBmbh() != null) {
            queryWrapper.eq("bmbh", loginUser.getBmbh());
        }
    }

    /**
     * 用户单位过滤条件
     *
     * @param map
     */
    public static void addUserConditions(Map map) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (map == null || loginUser == null) {
            return;
        }
        map.put("zhbh", loginUser.getZhbh());
        map.put("qybh", loginUser.getQybh());
        map.put("bmbh", loginUser.getBmbh());
    }
}
