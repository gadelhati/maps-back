package com.maps.exception.annotation;

import com.maps.exception.Validator;
import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.service.ServiceUser;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueUsername.ValidatorUniqueUsername.class })
@Documented
public @interface UniqueUsername {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    class ValidatorUniqueUsername implements ConstraintValidator<UniqueUsername, DTORequestUser> {

        @Autowired
        private ServiceUser serviceUser;

        @Override
        public boolean isValid(DTORequestUser value, ConstraintValidatorContext context) {
            return !Validator.isNull(value.getUsername()) && !serviceUser.existsByUsername(value.getUsername()) ||
                    !Validator.isNull(value.getUsername()) && !Validator.isNull(value.getId()) && !serviceUser.existsByUsernameAndIdNot(value.getUsername(), value.getId());
        }
    }
}