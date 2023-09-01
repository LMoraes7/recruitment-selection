package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.rowmapper.ProfileRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.ProfileFunctionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.query.ProfileCommands.*;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class ProfileRepositoryTest {

    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final ProfileFunctionRepository profileFunctionRepository = mock(ProfileFunctionRepository.class);
    private final ProfileRowMapper profileRowMapper = mock(ProfileRowMapper.class);
    private final ProfileRepository profileRepository = new ProfileRepository(
            this.jdbcTemplate,
            this.profileFunctionRepository,
            this.profileRowMapper
    );

    private Profile profile;
    private Collection<String> identifiers;

    @BeforeEach
    void setUp() {
        this.profile = dummyObject(Profile.class);
        this.identifiers = List.of(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
    }

    @Test
    void when_prompted_should_save_a_profile_successfully() {
        when(this.jdbcTemplate.update(SAVE.sql, this.profile.getIdentifier(), this.profile.getName()))
                .thenReturn(dummyObject(Integer.class));

        assertDoesNotThrow(() -> {
            final Profile profileSaved = this.profileRepository.save(this.profile);

            assertNotNull(profileSaved);
            assertEquals(this.profile.getIdentifier(), profileSaved.getIdentifier());
            assertEquals(this.profile.getName(), profileSaved.getName());
        });

        verify(this.jdbcTemplate, only()).update(SAVE.sql, this.profile.getIdentifier(), this.profile.getName());
        verify(this.profileFunctionRepository, only()).saveRelationship(this.profile);
    }

    @Test
    void when_requested_should_return_a_collection_of_profile_identifiers_found() {
        final String inSql = String.join(",", Collections.nCopies(this.identifiers.size(), "?"));

        when(this.jdbcTemplate.queryForList(
                        String.format(SELECT_IDENTIFIERS_IN.sql, inSql),
                        String.class,
                        this.identifiers.toArray()
                )).thenReturn(this.identifiers.stream().toList());

        assertDoesNotThrow(() -> {
            final Collection<String> fetchIdentifiers = this.profileRepository.fetchIdentifiers(this.identifiers);

            assertEquals(this.identifiers, fetchIdentifiers);
        });

        verify(this.jdbcTemplate, only()).queryForList(
                String.format(SELECT_IDENTIFIERS_IN.sql, inSql),
                String.class,
                this.identifiers.toArray()
        );
        verifyNoInteractions(this.profileFunctionRepository);
    }

    @Test
    void when_prompted_must_successfully_fetch_a_profile_by_id() {
        when(this.jdbcTemplate.queryForObject(
                FIND_BY_ID.sql,
                this.profileRowMapper,
                this.profile.getIdentifier()
        )).thenReturn(this.profile);

        assertDoesNotThrow(() -> {
            final Optional<Profile> optionalProfile = this.profileRepository.findById(
                    this.profile.getIdentifier()
            );

            assertNotNull(optionalProfile);
            assertTrue(optionalProfile.isPresent());
        });

        verify(this.jdbcTemplate, only()).queryForObject(
                FIND_BY_ID.sql,
                this.profileRowMapper,
                this.profile.getIdentifier()
        );
        verifyNoInteractions(this.profileFunctionRepository);
    }

    @Test
    void when_prompted_must_fetch_a_profile_by_the_failed_id() {
        when(this.jdbcTemplate.queryForObject(
                FIND_BY_ID.sql,
                this.profileRowMapper,
                this.profile.getIdentifier()
        )).thenThrow(EmptyResultDataAccessException.class);

        assertDoesNotThrow(() -> {
            final Optional<Profile> optionalProfile = this.profileRepository.findById(
                    this.profile.getIdentifier()
            );

            assertNotNull(optionalProfile);
            assertTrue(optionalProfile.isEmpty());
        });

        verify(this.jdbcTemplate, only()).queryForObject(
                FIND_BY_ID.sql,
                this.profileRowMapper,
                this.profile.getIdentifier()
        );
        verifyNoInteractions(this.profileFunctionRepository);
    }

}