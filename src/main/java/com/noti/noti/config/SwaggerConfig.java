package com.noti.noti.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  private String externalDocUrl;

  public SwaggerConfig(@Value("${externalUrl}") String externalDocUrl) {
    this.externalDocUrl = externalDocUrl;
  }

  @Bean
  public GroupedOpenApi teacherApi() {
    return GroupedOpenApi.builder()
        .group("NOTI-teacher")
        .pathsToMatch("/api/teacher/**")
        .build();
  }

  @Bean
  public GroupedOpenApi authApi() {
    return GroupedOpenApi.builder()
        .group("NOTI-auth")
        .pathsToMatch("/api/auth/**")
        .build();
  }

  @Bean
  public GroupedOpenApi classApi() {
    return GroupedOpenApi.builder()
        .group("NOTI-class")
        .pathsToMatch("/api/class/**")
        .build();
  }

  @Bean
  public GroupedOpenApi notificationApi() {
    return GroupedOpenApi.builder()
        .group("NOTI-notification")
        .pathsToMatch("/api/notification/**")
        .build();
  }

  @Bean
  public OpenAPI springNotiOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("NOTI API")
            .description("NOTI 서비 API 명세서")
            .version("V1")
            .license(new License().name("Apache 2.0").url("http://springdoc.org")))
        .externalDocs(new ExternalDocumentation()
            .description("NOTI Wiki Documentation")
            .url(externalDocUrl));
    // Authorization Header를 위한 설정 이후 추가
//        .components(new Components()
//            .addSecuritySchemes("bearer-key",
//                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
//                    .bearerFormat("JWT")));
  }
}
