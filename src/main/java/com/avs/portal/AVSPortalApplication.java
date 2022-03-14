package com.avs.portal;

import java.sql.Timestamp;
import java.time.ZoneId;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.avs.portal.enums.LogStatusEnum;
import com.avs.portal.util.Logger;

@SpringBootApplication
public class AVSPortalApplication {

	public static void main(String... args) {
		Logger.log(LogStatusEnum.INFO, "main >", ZoneId.systemDefault() + "\n" +	new Timestamp(System.currentTimeMillis()));
		
		SpringApplication.run(AVSPortalApplication.class, args);
		Logger.log(LogStatusEnum.INFO, "main >", "AVA Portal Application Started ...");
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            	Logger.log(LogStatusEnum.INFO, "corsConfigurer", "CORS :: Registered mapping /** ");
                registry.addMapping("/**")
                	.allowedOrigins("http://localhost:3000", "http://localhost:4000")
                	.allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.PATCH.name(), HttpMethod.DELETE.name(), HttpMethod.HEAD.name())
                	.maxAge(100000);
            }
        };
    }


}
