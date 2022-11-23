package com.cw.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@Configuration
public class OrderApplication {
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
	@Bean
	public Docket setSwaggerConfig(){

		//return a docket instance
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.ant("/orders/**")).apis(RequestHandlerSelectors.basePackage("com.cw.order")).build()
				.apiInfo(apiDetails());
	}

	public ApiInfo apiDetails() {

		//List<VendorExtension> vendorExtensions = new ArrayList<>();
		Contact contact = new Contact(
				"Divya",
				"",
				"bb@g.com"
		);
		ApiInfo apiInfo = new ApiInfo(
				"Order Service API",
				"sample API for car wash web development project", "1.0",
				"free to use", contact,
				"", "", Collections.emptyList());
		return apiInfo;
	}
}
