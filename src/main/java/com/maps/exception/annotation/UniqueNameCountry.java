package com.maps.exception.annotation;

import com.maps.persistence.payload.request.DTORequestCountry;
import com.maps.service.ServiceCountry;
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
@Constraint(validatedBy = { UniqueNameCountry.ValidatorUniqueNameCountry.class })
@Documented
public @interface UniqueNameCountry {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String label();

    class ValidatorUniqueNameCountry implements ConstraintValidator<UniqueNameCountry, DTORequestCountry> {

        private String values;
        @Autowired
        private ServiceCountry serviceCountry;

        @Override
        public void initialize(UniqueNameCountry constraintAnnotation) {
            this.values = constraintAnnotation.label();
        }
        @Override
        public boolean isValid(DTORequestCountry value, ConstraintValidatorContext context) {
            if (value == null || value.getName() == null || value.getName().trim().isEmpty()) {
                return false;
            }
            String normalizedName = value.getName().trim();
            if (value.getId() == null) {
                return !serviceCountry.existsByName(normalizedName);
            } else {
                return !serviceCountry.existsByNameAndIdNot(normalizedName, value.getId());
            }
        }
    }
}