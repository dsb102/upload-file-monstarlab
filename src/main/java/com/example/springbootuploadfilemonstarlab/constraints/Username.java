package com.example.springbootuploadfilemonstarlab.constraints;

import com.example.springbootuploadfilemonstarlab.validate.UsernameValidator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UsernameValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface Username {
    String message() default "Username not null or empty, length min = 3, length max = 30";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}