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

    @Value("${app-info.title}")
    private String title;

    @Value("${app-info.version}")
    private String version;

    @Value("${app-info.description}")
    private String description;


    @Bean
    public OpenAPI baseOpenAPI() {
        Contact author = new Contact().name(fullName).email(email);
        return new OpenAPI().info(new Info().title(title)
                            .version(version)
                            .description(description)
                            .contact(author));
    }
}
