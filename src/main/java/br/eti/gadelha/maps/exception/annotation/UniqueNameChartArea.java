package br.eti.gadelha.maps.exception.annotation;

import br.eti.gadelha.maps.exception.Validator;
import br.eti.gadelha.maps.persistence.payload.request.DTORequestChartArea;
import br.eti.gadelha.maps.service.ServiceChartArea;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueNameChartArea.ValidatorUniqueNameChartArea.class })
@Documented
public @interface UniqueNameChartArea {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    public class ValidatorUniqueNameChartArea implements ConstraintValidator<UniqueNameChartArea, DTORequestChartArea> {

        @Autowired
        private ServiceChartArea serviceChartArea;

        @Override
        public void initialize(UniqueNameChartArea constraintAnnotation) {
        }
        @Override
        public boolean isValid(DTORequestChartArea value, ConstraintValidatorContext context) {
            if (!Validator.isNull(value.getName()) && !serviceChartArea.existsByName(value.getName()) ||
                    !Validator.isNull(value.getName()) && !Validator.isNull(value.getId()) && !serviceChartArea.existsByNameAndIdNot(value.getName(), value.getId()) ) {
                return true;
            } else {
                return false;
            }
        }
    }
}