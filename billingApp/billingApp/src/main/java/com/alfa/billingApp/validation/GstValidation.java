package com.alfa.billingApp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GstValidation implements ConstraintValidator<GstNo,String> {
    @Override
    public void initialize(GstNo constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String gst, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid=gst.matches("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$");
        return valid;
    }
}
