package com.maps.exception.annotation;

import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.service.ServiceUser;
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
@Constraint(validatedBy = { UniqueNameUser.ValidatorUniqueNameNameUser.class })
@Documented
public @interface UniqueNameUser {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String label();

    class ValidatorUniqueNameNameUser implements ConstraintValidator<UniqueNameUser, DTORequestUser> {

        private String values;
        @Autowired
        private ServiceUser serviceUser;

        @Override
        public void initialize(UniqueNameUser constraintAnnotation) {
            this.values = constraintAnnotation.label();
        }
        @Override
        public boolean isValid(DTORequestUser value, ConstraintValidatorContext context) {
            if (value == null || value.getUsername() == null || value.getUsername().trim().isEmpty()) {
                return false;
            }
            String normalizedName = value.getUsername().trim();
            if (value.getId() == null) {
                return !serviceUser.existsByUsername(normalizedName);
            } else {
                return !serviceUser.existsByUsernameAndIdNot(normalizedName, value.getId());
            }
        }
    }
}