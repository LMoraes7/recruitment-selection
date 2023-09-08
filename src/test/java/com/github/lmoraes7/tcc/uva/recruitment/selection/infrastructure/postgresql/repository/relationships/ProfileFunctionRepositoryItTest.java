package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Function;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Functionality.FUNC_CREATE_EMPLOYEE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Functionality.FUNC_CREATE_PROFILE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@SpringBootTest
final class ProfileFunctionRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProfileFunctionRepository profileFunctionRepository;

    private Profile profile;

    @BeforeEach
    void setUp() {
        this.profile = new Profile(
                "PRO-123456789",
                "PROFILE_TEST",
                Set.of(
                        new Function("FUN-123456789", FUNC_CREATE_PROFILE),
                        new Function("FUN-987654321", FUNC_CREATE_EMPLOYEE)
                )
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/profile_function_repository_test.sql"})
    void when_prompted_it_should_save_the_relationship_between_profile_and_roles_successfully() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles_functions"));
        assertDoesNotThrow(() -> this.profileFunctionRepository.saveRelationship(this.profile));
        assertEquals(
                this.profile.getFunctions().size(),
                JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles_functions")
        );
    }

}