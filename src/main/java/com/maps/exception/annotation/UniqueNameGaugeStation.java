package com.maps.exception.annotation;

import com.maps.exception.Validator;
import com.maps.persistence.payload.request.DTORequestGaugeStation;
import com.maps.service.ServiceGaugeStation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
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

    public class ValidatorUniqueNameGaugeStation implements ConstraintValidator<UniqueNameGaugeStation, DTORequestGaugeStation> {

        @Autowired
        private ServiceGaugeStation serviceGaugeStation;

        @Override
        public void initialize(UniqueNameGaugeStation constraintAnnotation) {
        }
        @Override
        public boolean isValid(DTORequestGaugeStation value, ConstraintValidatorContext context) {
            if (!Validator.isNull(value.getTitle()) && !serviceGaugeStation.existsByNumber(value.getTitle()) ||
                    !Validator.isNull(value.getTitle()) && !Validator.isNull(value.getId()) && !serviceGaugeStation.existsByNumberAndIdNot(value.getTitle(), value.getId()) ) {
                return true;
            } else {
                return false;
            }
        }
    }
}