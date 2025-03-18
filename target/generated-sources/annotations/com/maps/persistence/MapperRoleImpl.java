package com.maps.persistence;

import com.maps.persistence.model.Privilege;
import com.maps.persistence.model.Role;
import com.maps.persistence.payload.request.DTORequestRole;
import com.maps.persistence.payload.response.DTOResponseRole;
import java.util.LinkedHashSet;
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
public class MapperRoleImpl implements MapperRole {

    @Override
    public DTOResponseRole toDTO(Role entity) {
        if ( entity == null ) {
            return null;
        }

        Set<Privilege> privileges = null;
        UUID id = null;
        String name = null;

        Set<Privilege> set = entity.getPrivileges();
        if ( set != null ) {
            privileges = new LinkedHashSet<Privilege>( set );
        }
        id = entity.getId();
        name = entity.getName();

        DTOResponseRole dTOResponseRole = new DTOResponseRole( id, name, privileges );

        return dTOResponseRole;
    }

    @Override
    public Role toObject(DTORequestRole dto) {
        if ( dto == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( dto.getId() );
        role.setName( dto.getName() );
        Set<Privilege> set = dto.getPrivileges();
        if ( set != null ) {
            role.setPrivileges( new LinkedHashSet<Privilege>( set ) );
        }

        return role;
    }
}
