package com.comy_delivery_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ComyDeliveryBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComyDeliveryBackApplication.class, args);
	}

}
