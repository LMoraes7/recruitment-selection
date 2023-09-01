package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.entity.ProfileEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.rowmapper.ProfileRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.ProfileFunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.query.ProfileCommands.*;

@Repository
public class ProfileRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ProfileFunctionRepository profileFunctionRepository;
    private final ProfileRowMapper profileRowMapper;

    @Autowired
    public ProfileRepository(
            final JdbcTemplate jdbcTemplate,
            final ProfileFunctionRepository profileFunctionRepository,
            final ProfileRowMapper profileRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.profileFunctionRepository = profileFunctionRepository;
        this.profileRowMapper = profileRowMapper;
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
        final ProfileEntity profileEntity = toEntity(profile);

        this.jdbcTemplate.update(SAVE.sql, profileEntity.getIdentifier(), profileEntity.getName());
        this.profileFunctionRepository.saveRelationship(profile);

        return profile;
    }

    public Optional<Profile> findById(final String id) {
        Profile profile = null;
        try {
            profile = this.jdbcTemplate.queryForObject(
                    FIND_BY_ID.sql,
                    this.profileRowMapper,
                    id
            );
        } catch (final EmptyResultDataAccessException ignored) {
        }

        return Optional.ofNullable(profile);
    }

}
