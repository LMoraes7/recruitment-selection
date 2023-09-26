package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.AccessCredentials;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.PersonalData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.*;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.CandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_012;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess.IN_PROGRESS;

public final class Candidate {
    private String identifier;
    private PersonalData personalData;
    private AccessCredentials accessCredentials;

    public Candidate(
            final String identifier,
            final PersonalData personalData,
            final AccessCredentials accessCredentials
    ) {
        this.identifier = identifier;
        this.personalData = personalData;
        this.accessCredentials = accessCredentials;
    }

    public String getIdentifier() {
        return identifier;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public AccessCredentials getAccessCredentials() {
        return accessCredentials;
    }

    public Candidacy performCandidacy(
            final SelectiveProcessRepository selectiveProcessRepository,
            final CandidacyRepository candidacyRepository,
            final CandidacyDto dto
    ) {
        final SelectiveProcess selectiveProcess = selectiveProcessRepository.findWithStepsById(dto.getSelectiveProcessIdentifier())
                .orElseThrow(() -> new NotFoundException(dto.getSelectiveProcessIdentifier(), SelectiveProcess.class));

        if (selectiveProcess.getStatus() != IN_PROGRESS)
            throw new BusinessException(APIX_012, List.of(selectiveProcess.getIdentifier()));

        return candidacyRepository.save(
                this.identifier,
                selectiveProcess.getIdentifier(),
                ConverterHelper.toModel(selectiveProcess)
        );
    }

    public SpecificCandidacyDto findSpecificCandidacy(
            final CandidacyRepository candidacyRepository,
            final String candidacyIdentifier
    ) {
        return candidacyRepository.findById(this.identifier, candidacyIdentifier)
                .orElseThrow(() -> new NotFoundException(candidacyIdentifier, Candidacy.class));
    }

    public CandidacyPaginated findCandidacies(
            final CandidacyRepository candidacyRepository,
            final PaginationQuery paginationQuery
    ) {
        return candidacyRepository.findAll(this.identifier, paginationQuery);
    }

    public void closeCandidacy(
            final CandidacyRepository candidacyRepository,
            final CloseCandidacyDto dto
    ) {
        candidacyRepository.closeCandidacy(
                this.identifier,
                dto.getSelectiveProcessIdentifier(),
                dto.getCandidacyIdentifier()
        );
    }

}
