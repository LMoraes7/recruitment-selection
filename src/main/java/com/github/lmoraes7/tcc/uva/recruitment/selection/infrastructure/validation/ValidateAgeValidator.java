package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class ValidateAgeValidator implements ConstraintValidator<ValidateAge, LocalDate> {
    private Integer age;

    @Override
    public void initialize(final ValidateAge constraintAnnotation) {
        this.age = constraintAnnotation.age();
    }

    @Override
    public boolean isValid(
            final LocalDate localDate,
            final ConstraintValidatorContext constraintValidatorContext
    ) {
        final int years = Period.between(localDate, LocalDate.now()).getYears();

        if (years >= age)
            return true;
        return false;
    }
}
