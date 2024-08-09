//package com.example.demo.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//
//@Configuration
//public class SwaggerCOnfig {
//
//
//
//	@Bean
//	public OpenAPI myOpenAPI() {
//		return new OpenAPI().info(
//				new Info()
//				.title("quan")
//				.version("1.0.0")
//				.description("API documentation for quan")
//				.license(new License().name("Apache 2.0").url("https://springdoc.org")));
//	}
//
//}