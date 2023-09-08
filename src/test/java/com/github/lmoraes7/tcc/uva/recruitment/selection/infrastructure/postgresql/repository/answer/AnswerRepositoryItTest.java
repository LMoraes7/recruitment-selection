package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@SpringBootTest
final class AnswerRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AnswerRepository answerRepository;

    private List<Answer> answers;

    @BeforeEach
    void setUp() {
        this.answers = List.of(
                new Answer(
                        GeneratorIdentifier.forAnswer(),
                        "Descrição qualquer para a resposta",
                        true
                ),
                new Answer(
                        GeneratorIdentifier.forAnswer(),
                        "Descrição qualquer para a resposta 2",
                        false
                ),
                new Answer(
                        GeneratorIdentifier.forAnswer(),
                        "Descrição qualquer para a resposta 3",
                        true
                ),
                new Answer(
                        GeneratorIdentifier.forAnswer(),
                        "Descrição qualquer para a resposta 4",
                        false
                )
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/answer_repository_test.sql"})
    void when_prompted_should_save_a_response_successfully() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "answers"));
        assertDoesNotThrow(() -> this.answerRepository.save("QUE-123456789", this.answers));
        assertEquals(this.answers.size(), JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "answers"));
    }

}