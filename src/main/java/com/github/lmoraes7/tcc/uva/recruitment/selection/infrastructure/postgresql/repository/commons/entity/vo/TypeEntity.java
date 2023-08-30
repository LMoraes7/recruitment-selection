package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.entity.vo;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;

import java.util.Arrays;

public enum TypeEntity {
    EMP(Employee.class);

    private final Class<?> classType;

    TypeEntity(final Class<?> classType) {
        this.classType = classType;
    }

    public static TypeEntity fromValue(final Class<?> classType) {
        return Arrays.stream(TypeEntity.values())
                .filter(it -> it.classType == classType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Informed value " + classType + " does not match the enum " + TypeEntity.class.getName()));
    }

}
