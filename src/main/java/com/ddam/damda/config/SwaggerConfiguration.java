package com.ddam.damda.config;

import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;

//Swagger-UI 확인
//http://localhost/swagger-ui.html
//http://localhost:8080/swagger-ui.html
@Configuration
public class SwaggerConfiguration {
	@Value("${company.logo-url}")
	private String companyLogoUrl;
	@Value("${company.temp-url-port}")
	private String companyLogoUrlPort;
	
//http://localhost:8080/image/chos.png

	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Company API Documentation")
                .description("<h3>Ddamdamda Reference for Developers</h3>Swagger를 이용한 Dddamdamda API<br><img src='/image/chos.png' width=\"150\">") 
                //.description("<h3>SSAFY API Reference for Developers</h3>Swagger를 이용한 Health Board API<br><img src='"+companyLogoUrl+"' width=\"150\">") 
                //.description("API documentation for Company services")
                .version("v1.0")
                .contact(new Contact().name("Support Team").email("support@company.com").url("https://github.com/2Ludy/Ddamdamda"))
                .license(new License().name("Company License").url("https://github.com/2Ludy/Ddamdamda")))
            .externalDocs(new ExternalDocumentation()
                .description("Company Documentation")
                .url("https://github.com/2Ludy/Ddamdamda"))
            .externalDocs(new ExternalDocumentation()
                    .description("Company Logo")
                    .url(companyLogoUrl))
            .servers(List.of(
                new Server().url(companyLogoUrlPort).description("Production server"),
                new Server().url(companyLogoUrlPort).description("Staging server")));
    }
//	@Bean
//	public GroupedOpenApi publicApi() {
//		return GroupedOpenApi.builder().group("com.ddam.damda.board").pathsToMatch("/board/**").build();
//	}

	
}