package br.eti.gadelha.maps.exception.annotation;

import br.eti.gadelha.maps.persistence.payload.request.DTORequestPrivilege;
import br.eti.gadelha.maps.service.ServicePrivilege;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.annotation.*;

import static br.eti.gadelha.maps.exception.Validator.isNull;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueNamePrivilege.ValidatorUniqueNamePrivilege.class })
@Documented
public @interface UniqueNamePrivilege {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    class ValidatorUniqueNamePrivilege implements ConstraintValidator<UniqueNamePrivilege, DTORequestPrivilege> {

        @Autowired
        private ServicePrivilege servicePrivilege;

        @Override
        public boolean isValid(DTORequestPrivilege value, ConstraintValidatorContext context) {
            return !isNull(value.getName()) && !servicePrivilege.existsByName(value.getName()) ||
                    !isNull(value.getName()) && !isNull(value.getId()) && !servicePrivilege.existsByNameAndIdNot(value.getName(), value.getId());
        }
    }
}