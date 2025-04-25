package com.maps.exception.annotation;

import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.service.ServiceUser;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

import static com.maps.exception.Validator.isNull;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueEmail.ValidatorUniqueEmail.class })
@Documented
public @interface UniqueEmail {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String label();

    class ValidatorUniqueEmail implements ConstraintValidator<UniqueEmail, DTORequestUser> {

        private String values;
        @Autowired
        private ServiceUser serviceUser;

        @Override
        public void initialize(UniqueEmail constraintAnnotation) {
            this.values = constraintAnnotation.label();
        }
        @Override
        public boolean isValid(DTORequestUser value, ConstraintValidatorContext context) {
            if (value == null || value.getEmail() == null || value.getEmail().trim().isEmpty()) {
                return false;
            }
            String normalizedName = value.getEmail().trim();
            if (value.getId() == null) {
                return !serviceUser.existsByEmail(normalizedName);
            } else {
                return !serviceUser.existsByEmailAndIdNot(normalizedName, value.getId());
            }
        }
    }
}