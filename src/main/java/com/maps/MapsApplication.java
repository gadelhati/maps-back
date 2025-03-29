package com.maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@SpringBootApplication
public class MapsApplication {

	private final static Logger LOGGER = LoggerFactory.getLogger(MapsApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting maps api");
		SpringApplication.run(MapsApplication.class, args);
		LOGGER.info("maps api started");
	}

}