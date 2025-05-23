package com.maps.exception.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.maps.exception.Validator.hasLength;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { HasLength.ValidatorHasLength.class })
@Documented
public @interface HasLength {

    String message() default "{has.length}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    class ValidatorHasLength implements ConstraintValidator<HasLength, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return hasLength(8, value);
        }
    }
}