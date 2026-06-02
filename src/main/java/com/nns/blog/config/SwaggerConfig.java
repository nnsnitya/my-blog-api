package com.nns.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {

        return new OpenAPI()
                .info(new Info()
                        .title("My Blog Api")
                        .version("v1")
                        .description("My Api for Blog Application")
                        .contact(new Contact()
                                .name("Nityanand Singh")
                                .email("nitya@gmail.com")
                        )
                );
    }
}
