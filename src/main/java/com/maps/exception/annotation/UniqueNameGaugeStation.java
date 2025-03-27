package com.maps.exception.annotation;

import com.maps.persistence.payload.request.DTORequestGaugeStation;
import com.maps.service.ServiceGaugeStation;
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
@Constraint(validatedBy = { UniqueNameGaugeStation.ValidatorUniqueNameGaugeStation.class })
@Documented
public @interface UniqueNameGaugeStation {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String label();

    class ValidatorUniqueNameGaugeStation implements ConstraintValidator<UniqueNameGaugeStation, DTORequestGaugeStation> {

        private String values;
        @Autowired
        private ServiceGaugeStation serviceGaugeStation;

        @Override
        public void initialize(UniqueNameGaugeStation constraintAnnotation) {
            this.values = constraintAnnotation.label();
        }
        @Override
        public boolean isValid(DTORequestGaugeStation value, ConstraintValidatorContext context) {
            if (value == null || value.getNumber() == null || value.getNumber().trim().isEmpty()) {
                return false;
            }
            String normalizedName = value.getTitle().trim();
            if (value.getId() == null) {
                return !serviceGaugeStation.existsByNumber(normalizedName);
            } else {
                return !serviceGaugeStation.existsByNumberAndIdNot(normalizedName, value.getId());
            }
        }
    }
}