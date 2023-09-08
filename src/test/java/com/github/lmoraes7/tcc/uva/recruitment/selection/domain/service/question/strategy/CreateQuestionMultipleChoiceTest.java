package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.strategy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.dto.QuestionDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.QuestionRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

final class CreateQuestionMultipleChoiceTest {

    private final QuestionRepository questionRepository = Mockito.mock(QuestionRepository.class);
    private final CreateQuestionMultipleChoice createQuestionMultipleChoice = new CreateQuestionMultipleChoice(this.questionRepository);

    private QuestionDto questionDto;

    @BeforeEach
    void setUp() {
        this.questionDto = TestUtils.dummyObject(QuestionDto.class);
    }

    @Test
    void when_requested_it_should_return_the_type_of_question() {
        assertDoesNotThrow(() -> assertEquals(TypeQuestion.MULTIPLE_CHOICE, this.createQuestionMultipleChoice.getTypeQuestion()));

        verifyNoInteractions(this.questionRepository);
    }

    @Test
    void when_prompted_should_successfully_save_the_question() {
        final Question question = ConverterHelper.toModel(this.questionDto);

        when(this.questionRepository.saveWithAnswers(any(Question.class))).thenReturn(question);

        assertDoesNotThrow(() -> this.createQuestionMultipleChoice.execute(this.questionDto));

        verify(this.questionRepository, only()).saveWithAnswers(any(Question.class));
    }

}