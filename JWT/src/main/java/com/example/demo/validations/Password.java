package com.example.demo.validations;

import java.lang.annotation.Retention;

import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Constraint(validatedBy = { PasswordValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "Password not valid. Must have 1 upper case letter, 2 numbers, no special characters"
            + " and length must be 8-12.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
