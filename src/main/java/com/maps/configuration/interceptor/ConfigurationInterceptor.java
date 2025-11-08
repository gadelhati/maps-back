package com.maps.configuration.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 **/

@Configuration
public class ConfigurationInterceptor implements WebMvcConfigurer {

    private final HibernateFilterInterceptor hibernateFilterInterceptor;

    public ConfigurationInterceptor(HibernateFilterInterceptor hibernateFilterInterceptor) {
        this.hibernateFilterInterceptor = hibernateFilterInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(hibernateFilterInterceptor);
    }
}
