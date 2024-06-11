package com.foundation.core.configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${author.fullName}")
    private String fullName;

    @Value("${author.email}")
    private String email;

    @Bean
    public OpenAPI baseOpenAPI() {
        Contact author = new Contact().name(fullName).email(email);
        return new OpenAPI().info(new Info().title("Foundation - Core")
                            .version("0.0.1")
                            .description("OpenAPI Swagger").contact(author));
    }
}
