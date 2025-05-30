package com.maps.exception.annotation;

import com.maps.persistence.payload.request.DTORequestResearcher;
import com.maps.service.ServiceResearcher;
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
@Constraint(validatedBy = { UniqueNameResearcher.ValidatorUniqueNameResearcher.class })
@Documented
public @interface UniqueNameResearcher {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String label();

    class ValidatorUniqueNameResearcher implements ConstraintValidator<UniqueNameResearcher, DTORequestResearcher> {

        private String values;
        @Autowired
        private ServiceResearcher serviceResearcher;

        @Override
        public void initialize(UniqueNameResearcher constraintAnnotation) {
            this.values = constraintAnnotation.label();
        }
        @Override
        public boolean isValid(DTORequestResearcher value, ConstraintValidatorContext context) {
            if (value == null || value.getName() == null || value.getName().trim().isEmpty()) {
                return false;
            }
            String normalizedName = value.getName().trim();
            if (value.getId() == null) {
                return !serviceResearcher.existsByName(normalizedName);
            } else {
                return !serviceResearcher.existsByNameAndIdNot(normalizedName, value.getId());
            }
        }
    }
}