package com.maps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Slf4j
@SpringBootApplication
public class MapsApplication {
	
	public static void main(String[] args) {
		log.info("Starting maps api");
		SpringApplication.run(MapsApplication.class, args);
		log.info("maps api started");
	}

}
