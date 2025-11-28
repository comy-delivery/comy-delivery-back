package com.comy_delivery_back;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class ComyDeliveryBackApplication {

	public static void main(String[] args) {
		MercadoPagoConfig.setAccessToken("APP_USR-6548025824607873-112721-733bf51959d7e660fd94765fc4adee11-3021366393");
		SpringApplication.run(ComyDeliveryBackApplication.class, args);

	}

}
