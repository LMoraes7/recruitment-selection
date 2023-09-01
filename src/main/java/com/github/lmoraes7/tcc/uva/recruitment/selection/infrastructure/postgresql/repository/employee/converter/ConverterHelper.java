package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.entity.vo.AccessCredentialsEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.entity.vo.PersonalDataEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.entity.EmployeeEntity;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.application.serialiazer.JsonUtils.toJson;

public final class ConverterHelper {

    public static EmployeeEntity toEntity(final Employee employee) {
        return new EmployeeEntity(
                employee.getIdentifier(),
                new PersonalDataEntity(
                        employee.getPersonalData().getName(),
                        employee.getPersonalData().getCpf(),
                        employee.getPersonalData().getEmail(),
                        employee.getPersonalData().getDateOfBirth(),
                        toJson(employee.getPersonalData().getPhone()),
                        toJson(employee.getPersonalData().getAddress())
                ),
                new AccessCredentialsEntity(
                    employee.getAccessCredentials().getUsername(),
                    employee.getAccessCredentials().getPassword()
                )
        );
    }

}
