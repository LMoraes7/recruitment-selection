package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Function;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch.SaveProfileFunctionBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query.ProfileFunctionCommands.SAVE;

@Repository
public class ProfileFunctionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfileFunctionRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveRelationship(final Profile profile) {
        this.jdbcTemplate.batchUpdate(
                SAVE.sql,
                new SaveProfileFunctionBatch(
                        profile.getIdentifier(),
                        profile.getFunctions().stream().map(Function::getIdentifier).toList()
                )
        );
    }

}
