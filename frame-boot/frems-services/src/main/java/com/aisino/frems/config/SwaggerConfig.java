package com.aisino.frems.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * REST APIs文档生成工具 【页面链接】 http://localhost:8080/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${asframework.swagger.enable:true}")
	private boolean swaggerEnable;
	@Value("${asframework.swagger.basePackage:}")
	private String basePackage;
	@Value("${asframework.swagger.title:Swagger API}")
	private String title;
	@Value("${asframework.swagger.description:Swagger API test.}")
	private String description;
	@Value("${asframework.swagger.version:1.0}")
	private String version;

	@Bean
	public Docket swaggerSpringMvcPlugin() {
		ParameterBuilder ticketPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		ticketPar.name("token").defaultValue("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiY2xpZW50SWQiOiIxMjcuMC4wLjEiLCJ1c2VySWQiOiIxIiwiaWF0IjoxNTcyMjQ0OTgxLCJqdGkiOiJiMjlmMGMzMS00NTFhLTRmMzgtYThhNi04YTk0ZjMzMWI5ZTUifQ.uu7f08woePqfrR6uccDqtZhD8gR_Bi07f0AsDtyO730").description("校验码")
				.modelRef(new ModelRef("string")).parameterType("header")
				.required(false);
		pars.add(ticketPar.build());
		return new Docket(DocumentationType.SWAGGER_2).enable(swaggerEnable).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.paths(PathSelectors.any()).build()
				.globalOperationParameters(pars);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(title)
				.description(description)
				.version(version)
				.build();
	}
}
