package com.campax.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI skolyOpenAPI() {
		return new OpenAPI()
					 .info(new Info()
								 .title("Campax API")
								 .description("School Management System — REST API Documentation")
								 .version("1.0.0"));
	}
	
}