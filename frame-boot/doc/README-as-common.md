# as-common-1.1.0.jar 使用说明

##基类
- 接口返回对象：org.asframework.base.model.InvokeResult
```
return InvokeResult.success();
return InvokeResult.success(message);
return InvokeResult.success(message, data);
return InvokeResult.success(null, data);
return InvokeResult.failure(message);
```
##工具类
- Bean具类：org.asframework.common.util.AsBeanUtil

- 中国身份证判断工具类：org.asframework.common.util.IDCardUtil

- IP获取工具类：org.asframework.common.util.IPUtil
```
// 获取服务器IP
IPUtil.getServerIp();
// 获取客户端IP
IPUtil.getClientIp(HttpServletRequest);
```

- json处理工具类：org.asframework.common.util.JsonUtil

- Ognl处理工具类：org.asframework.common.util.OgnlParser

- 企业社会信用代码处理工具类：org.asframework.common.util.UniscidUtil
