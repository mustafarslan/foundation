package com.foundation.core.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {
    // Todo: config properties
    @Value("${author.fullname}")
    private String authorFullName;

    @Bean
    public OpenAPI baseOpenAPI() {
        Contact author = new Contact();
        return new OpenAPI().components(new Components())
                .info(new Info().title("Foundation - Core")
                                .version("0.0.1")
                                .description("OpenAPI Swagger"));
    }
}
