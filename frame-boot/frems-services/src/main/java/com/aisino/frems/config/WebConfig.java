package com.aisino.frems.config;

import lombok.extern.slf4j.Slf4j;
import org.asframework.core.exception.auth.UserTokenInvalidException;
import org.asframework.security.EnableAsAuthorityConfiguration;
import org.asframework.security.auth.AsAuthorityConfigurer;
import org.asframework.security.auth.AuthInfo;
import org.asframework.security.auth.DefaultAsAuthorityConfigurerAdapter;
import org.asframework.security.interceptor.TokenAuthInterceptor;
import org.asframework.security.jwt.IJWTInfo;
import org.asframework.web.config.DruidMonitorConfig;
import org.asframework.web.config.GlobalExceptionConfig;
import org.asframework.web.config.I18nConfig;
import org.asframework.web.config.WebCommonConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;

/**
 * web配置<br/>
 * WebCommonConfig: fastjson、全局异常处理、日期格式转换<br/>
 * I18nConfig: 国际化<br/>
 * DruidMonitorConfig: druid<br/>
 *
 * @author wenzhaoming
 */
@Slf4j
@Configuration
@Import({WebCommonConfig.class, I18nConfig.class, DruidMonitorConfig.class, GlobalExceptionConfig.class})
//@EnableAsAuthorityConfiguration
public class WebConfig implements WebMvcConfigurer {

    @Value("${asframework.swagger.enable:false}")
    private boolean swaggerEnable;
    @Value("${asframework.token-expire:7200}")
    private int tokenExpire;

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
		/* 是否允许请求带有验证信息 */
        corsConfiguration.setAllowCredentials(true);
		/* 允许访问的客户端域名 */
        corsConfiguration.addAllowedOrigin("*");
		/* 允许服务端访问的客户端请求头 */
        corsConfiguration.addAllowedHeader("*");
		/* 允许访问的方法名,GET POST等 */
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    // token权限拦截器
//    @Bean
//    public HandlerInterceptor getTokenAuthInterceptor() {
//        return new TokenAuthInterceptor();
//    }
//
//    // token权限拦截器配置
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration ir = registry.addInterceptor(getTokenAuthInterceptor())
//                // 权限控制
//                .addPathPatterns("/**")
//                // druid监控页面不做权限控制
//                .excludePathPatterns("/druid/**");
//        if (swaggerEnable) {
//            // swagger不做权限校验
//            ir.excludePathPatterns("/swagger-ui.html/**", "/webjars/**", "/swagger-resources/**", "/v2/api-docs");
//        }
//    }
//
//    // 重写权限校验配置，覆盖默认的权限配置
//    @Bean
//    @Primary
//    public AsAuthorityConfigurer getAsAuthorityConfigurer() {
//        return new DefaultAsAuthorityConfigurerAdapter() {
//
//            @Override
//            public int getTokenExpire() {
//                return tokenExpire;
//            }
//
//            @Override
//            public boolean verifyToken(IJWTInfo jwtInfo, String token, HttpServletRequest request) throws UserTokenInvalidException {
//                return true;
//            }
//
//            @Override
//            public boolean verifyAuthority(AuthInfo authInfo, HttpServletRequest request) {
//                return true;
//            }
//
//            @Override
//            public boolean verifyClientIp(AuthInfo authInfo, HttpServletRequest request) {
//                return true;
//            }
//
//            @Override
//            public void successVerify(boolean ignoreUserToken, AuthInfo authInfo, HttpServletRequest request) {
//                // do nothing
//            }
//
//        };
//    }

}
