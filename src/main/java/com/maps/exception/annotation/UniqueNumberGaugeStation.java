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
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueNumberGaugeStation.ValidatorUniqueNameGaugeStation.class })
@Documented
public @interface UniqueNumberGaugeStation {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String label();

    class ValidatorUniqueNameGaugeStation implements ConstraintValidator<UniqueNumberGaugeStation, DTORequestGaugeStation> {

        private String values;
        @Autowired
        private ServiceGaugeStation serviceGaugeStation;

        @Override
        public void initialize(UniqueNumberGaugeStation constraintAnnotation) {
            this.values = constraintAnnotation.label();
        }
        @Override
        public boolean isValid(DTORequestGaugeStation value, ConstraintValidatorContext context) {
            if (value == null || value.number() == null || value.number().isBlank())
                return true;
            boolean isUnique = (value.id() == null)
                ? !serviceGaugeStation.existsByNumber(value.number().trim()  )
                : !serviceGaugeStation.existsByNumberAndIdNot(value.number().trim(), value.id());
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
