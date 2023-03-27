package com.project.calculate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("MainPage");
        registry.addViewController("/addClient").setViewName("AddClient");
        registry.addViewController("/editClient").setViewName("editClient");
        registry.addViewController("/framePage").setViewName("framePage");
        registry.addViewController("/foundationPage").setViewName("foundationPage");
        registry.addViewController("/calculation").setViewName("calculation");
        registry.addViewController("/login").setViewName("login");
    }



}
