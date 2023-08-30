package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.ProfileFunctionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.query.ProfileCommands.SAVE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.query.ProfileCommands.SELECT_IDENTIFIERS_IN;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class ProfileRepositoryTest {

    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    private final ProfileFunctionRepository profileFunctionRepository = Mockito.mock(ProfileFunctionRepository.class);
    private final ProfileRepository profileRepository = new ProfileRepository(
            this.jdbcTemplate,
            this.profileFunctionRepository
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

}