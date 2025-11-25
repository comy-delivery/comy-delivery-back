package com.comy_delivery_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class ComyDeliveryBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComyDeliveryBackApplication.class, args);
	}

}
