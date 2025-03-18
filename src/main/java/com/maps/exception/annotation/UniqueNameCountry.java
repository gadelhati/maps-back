package com.maps.exception.annotation;

import com.maps.exception.Validator;
import com.maps.persistence.payload.request.DTORequestCountry;
import com.maps.service.ServiceCountry;
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
@Constraint(validatedBy = { UniqueNameCountry.ValidatorUniqueNameCountry.class })
@Documented
public @interface UniqueNameCountry {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    public class ValidatorUniqueNameCountry implements ConstraintValidator<UniqueNameCountry, DTORequestCountry> {

        @Autowired
        private ServiceCountry serviceCountry;

        @Override
        public void initialize(UniqueNameCountry constraintAnnotation) {
        }
        @Override
        public boolean isValid(DTORequestCountry value, ConstraintValidatorContext context) {
            if (!Validator.isNull(value.getName()) && !serviceCountry.existsByName(value.getName()) ||
                    !Validator.isNull(value.getName()) && !Validator.isNull(value.getId()) && !serviceCountry.existsByNameAndIdNot(value.getName(), value.getId()) ) {
                return true;
            } else {
                return false;
            }
        }
    }
}