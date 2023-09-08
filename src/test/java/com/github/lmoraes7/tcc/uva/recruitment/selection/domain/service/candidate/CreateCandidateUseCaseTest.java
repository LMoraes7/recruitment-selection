package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidate;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredCandidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidate.dto.CandidateDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.CandidateRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.CommonRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.ProfileRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.properties.ConfigCandidateProfileProperties;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.service.PasswordEncryptorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_002;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidate.converter.ConverterHelper.toModel;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

final class CreateCandidateUseCaseTest {

    private final ProfileRepository profileRepository = mock(ProfileRepository.class);
    private final CommonRepository commonRepository = mock(CommonRepository.class);
    private final CandidateRepository candidateRepository = mock(CandidateRepository.class);
    private final PasswordEncryptorService passwordEncryptorService = mock(PasswordEncryptorService.class);
    private final ConfigCandidateProfileProperties configCandidateProfileProperties = mock(ConfigCandidateProfileProperties.class);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final CreateCandidateUseCase createCandidateUseCase = new CreateCandidateUseCase(
            this.profileRepository,
            this.commonRepository,
            this.candidateRepository,
            this.passwordEncryptorService,
            this.configCandidateProfileProperties,
            this.applicationEventPublisher
    );

    private CandidateDto dto;
    private Profile profile;

    @BeforeEach
    void setUp() {
        this.dto = dummyObject(CandidateDto.class);
        this.profile = dummyObject(Profile.class);
    }

    @Test
    void when_prompted_must_successfully_save_a_candidate() {
        final Candidate candidate = toModel(dto, profile, this.passwordEncryptorService);

        when(this.configCandidateProfileProperties.getIdentifier()).thenReturn(this.profile.getIdentifier());
        when(this.profileRepository.findById(this.profile.getIdentifier()))
                .thenReturn(Optional.of(this.profile));
        when(this.passwordEncryptorService.execute(dto.getAccessCredentials().getPassword()))
                .thenReturn(this.profile.getIdentifier());
        when(this.candidateRepository.save(any(Candidate.class))).thenReturn(candidate);

        assertDoesNotThrow(() -> this.createCandidateUseCase.execute(this.dto));

        verify(this.commonRepository, only()).saveRecords(
                dto.getPersonalData().getEmail(), dto.getPersonalData().getCpf()
        );
        verify(this.configCandidateProfileProperties, only()).getIdentifier();
        verify(this.profileRepository, only()).findById(this.profile.getIdentifier());
        verify(this.candidateRepository, only()).save(any(Candidate.class));
        verify(this.applicationEventPublisher, only()).publishEvent(any(NewRegisteredCandidate.class));
    }

    @Test
    void when_requested_should_throw_a_BusinessException_when_receiving_a_DataIntegrityViolationException() {
        doThrow(DataIntegrityViolationException.class)
                .when(this.commonRepository)
                .saveRecords(
                        this.dto.getPersonalData().getEmail(),
                        this.dto.getPersonalData().getCpf()
                );

        final BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> this.createCandidateUseCase.execute(this.dto)
        );

        assertNotNull(businessException);
        assertNotNull(businessException.getError());
        assertNotNull(businessException.getArgs());

        assertEquals(Error.APIX_003, businessException.getError());
        assertEquals(2, businessException.getArgs().size());
        assertTrue(businessException.getArgs().containsAll(
                List.of(this.dto.getPersonalData().getEmail(), this.dto.getPersonalData().getCpf())
        ));

        verify(this.commonRepository, only()).saveRecords(
                this.dto.getPersonalData().getEmail(),
                this.dto.getPersonalData().getCpf()
        );
        verifyNoInteractions(
                this.profileRepository,
                this.passwordEncryptorService,
                this.candidateRepository,
                this.applicationEventPublisher,
                this.configCandidateProfileProperties
        );
    }

    @Test
    void when_requested_it_should_throw_an_InternalErrorException_when_it_doesnt_find_the_profile() {
        when(this.configCandidateProfileProperties.getIdentifier()).thenReturn(this.profile.getIdentifier());
        when(this.profileRepository.findById(this.profile.getIdentifier()))
                .thenReturn(Optional.empty());

        final InternalErrorException exception = assertThrows(
                InternalErrorException.class,
                () -> this.createCandidateUseCase.execute(this.dto)
        );

        assertNotNull(exception.getError());
        assertEquals(INTG_002, exception.getError());

        verify(this.commonRepository, only()).saveRecords(
                this.dto.getPersonalData().getEmail(),
                this.dto.getPersonalData().getCpf()
        );
        verify(this.configCandidateProfileProperties, only()).getIdentifier();
        verify(this.profileRepository, only()).findById(this.profile.getIdentifier());
        verifyNoInteractions(
                this.passwordEncryptorService,
                this.candidateRepository,
                this.applicationEventPublisher
        );
    }

}