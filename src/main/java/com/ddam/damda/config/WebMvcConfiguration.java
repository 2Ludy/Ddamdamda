package com.ddam.damda.config;

import java.util.Arrays;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

//import com.ssafy.interceptor.ConfirmInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Value("${company.origin-url}")
	private String allowOrginUrl;
	
	private final Logger logger = LoggerFactory.getLogger(WebMvcConfiguration.class);
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		    .allowedOrigins(allowOrginUrl)
				.allowedMethods(
						HttpMethod.GET.name(), 
						HttpMethod.POST.name(), 
						HttpMethod.PUT.name(),
						HttpMethod.DELETE.name(), 
						HttpMethod.HEAD.name(), 
						HttpMethod.OPTIONS.name(),
						HttpMethod.PATCH.name())
//				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
//				.allowedMethods("*")
				.maxAge(1800); // 1800초 동안 preflight 결과를 캐시에 저장
	}
	
}
