package com.maps.persistence.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@AllArgsConstructor
public class DTOResponseToken extends RepresentationModel<DTOResponseToken> {

    private final String tokenType = "Bearer ";
    private String accessToken;
    private UUID refreshToken;
    private Set<String> roles;

    @Override
    public String toString() {
        return "{" +
                "tokenType='" + tokenType + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken=" + refreshToken +
                ", roles=" + roles +
                '}';
    }
}