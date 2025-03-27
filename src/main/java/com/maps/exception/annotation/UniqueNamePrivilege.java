package com.maps.exception.annotation;

import com.maps.persistence.payload.request.DTORequestPrivilege;
import com.maps.service.ServicePrivilege;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueNamePrivilege.ValidatorUniqueNamePrivilege.class })
@Documented
public @interface UniqueNamePrivilege {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String label();

    class ValidatorUniqueNamePrivilege implements ConstraintValidator<UniqueNamePrivilege, DTORequestPrivilege> {

        private String values;
        @Autowired
        private ServicePrivilege servicePrivilege;

        @Override
        public void initialize(UniqueNamePrivilege constraintAnnotation) {
            this.values = constraintAnnotation.label();
        }
        @Override
        public boolean isValid(DTORequestPrivilege value, ConstraintValidatorContext context) {
            if (value == null || value.getName() == null || value.getName().trim().isEmpty()) {
                return false;
            }
            String normalizedName = value.getName().trim();
            if (value.getId() == null) {
                return !servicePrivilege.existsByName(normalizedName);
            } else {
                return !servicePrivilege.existsByNameAndIdNot(normalizedName, value.getId());
            }
        }
    }
}