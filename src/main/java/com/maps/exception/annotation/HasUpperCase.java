package com.maps.exception.annotation;

import com.maps.exception.Validator;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { HasUpperCase.ValidatorHasUpperCase.class })
@Documented
public @interface HasUpperCase {

    String message() default "{has.upper.case}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    class ValidatorHasUpperCase implements ConstraintValidator<HasUpperCase, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return Validator.hasUpperCase(value);
        }
    }
}