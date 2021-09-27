package com.aisino.frems.common.ws;

import com.aisino.frems.common.ws.dto.WsResultBase;
import com.alibaba.fastjson.JSONObject;
import org.asframework.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录以及接口调用公共方法.
 */
@DependsOn("springContextUtils")
public class EecmiswsCaller {

    protected static final Logger logger = LoggerFactory.getLogger(EecmiswsCaller.class.getName());

    /**
     * 登录全国系统接口的会话ID
     */
    private static String sessionId;
    /**
     * ws调用器
     */
    private static EecmiswsStub stub;
    private static RestTemplate restTemplate;

    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        //设置读超时时间，单位：毫秒
        requestFactory.setReadTimeout(10000);
        //设置连接超时时间，单位：毫秒
        requestFactory.setConnectTimeout(30000);
        // 添加转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
        //messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
    }

    /**
     * 全国系统接口统一调用方法
     *
     * @param args     接口参数
     * @param funccode 接口功能代码
     * @return 全国系统统一方法返回值
     */
    public static <T> WsResultBase<T> callUnimethod(String args, String funccode,
																										Class<? extends WsResultBase<T>> clazz)
            throws Exception {
        WsResultBase<T> wsResult = null;
        try {
            String result = unimethodCore(args, funccode, false);
            wsResult = JsonUtil.jsonToObject(result, clazz);
            if (WsStateConst.STATE_ERROR_SESSION.equals(wsResult.getState())) {
                result = unimethodCore(args, funccode, true);
                wsResult = JsonUtil.jsonToObject(result, clazz);
            }
            wsResult.setResultStr(result);
        } catch (RemoteException e) {
            logger.error("全国系统接口调用失败", e);
            throw e;
        }
        return wsResult;
    }

    /**
     * 全国系统接口统一调用方法2
     *
     * @param args     接口参数
     * @param funccode 接口功能代码
     * @return 返回jsonobject格式数据
     */
    public static JSONObject callUnimethod2(String args, String funccode)
            throws Exception {
        JSONObject wsResult = null;
        try {
            String result = unimethodCore(args, funccode, false);
            wsResult = JsonUtil.toJSONObject(result);
        } catch (RemoteException e) {
            logger.error("全国系统接口调用失败", e);
            throw e;
        }
        return wsResult;
    }

    /**
     * 全国系统接口调用方法基本方法
     *
     * @param args     接口参数
     * @param funccode 接口功能代码
     * @param reLogin  是否重新登录
     * @return 全国系统统一方法返回值
     */
    private static String unimethodCore(String args, String funccode, boolean reLogin) throws Exception {
        String result = null;
        try {
            if (null == sessionId || reLogin) {
                LoginNew();
            }
            EecmiswsStub.Unimethod unimethod = new EecmiswsStub.Unimethod();
            unimethod.setSessionId(getSessionId());
            unimethod.setFunccode(funccode);
            unimethod.setArgs(args);
            result = stub.unimethod(unimethod).get_return();
        } catch (RemoteException e) {
            logger.error("全国系统接口调用失败", e);
            throw e;
        }
        return result;
    }

    /**
     * 全国系统接口登陆方法
     */
   /* private static void wsLogin() throws Exception {
        try {
            String username = null, password = null, targetEnpoint = null;
            EecmisConfig eecmisConfig = SpringApplicationContextHolder.getBean(EecmisConfig.class);
            username = eecmisConfig.getUsername();
            password = eecmisConfig.getPassword();
            targetEnpoint = eecmisConfig.getTargetEnpoint();

            if (null == stub) {
                stub = new EecmiswsStub(targetEnpoint);
            }
            EecmiswsStub.Login login = new EecmiswsStub.Login();
            login.setLoginName(username);
            login.setPassword(password);
            sessionId = stub.login(login).get_return();
        } catch (Exception e) {
            logger.error("全国系统接口登录失败", e);
            throw e;
        }
    }*/

    public static void LoginNew() throws Exception {
        try {
            String targetEnpoint = "http://192.168.7.14/eecmisws/services/eecmisws?wsdl";
            //调用http请求
            try {
                String result = restTemplate.postForObject("http://127.0.0.1:7788/GetCertificateInfoPKCS7", null,
										String.class);
                if (null != result) {
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    //调用成功
                    if (1 == jsonObject.getInteger("success")) {
                        //获取签名数据
                        String cert = jsonObject.getString("data");
                        if (null == stub) {
                            stub = new EecmiswsStub(targetEnpoint);
                        }
                        EecmiswsStub.LoginNew loginNew = new EecmiswsStub.LoginNew();
                        loginNew.setArgs(cert);
                        String temp = stub.loginNew(loginNew).get_return();
                        if (temp != null) {
                            JSONObject jsonObject1 = JSONObject.parseObject(temp);
                            if (1 == jsonObject1.getInteger("success")) {
                                sessionId = jsonObject1.getString("data");
                                logger.info("sessionId:" + sessionId);
                            } else {
                                logger.error("全国系统接口登录失败");
                            }
                        } else {
                            logger.error("签名数据为空");
                        }
                    }
                } else {
                    logger.error("调用签名接口失败");
                }
            } catch (Exception e) {
                logger.error("全国系统接口登录失败", e);
                throw e;
            }

        } catch (Exception e) {
            logger.error("全国系统接口登录失败", e);
            throw e;
        }
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        EecmiswsCaller.sessionId = sessionId;
    }

}
