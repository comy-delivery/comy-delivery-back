package com.comy_delivery_back.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact =  new Contact()
                .name("Equipe: Arthur, Emilio, Heloisa, Jude, Sinara")
                .email("dev.hmtvrs@gmail.com")
                .url("url....");
        return new OpenAPI()

                .info(new Info()
                        .title("Comy Delivery API")
                        .version("1.0")
                        .description("API de delivery de comida")
                        .contact(contact))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
