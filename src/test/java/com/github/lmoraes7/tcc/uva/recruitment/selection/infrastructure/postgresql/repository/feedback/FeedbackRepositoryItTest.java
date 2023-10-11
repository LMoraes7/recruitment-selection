package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.feedback;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Feedback;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@SpringBootTest
class FeedbackRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FeedbackRepository feedbackRepository;

    private Feedback feedback;
    private String candidacyIdentifier;
    private String stepIdentifier;

    @BeforeEach
    void setUp() {
        this.feedback = new Feedback(
                GeneratorIdentifier.forFeedback(),
                Result.APPROVED,
                new BigDecimal("7.59"),
                "fdhfhyht"
        );
        this.candidacyIdentifier = "APP-123456789";
        this.stepIdentifier = "STE-123456789";
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/feedback_repository_test.sql"})
    void when_prompted_you_should_save_feedback_successfully() {
        assertEquals(
                0,
                JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "feedback")
        );

        assertDoesNotThrow(() -> this.feedbackRepository.save(this.feedback, this.candidacyIdentifier, this.stepIdentifier));

        assertEquals(
                1,
                JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "feedback")
        );
    }
}