package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.dto.QuestionDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.strategy.CreateQuestionStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_002;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class CreateQuestionUseCaseTest {
    private final CreateQuestionStrategy createQuestionStrategy = mock(CreateQuestionStrategy.class);
    private final Collection<CreateQuestionStrategy> createQuestionStrategies = List.of(this.createQuestionStrategy);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final CreateQuestionUseCase createQuestionUseCase = new CreateQuestionUseCase(
            this.createQuestionStrategies,
            this.applicationEventPublisher
    );

    private Employee employee;
    private QuestionDto questionDto;
    private Question question;

    @BeforeEach
    void setUp() {
        this.employee = TestUtils.dummyObject(Employee.class);
        this.questionDto = new QuestionDto(
                UUID.randomUUID().toString(),
                TypeQuestion.DISCURSIVE,
                Set.of()
        );
        this.question = TestUtils.dummyObject(Question.class);
    }

    @Test
    void when_prompted_should_successfully_save_a_question() {
        when(this.createQuestionStrategy.getTypeQuestion()).thenReturn(this.questionDto.getType());
        when(this.createQuestionStrategy.execute(this.questionDto)).thenReturn(this.question);

        assertDoesNotThrow(() -> this.createQuestionUseCase.execute(this.employee, this.questionDto));

        verify(this.createQuestionStrategy, times(1)).getTypeQuestion();
        verify(this.createQuestionStrategy, times(1)).execute(this.questionDto);
        verify(this.applicationEventPublisher, only()).publishEvent(
                new NewRegisteredQuestion(
                        this.question.getIdentifier(),
                        this.question.getType()
                )
        );
        verifyNoMoreInteractions(this.createQuestionStrategy);
    }

    @Test
    void when_asked_should_throw_InternalErrorException_when_not_finding_a_strategy() {
        when(this.createQuestionStrategy.getTypeQuestion()).thenReturn(TypeQuestion.MULTIPLE_CHOICE);

        final InternalErrorException exception = assertThrows(
                InternalErrorException.class,
                () -> this.createQuestionUseCase.execute(this.employee, this.questionDto)
        );

        assertNotNull(exception);
        assertEquals(INTG_002, exception.getError());

        verify(this.createQuestionStrategy, only()).getTypeQuestion();
        verifyNoInteractions(this.applicationEventPublisher);
    }

}