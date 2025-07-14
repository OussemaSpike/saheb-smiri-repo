package com.wassefchargui.department_service.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;

@Configuration
@EnableConfigurationProperties(OpenApiConfigurationProperties.class)
public class OpenApiConfig {


    private final OpenApiConfigurationProperties properties;

    public OpenApiConfig(MappingJackson2HttpMessageConverter converter, OpenApiConfigurationProperties properties) {
        this.properties = properties;
        var supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
        supportedMediaTypes.add(new MediaType("application", "octet-stream"));
        converter.setSupportedMediaTypes(supportedMediaTypes);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(properties.getTitle())
                        .version(properties.getApiVersion())
                        .description(properties.getDescription())
                        .termsOfService("Terms of service")
                        .license(new License().name("Licence name").url("https://some-url.com")))
                .addSecurityItem(new SecurityRequirement().addList("keycloak"))
                .schemaRequirement(
                        "keycloak",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("http://localhost:8080/realms/wassefchargui/protocol/openid-connect/auth")
                                        .tokenUrl("http://localhost:8080/realms/wassefchargui/protocol/openid-connect/token")
                                                .scopes(new Scopes()
                                                        .addString("openid", "OpenID Connect scope")
                                                        .addString("profile", "Profile access")))));
    }
}
