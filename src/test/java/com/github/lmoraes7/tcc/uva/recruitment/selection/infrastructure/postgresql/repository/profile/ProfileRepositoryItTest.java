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
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Functionality.FUNC_CREATE_EMPLOYEE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Functionality.FUNC_CREATE_PROFILE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestItUtils.saveFunctions;
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
          new Function(GeneratorIdentifier.forEmployee(), FUNC_CREATE_PROFILE),
          new Function(GeneratorIdentifier.forEmployee(), FUNC_CREATE_EMPLOYEE)
        );

        this.profile = new Profile(GeneratorIdentifier.forProfile(), "ADM_PROFILE", this.functions);
    }

    @Test
    @Transactional
    void when_prompted_should_save_a_profile_successfully() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));

        saveFunctions(this.jdbcTemplate, this.functions);
        this.profileRepository.save(this.profile);

        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));
    }

    @Test
    @Transactional
    void when_saving_a_profile_with_a_name_already_registered_it_must_throw_a_DataIntegrityViolationException() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));

        saveFunctions(this.jdbcTemplate, this.functions);
        this.profileRepository.save(this.profile);

        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));

        assertThrows(
                DataIntegrityViolationException.class,
                () -> this.profileRepository.save(new Profile(GeneratorIdentifier.forProfile(), this.profile.getName(), this.functions))
        );
//        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));
    }

    @Test
    @Transactional
    void when_requested_should_return_a_collection_of_profile_identifiers_found() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));

        saveFunctions(this.jdbcTemplate, this.functions);
        this.profileRepository.save(this.profile);

        assertDoesNotThrow(
                () -> {
                    final Collection<String> fetchIdentifiers = this.profileRepository.fetchIdentifiers(
                            List.of(
                                    UUID.randomUUID().toString(),
                                    this.profile.getIdentifier(),
                                    UUID.randomUUID().toString()
                            )
                    );

                    assertNotNull(fetchIdentifiers);
                    assertEquals(1, fetchIdentifiers.size());
                    assertTrue(fetchIdentifiers.contains(this.profile.getIdentifier()));
                }
        );
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles"));
    }

}