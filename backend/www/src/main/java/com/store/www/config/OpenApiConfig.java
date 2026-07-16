package com.store.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Configura Swagger/OpenAPI:
 *  - Datos generales de la API (título, versión).
 *  - Un esquema de seguridad "bearer JWT" para que Swagger muestre el botón
 *    "Authorize" donde se pega el token y lo añada a cada petición.
 */
@Configuration
public class OpenApiConfig {

    private static final String ESQUEMA = "bearerAuth";

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Tienda - Productos y Categorías")
                        .version("1.0")
                        .description("Backend de la prueba técnica full stack."))
                // exige el esquema por defecto en todos los endpoints
                .addSecurityItem(new SecurityRequirement().addList(ESQUEMA))
                .components(new Components()
                        .addSecuritySchemes(ESQUEMA, new SecurityScheme()
                                .name(ESQUEMA)
                                .type(SecurityScheme.Type.HTTP)   // autenticación HTTP
                                .scheme("bearer")                 // esquema Bearer
                                .bearerFormat("JWT")));           // el token es un JWT
    }
}
