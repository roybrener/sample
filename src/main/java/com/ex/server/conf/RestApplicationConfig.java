package com.ex.server.conf;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by roy on 10/02/2015.
 */
@Configuration
@ComponentScan
public class RestApplicationConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
    }
}
