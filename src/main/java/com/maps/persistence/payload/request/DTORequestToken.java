package com.maps.persistence.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
public class DTORequestToken extends Identifiable {

    private final String tokenType = "Bearer ";
    private String accessToken;
    @NotNull
    private UUID refreshToken;
}