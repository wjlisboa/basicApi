package com.basicapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BasicApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicApiApplication.class, args);
	}

}
