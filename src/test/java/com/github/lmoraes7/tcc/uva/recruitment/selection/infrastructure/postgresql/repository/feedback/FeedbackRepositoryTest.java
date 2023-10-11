package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.feedback;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Feedback;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.feedback.query.FeedbackCommands.SAVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class FeedbackRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final FeedbackRepository feedbackRepository = new FeedbackRepository(this.jdbcTemplate);

    private Feedback feedback;
    private String candidacyIdentifier;
    private String stepIdentifier;

    @BeforeEach
    void setUp() {
        this.feedback = TestUtils.dummyObject(Feedback.class);
        this.candidacyIdentifier = GeneratorIdentifier.forCandidacy();
        this.stepIdentifier = GeneratorIdentifier.forStep();
    }

    @Test
    void when_prompted_you_should_save_feedback_successfully() {
        when(this.jdbcTemplate.update(
                SAVE.sql,
                this.feedback.getIdentifier(),
                this.feedback.getResult().name(),
                this.feedback.getPointing(),
                this.feedback.getAdditionalInfo(),
                this.candidacyIdentifier,
                this.stepIdentifier
        )).thenReturn(1);

        assertDoesNotThrow(() -> this.feedbackRepository.save(this.feedback, this.candidacyIdentifier, this.stepIdentifier));

        verify(this.jdbcTemplate, only()).update(
                SAVE.sql,
                this.feedback.getIdentifier(),
                this.feedback.getResult().name(),
                this.feedback.getPointing(),
                this.feedback.getAdditionalInfo(),
                this.candidacyIdentifier,
                this.stepIdentifier
        );
    }

}