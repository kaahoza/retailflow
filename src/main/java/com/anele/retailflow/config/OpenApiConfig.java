package com.anele.retailflow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI retailFlowOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RetailFlow API")
                        .description("Backend platform for retail inventory, sales, and business intelligence")
                        .version("v0.1"));
    }
}