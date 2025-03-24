package com.alfa.billingApp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = GstValidation.class)
@Target({ElementType.FIELD,ElementType.PARAMETER})
//1. RetentionPolicy.source   --- discarded during run time (@Override)
//2. RetentionPolicy.class    --- saved in .class file but discarded during run time
//3. .runtime --- will be used during run time
@Retention(RetentionPolicy.RUNTIME)

//@interface to define a custom annotation
//annotation are meta data
public @interface GstNo{

    String message() default "Invalid GST number";
//    String gstNo() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
