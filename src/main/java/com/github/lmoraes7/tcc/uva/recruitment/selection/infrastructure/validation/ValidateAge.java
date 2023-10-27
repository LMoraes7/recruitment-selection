package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.validation;

import javax.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidateAgeValidator.class})
public @interface ValidateAge {
    int age();
}


