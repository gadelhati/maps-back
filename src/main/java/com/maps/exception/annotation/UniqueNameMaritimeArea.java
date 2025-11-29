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
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
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
            if (value == null || value.name() == null || value.name().isBlank())
                return true;
            boolean isUnique = (value.id() == null)
                ? !serviceMaritimeArea.existsByName(value.name().trim())
                : !serviceMaritimeArea.existsByNameAndIdNot(value.name().trim(), value.id());
            if (!isUnique) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        context.getDefaultConstraintMessageTemplate()
                                .replace("{label}", values)
                ).addConstraintViolation();
            }
            return isUnique;
        }
    }
}
