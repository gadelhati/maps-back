package com.maps.persistence;

import com.maps.persistence.model.Privilege;
import com.maps.persistence.payload.request.DTORequestPrivilege;
import com.maps.persistence.payload.response.DTOResponsePrivilege;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-18T11:55:44-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class MapperPrivilegeImpl implements MapperPrivilege {

    @Override
    public DTOResponsePrivilege toDTO(Privilege entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        String name = null;

        id = entity.getId();
        name = entity.getName();

        DTOResponsePrivilege dTOResponsePrivilege = new DTOResponsePrivilege( id, name );

        return dTOResponsePrivilege;
    }

    @Override
    public Privilege toObject(DTORequestPrivilege dto) {
        if ( dto == null ) {
            return null;
        }

        Privilege privilege = new Privilege();

        privilege.setId( dto.getId() );
        privilege.setName( dto.getName() );

        return privilege;
    }
}
