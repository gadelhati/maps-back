package com.maps.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Configuration
@RequiredArgsConstructor
public class ConfigurationOpenAPI {
    @Value("${maps.openapi.homolog-url}")
    private String homologUrl;
    @Value("${maps.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Maps API")
                        .version("0.0.1")
                        .contact(new Contact()
                                .email("gadelha.ti@gmail.com")
                                .name("Gadelha TI")
                                .url("https://www.gadelha.eti.br"))
                        .description("This API exposes endpoints to manage researches.").termsOfService("https://www.gadelha.eti.br")
                        .license(new License()
                                .name("MIT License")
                                .url("https://choosealicense.com/licenses/mit/")))
                .servers(List.of(
                        new Server().url(homologUrl).description("Server URL in approval environment"),
                        new Server().url(prodUrl).description("Server URL in Item environment")));
    }
}