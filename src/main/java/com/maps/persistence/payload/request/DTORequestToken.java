package com.maps.persistence.payload.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

public record DTORequestToken (

    UUID id,
    String tokenType,
    String accessToken,
    @NotNull
    UUID refreshToken
) implements DTORequestIdentifiable {
    
    // Valor padrão para tokenType
    public DTORequestToken(UUID id, String accessToken, UUID refreshToken) {
        this(id, "Bearer ", accessToken, refreshToken);
    }
}
