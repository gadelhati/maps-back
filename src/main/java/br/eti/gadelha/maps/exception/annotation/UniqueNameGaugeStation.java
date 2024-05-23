package br.eti.gadelha.maps.exception.annotation;

import br.eti.gadelha.maps.exception.Validator;
import br.eti.gadelha.maps.persistence.payload.request.DTORequestGaugeStation;
import br.eti.gadelha.maps.service.ServiceGaugeStation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

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