package com.rekshinspringboot.firstSpring.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig {
//	
//	@Bean
//	public WebMvcConfigurer corsConfiguration() {
//		
//		return new WebMvcConfigurer() {
//			public void addCorsMapping(CorsRegistry registry) {
//			registry.addMapping("/**")
//					.allowedOrigins("http://localhost:5713")
//					.allowedMethods("*")
//					.allowedHeaders("*");
//			}
//		};
//	}
//}



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("*");
            }
        };
    }
}