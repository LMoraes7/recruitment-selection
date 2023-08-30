package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.ProfileFunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.query.ProfileCommands.SAVE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.query.ProfileCommands.SELECT_IDENTIFIERS_IN;

@Repository
public class ProfileRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ProfileFunctionRepository profileFunctionRepository;

    @Autowired
    public ProfileRepository(
            final JdbcTemplate jdbcTemplate,
            final ProfileFunctionRepository profileFunctionRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.profileFunctionRepository = profileFunctionRepository;
    }

    public Collection<String> fetchIdentifiers(final Collection<String> identifiers) {
        final String inSql = String.join(",", Collections.nCopies(identifiers.size(), "?"));
        return this.jdbcTemplate.queryForList(
                String.format(SELECT_IDENTIFIERS_IN.sql, inSql),
                String.class,
                identifiers.toArray()
        );
    }

    public Profile save(final Profile profile) {
        this.jdbcTemplate.update(SAVE.sql, profile.getIdentifier(), profile.getName());
        this.profileFunctionRepository.saveRelationship(profile);

        return profile;
    }

}
