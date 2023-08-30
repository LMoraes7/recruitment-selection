package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Function;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch.SaveProfileFunctionBatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query.ProfileFunctionCommands.SAVE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class ProfileFunctionRepositoryTest {

    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    private final ProfileFunctionRepository profileFunctionRepository = new ProfileFunctionRepository(this.jdbcTemplate);

    private Profile profile;

    @BeforeEach
    void setUp() {
        this.profile = dummyObject(Profile.class);
    }

    @Test
    void when_prompted_it_should_save_the_relationship_between_profile_and_roles_successfully() {
        final SaveProfileFunctionBatch saveProfileFunctionBatch = new SaveProfileFunctionBatch(
                this.profile.getIdentifier(),
                this.profile.getFunctions().stream().map(Function::getIdentifier).toList()
        );

        when(this.jdbcTemplate.batchUpdate(SAVE.sql, saveProfileFunctionBatch)).thenReturn(new int[]{});

        assertDoesNotThrow(() -> this.profileFunctionRepository.saveRelationship(this.profile));

        verify(this.jdbcTemplate, only()).batchUpdate(SAVE.sql, saveProfileFunctionBatch);
    }

}