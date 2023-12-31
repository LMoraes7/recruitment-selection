package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.employee.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.AddressDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.PersonalDataDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.PhoneDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto.EmployeeDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.employee.request.EmployeeRequest;

public final class ConverterHelper {

    public static EmployeeDto toDto(final EmployeeRequest request) {
        return new EmployeeDto(
                new PersonalDataDto(
                        request.getPersonalData().getName(),
                        request.getPersonalData().getCpf(),
                        request.getPersonalData().getEmail(),
                        request.getPersonalData().getDateOfBirth(),
                        new PhoneDto(
                                request.getPersonalData().getPhone().getDdd(),
                                request.getPersonalData().getPhone().getNumber()
                        ),
                        new AddressDto(
                                request.getPersonalData().getAddress().getNumber(),
                                request.getPersonalData().getAddress().getComplement(),
                                request.getPersonalData().getAddress().getCep(),
                                request.getPersonalData().getAddress().getPlace(),
                                request.getPersonalData().getAddress().getNeighborhood(),
                                request.getPersonalData().getAddress().getLocality(),
                                request.getPersonalData().getAddress().getUf()
                        )
                ),
                request.getProfilesIdentifiers()
        );
    }

}
