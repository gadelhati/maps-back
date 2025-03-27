package com.maps.exception.annotation;

import com.maps.persistence.payload.request.DTORequestMaritimeArea;
import com.maps.service.ServiceMaritimeArea;
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
@Constraint(validatedBy = { UniqueNameMaritimeArea.ValidatorUniqueNameMaritimeArea.class })
@Documented
public @interface UniqueNameMaritimeArea {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String label();

    class ValidatorUniqueNameMaritimeArea implements ConstraintValidator<UniqueNameMaritimeArea, DTORequestMaritimeArea> {

        private String values;
        @Autowired
        private ServiceMaritimeArea serviceMaritimeArea;

        @Override
        public void initialize(UniqueNameMaritimeArea constraintAnnotation) {
            this.values = constraintAnnotation.label();
        }
        @Override
        public boolean isValid(DTORequestMaritimeArea value, ConstraintValidatorContext context) {
            if (value == null || value.getName() == null || value.getName().trim().isEmpty()) {
                return false;
            }
            String normalizedName = value.getName().trim();
            if (value.getId() == null) {
                return !serviceMaritimeArea.existsByName(normalizedName);
            } else {
                return !serviceMaritimeArea.existsByNameAndIdNot(normalizedName, value.getId());
            }
        }
    }
}