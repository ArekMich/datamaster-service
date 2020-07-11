package com.agh.dataminingservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class to build Swagger.
 *
 * @author Arkadiusz Michalik
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * List of resources for which authentication is not required to use Swagger.
     * Used in {@link SecurityConfig#configure(HttpSecurity)} method.
     */
    public static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    /**
     * @return A builder which is intended to be the primary interface into the swagger-springmvc framework.
     * Provides sensible defaults and convenience methods for configuration.
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
