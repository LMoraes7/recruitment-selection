package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.feedback;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.RegisterFeedback;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Feedback;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.feedback.dto.RegisterFeedbackDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.feedback.FeedbackRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.CandidacyStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public final class RegisterFeedbackUseCase {
    private final CandidacyStepRepository candidacyStepRepository;
    private final FeedbackRepository feedbackRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public RegisterFeedbackUseCase(
            final CandidacyStepRepository candidacyStepRepository,
            final FeedbackRepository feedbackRepository,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.candidacyStepRepository = candidacyStepRepository;
        this.feedbackRepository = feedbackRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void execute(final Employee employee, final RegisterFeedbackDto dto) {
        final Feedback feedback = employee.registerFeedback(
                this.candidacyStepRepository,
                this.feedbackRepository,
                dto
        );

        this.applicationEventPublisher.publishEvent(
                new RegisterFeedback(
                        feedback.getIdentifier(),
                        dto.getCandidacyIdentifier(),
                        dto.getStepIdentifier(),
                        feedback.getResult().name()
                )
        );

    }

}
