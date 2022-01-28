package com.fincarebank.demouser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookUserApplication.class, args);
	}

}
