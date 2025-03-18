package com.maps.exception.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.maps.exception.Validator.hasLowerCase;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { HasLowerCase.ValidatorHasLowerCase.class })
@Documented
public @interface HasLowerCase {

    String message() default "{has.lower.case}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    class ValidatorHasLowerCase implements ConstraintValidator<HasLowerCase, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return hasLowerCase(value);
        }
    }
}