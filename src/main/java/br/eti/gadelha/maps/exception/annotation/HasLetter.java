package br.eti.gadelha.maps.exception.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import static br.eti.gadelha.maps.exception.Validator.hasLetter;

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