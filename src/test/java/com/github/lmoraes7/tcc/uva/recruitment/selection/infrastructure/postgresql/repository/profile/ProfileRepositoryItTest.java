package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Function;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Functionality.FUNC_CREATE_EMPLOYEE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Functionality.FUNC_CREATE_PROFILE;
import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@SpringBootTest
final class ProfileRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProfileRepository profileRepository;

    private Profile profile;
    private Set<Function> functions;

    @BeforeEach
    void setUp() {
        this.functions = Set.of(
          new Function("FUN-123456789", FUNC_CREATE_PROFILE),
          new Function("FUN-987654321", FUNC_CREATE_EMPLOYEE)
        );

        this.profile = new Profile(GeneratorIdentifier.forProfile(), "ADM_PROFILE", this.functions);
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/profile_repository_test.sql"})
    void when_prompted_should_save_a_profile_successfully() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));

        assertDoesNotThrow(() -> this.profileRepository.save(this.profile));

        assertEquals(2, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/profile_repository_test.sql"})
    void when_saving_a_profile_with_a_name_already_registered_it_must_throw_a_DataIntegrityViolationException() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));
        assertThrows(
                DataIntegrityViolationException.class,
                () -> this.profileRepository.save(new Profile(GeneratorIdentifier.forProfile(), "PROFILE_TEST", this.functions))
        );
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/profile_repository_test.sql"})
    void when_requested_should_return_a_collection_of_profile_identifiers_found() {
        final List<String> identifiers = List.of(
                UUID.randomUUID().toString(),
                "PRO-123456789",
                UUID.randomUUID().toString()
        );

        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));
        assertDoesNotThrow(
                () -> {
                    final Collection<String> fetchIdentifiers = this.profileRepository.fetchIdentifiers(identifiers);

                    assertNotNull(fetchIdentifiers);
                    assertEquals(1, fetchIdentifiers.size());
                    assertTrue(fetchIdentifiers.contains(identifiers.get(1)));
                }
        );
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/profile_repository_test.sql"})
    void when_prompted_must_successfully_fetch_a_profile_by_id() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));
        assertDoesNotThrow(() -> {
            final Optional<Profile> optionalProfile = this.profileRepository.findById("PRO-123456789");

            assertNotNull(optionalProfile);
            assertTrue(optionalProfile.isPresent());
        });
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/profile_repository_test.sql"})
    void when_prompted_must_fetch_a_profile_by_the_failed_id() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));
        assertDoesNotThrow(() -> {
            final Optional<Profile> optionalProfile = this.profileRepository.findById(GeneratorIdentifier.forProfile());

            assertNotNull(optionalProfile);
            assertTrue(optionalProfile.isEmpty());
        });
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));
    }

}