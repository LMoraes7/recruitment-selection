package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidate.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidate.dto.AccessCredentialsDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidate.dto.CandidateDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.AddressDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.PersonalDataDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.PhoneDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidate.request.CandidateRequest;

public final class ConverterHelper {

    public static CandidateDto toDto(final CandidateRequest request) {
        return new CandidateDto(
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
                new AccessCredentialsDto(
                        request.getAccessCredentials().getPassword()
                )
        );
    }

}
