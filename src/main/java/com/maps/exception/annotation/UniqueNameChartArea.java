package com.maps.exception.annotation;

import com.maps.persistence.payload.request.DTORequestChartArea;
import com.maps.service.ServiceChartArea;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueNameChartArea.ValidatorUniqueNameChartArea.class })
@Documented
public @interface UniqueNameChartArea {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String label();

    class ValidatorUniqueNameChartArea implements ConstraintValidator<UniqueNameChartArea, DTORequestChartArea> {

        private String values;
        @Autowired
        private ServiceChartArea serviceChartArea;

        @Override
        public void initialize(UniqueNameChartArea constraintAnnotation) {
            this.values = constraintAnnotation.label();
        }
        @Override
        public boolean isValid(DTORequestChartArea value, ConstraintValidatorContext context) {
            if (value == null || value.name() == null || value.name().trim().isEmpty()) {
                return false;
            }
            String normalizedName = value.name().trim();
            if (value.id() == null) {
                return !serviceChartArea.existsByName(normalizedName);
            } else {
                return !serviceChartArea.existsByNameAndIdNot(normalizedName, value.id());
            }
        }
    }
}
