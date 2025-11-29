package com.maps.exception.annotation;

import com.maps.persistence.payload.request.DTORequestRole;
import com.maps.service.ServiceRole;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueNameRole.ValidatorUniqueNameRole.class })
@Documented
public @interface UniqueNameRole {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String label();

    class ValidatorUniqueNameRole implements ConstraintValidator<UniqueNameRole, DTORequestRole> {

        private String values;
        @Autowired
        private ServiceRole serviceRole;

        @Override
        public void initialize(UniqueNameRole constraintAnnotation) {
            this.values = constraintAnnotation.label();
        }
        @Override
        public boolean isValid(DTORequestRole value, ConstraintValidatorContext context) {
            if (value == null || value.name() == null || value.name().isBlank())
                return true;
            boolean isUnique = (value.id() == null)
                ? !serviceRole.existsByName(value.name().trim())
                : !serviceRole.existsByNameAndIdNot(value.name().trim(), value.id());
            if (!isUnique) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        context.getDefaultConstraintMessageTemplate()
                                .replace("{label}", values)
                ).addConstraintViolation();
            }
            return isUnique;
        }
    }
}
