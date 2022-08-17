package com.example.springbootuploadfilemonstarlab.constraints;


import com.example.springbootuploadfilemonstarlab.validate.EmailValidator;
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

@Constraint(validatedBy = EmailValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface CusEmail {
    String message() default "Email must be in the correct format, not null or empty";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}