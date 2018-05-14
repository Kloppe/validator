package com.gl365.validator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@ServletComponentScan({ "com.gl365.dao.monitor" })
@MapperScan("com.gl365.validator.mapper")
@EnableSwagger2
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
