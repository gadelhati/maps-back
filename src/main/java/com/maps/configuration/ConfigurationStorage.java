package com.maps.configuration;

import com.maps.service.ServiceStorage;
import lombok.Getter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@Configuration
public class ConfigurationStorage {

    @Bean
    CommandLineRunner init(ServiceStorage serviceStorage) {
        return (args) -> {
            serviceStorage.deleteAll();
            serviceStorage.init();
        };
    }
    private String location = "upload-dir";

    public void setLocation(String location) {
        this.location = location;
    }
}