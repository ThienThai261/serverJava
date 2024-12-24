package com.example.demo22.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/assets/**")
                    .addResourceLocations("classpath:/assets/")
                    .setCachePeriod(3600)
                    .resourceChain(true);

            // Log để xác nhận configuration
            System.out.println("Resource handler configured:");
            System.out.println("Pattern: /assets/**");
            System.out.println("Location: classpath:/assets/");
        }
    }
