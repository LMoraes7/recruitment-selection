package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.entity.vo;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;

import java.util.Arrays;

public enum TypeEntity {
    EMP(Employee.class, com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity.EMP),
    CAN(Candidate.class, com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity.CAN);

    private final Class<?> classType;
    private final com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity typeEntity;

    TypeEntity(final Class<?> classType, final com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity typeEntity) {
        this.classType = classType;
        this.typeEntity = typeEntity;
    }

    public static TypeEntity fromValue(final Class<?> classType) {
        return Arrays.stream(TypeEntity.values())
                .filter(it -> it.classType == classType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Informed value " + classType + " does not match the enum " + TypeEntity.class.getName()));
    }

    public static TypeEntity fromValue(final com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity typeEntity) {
        return Arrays.stream(TypeEntity.values())
                .filter(it -> it.typeEntity == typeEntity)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Informed value " + typeEntity + " does not match the enum " + TypeEntity.class.getName()));
    }

}
