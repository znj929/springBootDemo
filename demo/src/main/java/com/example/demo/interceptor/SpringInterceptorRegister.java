package com.example.demo.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

/** 
 *  	
 *  这个类就是类似于xml里面的拦截器定义
 */
@Slf4j
@EnableWebMvc
@Configuration
public class SpringInterceptorRegister implements  WebMvcConfigurer  {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/*
		 * // TODO Auto-generated method stub
		 * registry.addResourceHandler("/templates/**,/static/**")
		 * .addResourceLocations("classpath:/templates/")
		 * .addResourceLocations("classpath:/static/**");
		 * super.addResourceHandlers(registry);
		 */
    	registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

}
