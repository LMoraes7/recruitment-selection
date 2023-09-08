package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.dto.QuestionDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.strategy.CreateQuestionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_002;

@Service
public final class CreateQuestionUseCase {

    private final Collection<CreateQuestionStrategy> createQuestionStrategies;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CreateQuestionUseCase(
            final Collection<CreateQuestionStrategy> createQuestionStrategies,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.createQuestionStrategies = createQuestionStrategies;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Question execute(final Employee employee, final QuestionDto dto) {
        final CreateQuestionStrategy strategy = this.createQuestionStrategies.stream()
                .filter(it -> it.getTypeQuestion() == dto.getType())
                .findFirst()
                .orElseThrow(() -> new InternalErrorException(INTG_002));

        final Question question = employee.createQuestion(dto, strategy);

        this.applicationEventPublisher.publishEvent(
            new NewRegisteredQuestion(
                    question.getIdentifier(),
                    question.getType()
            )
        );

        return question;
    }

}
