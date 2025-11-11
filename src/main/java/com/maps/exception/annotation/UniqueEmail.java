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
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
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
            if (value == null || value.email() == null || value.email().trim().isEmpty()) {
                return false;
            }
            String normalizedName = value.email().trim();
            if (value.id() == null) {
                return !serviceUser.existsByEmail(normalizedName);
            } else {
                return !serviceUser.existsByEmailAndIdNot(normalizedName, value.id());
            }
        }
    }
}
