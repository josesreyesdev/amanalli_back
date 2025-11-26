package com.amanalli.back.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // más flexible que allowedOrigins
                .allowedMethods("*")        // permite todos los métodos
                .allowedHeaders("*");        // permite todos los headers
                //.allowCredentials(false);   // evita problemas de seguridad
    }
}
