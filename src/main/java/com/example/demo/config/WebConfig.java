package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 這行的意思是 /images/xxx 會去抓 uploads/images/xxx
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads/images/");
    }
}
