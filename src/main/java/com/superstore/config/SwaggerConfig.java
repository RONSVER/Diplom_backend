package com.superstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * Configuration class for Swagger API documentation.
 */
@Configuration
public class SwaggerConfig {
    /**
     * Defines the OpenAPI configuration for Swagger API documentation.
     *
     * @return An instance of OpenAPI containing the configured documentation.
     */
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");


        Info information = new Info()
                .title("Superstore API")
                .version("1.0")
                .description("This API exposes endpoints to manage employees.");
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
