package com.project.calculate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("MainPage");
        registry.addViewController("/client_card").setViewName("ClientCard");
        registry.addViewController("/add_client").setViewName("AddClient");
        registry.addViewController("/login").setViewName("login");
    }
}
