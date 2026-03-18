package io.github.leo.topichub.config.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfigurations {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(
                        List.of(new Server().url("http://localhost:8080").description("Local Development Environment")))
                .components(new Components()
                        .addSecuritySchemes(
                                "bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT authentication token. Format: Bearer <token>")))
                .info(new Info()
                        .title("Topic Hub API")
                        .version("v1.0.0")
                        .description("""
                            ## Overview
                            **Topic Hub** is a RESTful API designed for a developer forum platform.
                            It allows users to create and manage discussion topics, post replies, manage their profiles, and interact with the community.

                            ## Main Features
                            - Create, view, update, and manage discussion topics
                            - Post and manage replies within topics
                            - Manage user profiles
                            - Moderate and organize forum content

                            ## Authentication
                            Most endpoints require authentication using a **JWT Bearer Token**.

                            ### How to authenticate
                            1. Register a new account using `POST /auth/register`
                               or log in using `POST /auth/login`
                            2. Copy the `token` returned in the response
                            3. Click the **Authorize** (🔒) button at the top of this page
                            4. Enter the token in the following format:

                               `Bearer <your_token>`

                            Once authorized, the token will be automatically included in requests to protected endpoints.
                            """)
                        .contact(new Contact()
                                .name("Topic Hub — Backend Team")
                                .email("backend@topichub.dev")
                                .url("https://github.com/leonardocurtis/topic-hub-api"))
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("1 - Authentication")
                .pathsToMatch("/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi topicsApi() {
        return GroupedOpenApi.builder()
                .group("2 - Topics and Responses")
                .pathsToMatch("/topics/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("3 - Admin Users")
                .pathsToMatch("/admin/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi usersApi() {
        return GroupedOpenApi.builder()
                .group("4 - Users")
                .pathsToMatch("/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi categoriesApi() {
        return GroupedOpenApi.builder()
                .group("5 - Categories")
                .pathsToMatch("/categories/**")
                .build();
    }

    @Bean
    public GroupedOpenApi coursesApi() {
        return GroupedOpenApi.builder()
                .group("4 - Courses")
                .pathsToMatch("/courses/**")
                .build();
    }
}
