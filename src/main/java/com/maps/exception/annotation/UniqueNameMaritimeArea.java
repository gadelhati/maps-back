package com.maps.exception.annotation;

import com.maps.exception.Validator;
import com.maps.persistence.payload.request.DTORequestMaritimeArea;
import com.maps.service.ServiceMaritimeArea;
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
@Constraint(validatedBy = { UniqueNameMaritimeArea.ValidatorUniqueNameMaritimeArea.class })
@Documented
public @interface UniqueNameMaritimeArea {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    public class ValidatorUniqueNameMaritimeArea implements ConstraintValidator<UniqueNameMaritimeArea, DTORequestMaritimeArea> {

        @Autowired
        private ServiceMaritimeArea serviceMaritimeArea;

        @Override
        public void initialize(UniqueNameMaritimeArea constraintAnnotation) {
        }
        @Override
        public boolean isValid(DTORequestMaritimeArea value, ConstraintValidatorContext context) {
            if (!Validator.isNull(value.getName()) && !serviceMaritimeArea.existsByName(value.getName()) ||
                    !Validator.isNull(value.getName()) && !Validator.isNull(value.getId()) && !serviceMaritimeArea.existsByNameAndIdNot(value.getName(), value.getId()) ) {
                return true;
            } else {
                return false;
            }
        }
    }
}