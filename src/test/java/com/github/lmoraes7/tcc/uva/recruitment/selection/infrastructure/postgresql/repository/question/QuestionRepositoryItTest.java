package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Answer;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@SpringBootTest
class QuestionRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private QuestionRepository questionRepository;

    private Question questionWithAnswers;
    private Question questionWithoutAnswers;

    @BeforeEach
    void setUp() {
        this.questionWithAnswers = new Question(
                GeneratorIdentifier.forQuestion(),
                "Descrição qualquer para a questão",
                TypeQuestion.MULTIPLE_CHOICE,
                Set.of(new Answer(
                        GeneratorIdentifier.forAnswer(),
                        "Descrição qualquer para a resposta",
                        true
                ))
        );

        this.questionWithoutAnswers = new Question(
                GeneratorIdentifier.forQuestion(),
                "Descrição qualquer para a questão",
                TypeQuestion.MULTIPLE_CHOICE
        );
    }

    @Test
    @Transactional
    void when_prompted_should_successfully_save_a_question_with_answers() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "questions"));
        assertDoesNotThrow(() -> this.questionRepository.saveWithAnswers(this.questionWithAnswers));
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "questions"));
    }

    @Test
    @Transactional
    void when_prompted_should_successfully_save_a_question_without_answers() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "questions"));
        assertDoesNotThrow(() -> this.questionRepository.saveWithoutAnswers(this.questionWithoutAnswers));
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "questions"));
    }

}