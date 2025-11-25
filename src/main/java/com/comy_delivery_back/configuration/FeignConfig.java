package com.comy_delivery_back.configuration;


import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Value("${awesome_key}")
    private String apiToken;

    @Bean
    public RequestInterceptor tokenInterceptor() {
        return requestTemplate -> {
            requestTemplate.query("token", apiToken);
        };
    }
}
