package com.example.nalsam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000","https://d15y1hflvb5egs.cloudfront.net/","https://udegaforyou.com/")
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");

    }
}
