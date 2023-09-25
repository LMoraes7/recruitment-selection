package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.CandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.CandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

final class CreateCandidacyUseCaseTest {
    private final SelectiveProcessRepository selectiveProcessRepository = mock(SelectiveProcessRepository.class);
    private final CandidacyRepository candidacyRepository = mock(CandidacyRepository.class);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final CreateCandidacyUseCase createCandidacyUseCase = new CreateCandidacyUseCase(this.selectiveProcessRepository, this.candidacyRepository, this.applicationEventPublisher);

    private Candidate candidate;
    private CandidacyDto candidacyDto;
    private SelectiveProcess selectiveProcess;
    private Candidacy candidacy;

    @BeforeEach
    void setUp() {
        this.candidate = dummyObject(Candidate.class);
        this.candidacyDto = dummyObject(CandidacyDto.class);
        this.selectiveProcess = new SelectiveProcess(
                GeneratorIdentifier.forSelectiveProcess(),
                StatusSelectiveProcess.IN_PROGRESS,
                List.of(
                        new ExternalStep(new StepData(GeneratorIdentifier.forStep(), TypeStep.UPLOAD_FILES), 4364564L),
                        new ExternalStep(new StepData(GeneratorIdentifier.forStep(), TypeStep.THEORETICAL_TEST), 4765547L),
                        new ExternalStep(new StepData(GeneratorIdentifier.forStep(), TypeStep.EXTERNAL)),
                        new ExternalStep(new StepData(GeneratorIdentifier.forStep(), TypeStep.UPLOAD_FILES), 576786L)
                )
        );
        this.candidacy = ConverterHelper.toModel(selectiveProcess);
    }

    @Test
    void when_prompted_you_must_save_a_successful_application() {
        when(this.selectiveProcessRepository.findWithStepsById(this.candidacyDto.getSelectiveProcessIdentifier()))
                .thenReturn(Optional.of(this.selectiveProcess));
        when(this.candidacyRepository.save(
                eq(this.candidate.getIdentifier()),
                eq(this.selectiveProcess.getIdentifier()),
                any(Candidacy.class)
        )).thenReturn(this.candidacy);

        assertDoesNotThrow(
                () -> this.createCandidacyUseCase.execute(
                        this.candidate,
                        this.candidacyDto
                )
        );

        verify(this.selectiveProcessRepository, only()).findWithStepsById(this.candidacyDto.getSelectiveProcessIdentifier());
        verify(this.candidacyRepository, only()).save(
                eq(this.candidate.getIdentifier()),
                eq(this.selectiveProcess.getIdentifier()),
                any(Candidacy.class)
        );
        verify(this.applicationEventPublisher, only()).publishEvent(
                new NewRegisteredCandidacy(
                        this.candidacy.getIdentifier(),
                        this.candidate.getIdentifier(),
                        this.candidacyDto.getSelectiveProcessIdentifier()
                )
        );
    }

}