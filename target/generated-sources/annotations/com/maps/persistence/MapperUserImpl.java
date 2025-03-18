package com.maps.persistence;

import com.maps.persistence.model.Role;
import com.maps.persistence.model.User;
import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.persistence.payload.response.DTOResponseUser;
import java.util.Collection;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-18T11:55:44-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class MapperUserImpl implements MapperUser {

    @Override
    public DTOResponseUser toDTO(User entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        String username = null;
        String email = null;
        Integer attempt = null;
        Boolean active = null;

        id = entity.getId();
        username = entity.getUsername();
        email = entity.getEmail();
        attempt = entity.getAttempt();
        active = entity.getActive();

        Collection<Role> role = null;

        DTOResponseUser dTOResponseUser = new DTOResponseUser( id, username, email, attempt, active, role );

        return dTOResponseUser;
    }

    @Override
    public User toObject(DTORequestUser dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setId( dto.getId() );
        user.setUsername( dto.getUsername() );
        user.setEmail( dto.getEmail() );
        user.setPassword( dto.getPassword() );
        user.setActive( dto.isActive() );
        user.setSecret( dto.getSecret() );

        return user;
    }
}
