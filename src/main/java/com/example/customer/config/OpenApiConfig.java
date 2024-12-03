package com.example.customer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Customer API")
                        .description("API para la gestión de clientes en el sistema")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Brigitte Mendez")
                                .email("mendezbrigitte13@gmail.com")
                                .url("https://github.com/BrigitteMendez1302/Java-Proyecto2-CustomerMicroservice"))
                );
    }
}