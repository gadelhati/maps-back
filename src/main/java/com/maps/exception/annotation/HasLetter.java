package com.maps.exception.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.maps.exception.Validator.hasLetter;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { HasLetter.ValidatorHasLetter.class })
@Documented
public @interface HasLetter {

    String message() default "{has.letter}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    class ValidatorHasLetter implements ConstraintValidator<HasLetter, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return hasLetter(value);
        }
    }
}