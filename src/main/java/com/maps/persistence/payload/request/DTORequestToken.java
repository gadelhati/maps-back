package com.maps.persistence.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Getter
public class DTORequestToken extends DTORequestIdentifiable {

    private final String tokenType = "Bearer ";
    private String accessToken;
    @NotNull
    private UUID refreshToken;
}
