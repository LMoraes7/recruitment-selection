package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Function;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Functionality.FUNC_CREATE_EMPLOYEE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Functionality.FUNC_CREATE_PROFILE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestItUtils.saveFunctions;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestItUtils.saveProfiles;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@SpringBootTest
final class ProfileFunctionRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProfileFunctionRepository profileFunctionRepository;

    private List<Function> functions;
    private Set<Profile> profiles;

    @BeforeEach
    void setUp() {
        this.functions = List.of(
                new Function(GeneratorIdentifier.forEmployee(), FUNC_CREATE_PROFILE),
                new Function(GeneratorIdentifier.forEmployee(), FUNC_CREATE_EMPLOYEE)
        );

        this.profiles = Set.of(
                new Profile(GeneratorIdentifier.forProfile(), "ADM_PROFILE", new HashSet<>(this.functions)),
                new Profile(GeneratorIdentifier.forProfile(), "EMP_PROFILE", Set.of(this.functions.get(1)))
        );
    }

    @Test
    @Transactional
    void when_prompted_it_should_save_the_relationship_between_profile_and_roles_successfully() {
        saveFunctions(this.jdbcTemplate, this.functions);
        saveProfiles(this.jdbcTemplate, this.profiles);

        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles_functions"));
        assertDoesNotThrow(() ->
            this.profiles.forEach(it -> this.profileFunctionRepository.saveRelationship(it))
        );
        assertEquals(3, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "profiles_functions"));
    }

}