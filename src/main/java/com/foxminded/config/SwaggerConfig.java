package com.foxminded.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Schedule API")
                .description("Operations with a schedule for training")
                .version("1.0")
                .contact(apiContact());
    }

    private Contact apiContact() {
        return new Contact()
                .name("Yura Odnoroh")
                .email("odnorog.yu@gmail.com")
                .url("https://github.com/YuraTy");
    }
}
