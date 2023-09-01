package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.entity.CandidateEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.entity.vo.AccessCredentialsEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.entity.vo.PersonalDataEntity;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.application.serialiazer.JsonUtils.toJson;

public final class ConverterHelper {

    public static CandidateEntity toEntity(final Candidate candidate) {
        return new CandidateEntity(
                candidate.getIdentifier(),
                new PersonalDataEntity(
                        candidate.getPersonalData().getName(),
                        candidate.getPersonalData().getCpf(),
                        candidate.getPersonalData().getEmail(),
                        candidate.getPersonalData().getDateOfBirth(),
                        toJson(candidate.getPersonalData().getPhone()),
                        toJson(candidate.getPersonalData().getAddress())
                ),
                new AccessCredentialsEntity(
                        candidate.getAccessCredentials().getUsername(),
                        candidate.getAccessCredentials().getPassword()
                )
        );
    }

}
