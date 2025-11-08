package com.maps.configuration;

import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Configuration
public class ConfigurationJts {
    @Bean
    public JtsModule jtsModule() {
        return new JtsModule();
    }
}
