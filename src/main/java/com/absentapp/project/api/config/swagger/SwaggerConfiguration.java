package com.absentapp.project.api.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createAPIScheme()))
                .info(new Info()
                        .title("API AbsentApp")
                        .description("API para controle de presença em salas e eventos em geral.")
                        .termsOfService("")
                        .version("0.0.1")
                        .license(new License()
                                .name("Licença de uso proprietário")
                                .url("https://absentapp.com/licenca-de-uso"))
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .email("contatoabsentapp@email.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Ambiente de desenvolvimento"),
                        new Server()
                                .url("https://test-absentapp.onrender.com")
                                .description("Ambiente de testes")));
    }

    private SecurityScheme createAPIScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
