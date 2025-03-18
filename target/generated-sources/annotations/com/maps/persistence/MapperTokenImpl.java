package com.maps.persistence;

import com.maps.persistence.model.Token;
import com.maps.persistence.payload.request.DTORequestToken;
import com.maps.persistence.payload.response.DTOResponseToken;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-18T11:55:44-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class MapperTokenImpl implements MapperToken {

    @Override
    public DTOResponseToken toDTO(Token entity) {
        if ( entity == null ) {
            return null;
        }

        UUID refreshToken = null;

        refreshToken = entity.getRefreshToken();

        String accessToken = null;
        Set<String> roles = null;

        DTOResponseToken dTOResponseToken = new DTOResponseToken( accessToken, refreshToken, roles );

        return dTOResponseToken;
    }

    @Override
    public Token toObject(DTORequestToken dto) {
        if ( dto == null ) {
            return null;
        }

        Token token = new Token();

        token.setId( dto.getId() );
        token.setRefreshToken( dto.getRefreshToken() );

        return token;
    }
}
