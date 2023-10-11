package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.feedback;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.feedback.query.FeedbackCommands.SAVE;

@Repository
public class FeedbackRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FeedbackRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Feedback save(final Feedback feedback, final String candidacyIdentifier, final String stepIdentifier) {
        this.jdbcTemplate.update(
                SAVE.sql,
                feedback.getIdentifier(),
                feedback.getResult().name(),
                feedback.getPointing(),
                feedback.getAdditionalInfo(),
                candidacyIdentifier,
                stepIdentifier
        );
        return feedback;
    }

}
