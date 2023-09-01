package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidate;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredCandidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidate.dto.CandidateDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.CandidateRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.CommonRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.ProfileRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.properties.ConfigCandidateProfileProperties;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service.PasswordEncryptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_003;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_001;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidate.converter.ConverterHelper.toModel;

@Service
public final class CreateCandidateUseCase {
    private final ProfileRepository profileRepository;
    private final CommonRepository commonRepository;
    private final CandidateRepository candidateRepository;
    private final PasswordEncryptorService passwordEncryptorService;
    private final ConfigCandidateProfileProperties configCandidateProfileProperties;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CreateCandidateUseCase(
            final ProfileRepository profileRepository,
            final CommonRepository commonRepository,
            final CandidateRepository candidateRepository,
            final PasswordEncryptorService passwordEncryptorService,
            final ConfigCandidateProfileProperties configCandidateProfileProperties,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.profileRepository = profileRepository;
        this.commonRepository = commonRepository;
        this.candidateRepository = candidateRepository;
        this.passwordEncryptorService = passwordEncryptorService;
        this.configCandidateProfileProperties = configCandidateProfileProperties;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Candidate execute(final CandidateDto dto) {
        try {
            this.commonRepository.saveRecords(dto.getPersonalData().getEmail(), dto.getPersonalData().getCpf());
        } catch (final DataIntegrityViolationException ex) {
            throw new BusinessException(
                    APIX_003,
                    List.of(dto.getPersonalData().getEmail(), dto.getPersonalData().getCpf())
            );
        }

        final Profile profile = this.profileRepository.findById(this.configCandidateProfileProperties.getIdentifier())
                .orElseThrow(() -> new InternalErrorException(INTG_001));

        final Candidate candidate = this.candidateRepository.save(
                toModel(dto, profile, this.passwordEncryptorService)
        );

        this.applicationEventPublisher.publishEvent(
                new NewRegisteredCandidate(
                        candidate.getIdentifier(),
                        candidate.getPersonalData().getEmail(),
                        candidate.getPersonalData().getCpf()
                )
        );
        return candidate;
    }

}
