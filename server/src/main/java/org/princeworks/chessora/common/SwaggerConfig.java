package org.princeworks.chessora.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    SecurityScheme bearerScheme =
        new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .description("JWT bearer token");

    SecurityRequirement bearerRequirement =
        new SecurityRequirement().addList("Bearer Authentication");

    return new OpenAPI()
        .info(
            new Info()
                .title("Chessora Api documentation")
                .version("1.0")
                .description("")
                .contact(
                    new Contact()
                        .name("Prince Pal")
                        .email("princepaldev@gmail.com")
                        .url("www.princepaldev.engineer")))
        .components(new Components().addSecuritySchemes("Bearer Authentication", bearerScheme))
        .addSecurityItem(bearerRequirement);
  }
}
