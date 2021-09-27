# as-webmvc-1.1.0.jar 使用说明

- org.asframework.web.common.config.AppContext
```
AppContext.getBean(String);
AppContext.getBean(Class);
AppContext.getRequest();//HttpServletRequest
```
- 国际化工具类：org.asframework.web.common.util.I18nMessage
```
I18nMessage.getMessage(code);
I18nMessage.getMessage(code, params[]);
```