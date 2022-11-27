package com.dsumtsov.restful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@ConfigurationPropertiesScan
public class RestfulServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulServiceApplication.class, args);
	}

}
