package br.eti.gadelha.maps.exception.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import static br.eti.gadelha.maps.exception.Validator.hasDigit;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { HasDigit.ValidatorHasDigit.class })
@Documented
public @interface HasDigit {

    String message() default "{has.digit}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    class ValidatorHasDigit implements ConstraintValidator<HasDigit, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return hasDigit(value);
        }
    }
}