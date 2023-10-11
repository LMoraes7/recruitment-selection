package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.feedback;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.RegisterFeedback;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Feedback;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.feedback.dto.RegisterFeedbackDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.FindStepsDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.feedback.FeedbackRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.CandidacyStepRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

final class RegisterFeedbackUseCaseTest {
    private final CandidacyStepRepository candidacyStepRepository = mock(CandidacyStepRepository.class);
    private final FeedbackRepository feedbackRepository = mock(FeedbackRepository.class);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final RegisterFeedbackUseCase registerFeedbackUseCase = new RegisterFeedbackUseCase(this.candidacyStepRepository, this.feedbackRepository, this.applicationEventPublisher);

    private Employee employee;
    private RegisterFeedbackDto registerFeedbackDto;
    private Feedback feedback;
    private FindStepsDto findStepsDto;

    @BeforeEach
    void setUp() {
        this.employee = TestUtils.dummyObject(Employee.class);
        this.registerFeedbackDto = TestUtils.dummyObject(RegisterFeedbackDto.class);
        this.feedback = TestUtils.dummyObject(Feedback.class);
        this.findStepsDto = new FindStepsDto("1234567", "7654321", StatusStepCandidacy.WAITING_FOR_EXECUTION, TypeStep.EXTERNAL);
    }

    @Test
    void when_prompted_you_should_save_feedback_successfully() {
        when(this.candidacyStepRepository.getStep(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier()
        )).thenReturn(Optional.of(this.findStepsDto));
        when(this.feedbackRepository.save(
                any(),
                eq(this.registerFeedbackDto.getCandidacyIdentifier()),
                eq(this.registerFeedbackDto.getStepIdentifier())
        )).thenReturn(this.feedback);

        assertDoesNotThrow(() -> this.registerFeedbackUseCase.execute(this.employee, this.registerFeedbackDto));

        verify(this.candidacyStepRepository, times(1)).getStep(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier()
        );
        verify(this.feedbackRepository, only()).save(
                any(),
                eq(this.registerFeedbackDto.getCandidacyIdentifier()),
                eq(this.registerFeedbackDto.getStepIdentifier())
        );
        verify(this.candidacyStepRepository, times(1)).updateStatus(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier(),
                StatusStepCandidacy.COMPLETED
        );
        verify(this.applicationEventPublisher, only()).publishEvent(
                new RegisterFeedback(
                        this.feedback.getIdentifier(),
                        this.registerFeedbackDto.getCandidacyIdentifier(),
                        this.registerFeedbackDto.getStepIdentifier(),
                        this.feedback.getResult().name()
                )
        );
        verifyNoMoreInteractions(this.candidacyStepRepository);
    }

}