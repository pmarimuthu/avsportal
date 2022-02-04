package com.avs.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class KanaksanInitializerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(
      SpringApplicationBuilder builder) {
        return builder.sources(KanaksanInitializerApplication.class);
    }
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                	.allowedOrigins("http://localhost:8080", "http://localhost:4000")
                	.allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.PATCH.name(), HttpMethod.DELETE.name(), HttpMethod.HEAD.name())
                	.maxAge(100000);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication sa = new SpringApplication(
        		KanaksanInitializerApplication.class);
        sa.run(args);
    }

    @RestController
    public static class WarInitializerController {

        @GetMapping("/")
        public String handler() {
           return "Hello, Kanaksan!!";
        }
    }
}