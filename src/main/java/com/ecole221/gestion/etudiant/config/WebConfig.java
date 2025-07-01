package com.ecole221.gestion.etudiant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    private static final String[] ALLOWED_ORIGINS = {
        "http://localhost:8088",
            "http://localhost:8083/swagger-ui/index.html",
        "http://localhost:8085", // gestion-inscription
        // "http://localhost:8083", // gestion-etudiant
       
    };
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Autorise tous les endpoints
                        .allowedOrigins(ALLOWED_ORIGINS) // Autorise les origines spécifiées
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
