package com.superstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * Configuration class that defines the Swagger OpenAPI documentation for the Superstore API.
 *
 * The SwaggerConfig class provides a bean that defines the OpenAPI documentations for the Superstore API.
 * It sets the server URL and description, API title, version, and description.
 * The OpenAPI bean is used to generate the Swagger documentation of the API endpoints.
 *
 * This class is typically used in a Spring Boot application to configure the Swagger documentation.
 */
@Configuration
public class SwaggerConfig {

    private static final String SERVER_URL = "http://localhost:8080";
    private static final String SERVER_DESCRIPTION = "Development";

    private static final String API_TITLE = "Superstore API";
    private static final String API_VERSION = "1.0";
    private static final String API_DESCRIPTION = "This API exposes endpoints to manage employees.";

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl(SERVER_URL);
        server.setDescription(SERVER_DESCRIPTION);

        Info information = new Info()
                .title(API_TITLE)
                .version(API_VERSION)
                .description(API_DESCRIPTION);

        return new OpenAPI().info(information).servers(List.of(server));
    }
}
