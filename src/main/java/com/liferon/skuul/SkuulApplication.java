package com.liferon.skuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SkuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkuulApplication.class, args);
	}

}
