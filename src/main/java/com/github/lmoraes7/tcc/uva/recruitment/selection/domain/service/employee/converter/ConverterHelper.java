package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorPassword;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.AccessCredentials;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.Address;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.PersonalData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.Phone;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto.EmployeeDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service.PasswordEncryptorService;

import java.util.stream.Collectors;

public final class ConverterHelper {

    public static Employee toModel(final EmployeeDto dto, final PasswordEncryptorService passwordEncryptorService) {
        return new Employee(
                GeneratorIdentifier.forEmployee(),
                new PersonalData(
                        dto.getPersonalData().getName(),
                        dto.getPersonalData().getCpf(),
                        dto.getPersonalData().getEmail(),
                        dto.getPersonalData().getDateOfBirth(),
                        new Phone(
                                dto.getPersonalData().getPhone().getDdd(),
                                dto.getPersonalData().getPhone().getNumber()
                        ),
                        new Address(
                                dto.getPersonalData().getAddress().getPlace(),
                                dto.getPersonalData().getAddress().getNumber(),
                                dto.getPersonalData().getAddress().getComplement(),
                                dto.getPersonalData().getAddress().getNeighborhood(),
                                dto.getPersonalData().getAddress().getLocality(),
                                dto.getPersonalData().getAddress().getUf(),
                                dto.getPersonalData().getAddress().getCep()
                        )
                ),
                new AccessCredentials(
                        dto.getPersonalData().getEmail(),
                        passwordEncryptorService.execute(GeneratorPassword.forEmployee()),
                        dto.getProfilesIdentifiers().stream().map(Profile::new).collect(Collectors.toSet())
                )
        );
    }

}
