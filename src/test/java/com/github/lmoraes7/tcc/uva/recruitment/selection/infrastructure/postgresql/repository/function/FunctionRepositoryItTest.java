package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.function;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Functionality.FUNC_CREATE_EMPLOYEE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Functionality.FUNC_CREATE_PROFILE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestItUtils.saveFunctions;
import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@SpringBootTest
final class FunctionRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FunctionRepository functionRepository;

    private List<Function> functions;

    @BeforeEach
    void setUp() {
        this.functions = List.of(
                new Function(GeneratorIdentifier.forEmployee(), FUNC_CREATE_PROFILE),
                new Function(GeneratorIdentifier.forEmployee(), FUNC_CREATE_EMPLOYEE)
        );
    }

    @Test
    @Transactional
    void when_requested_should_return_a_collection_of_function_identifiers_found() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "functions"));

        saveFunctions(this.jdbcTemplate, this.functions);

        assertDoesNotThrow(() -> {
            final Collection<String> fetchIdentifiers = this.functionRepository.fetchIdentifiers(
                    List.of(
                            UUID.randomUUID().toString(),
                            this.functions.get(0).getIdentifier(),
                            UUID.randomUUID().toString(),
                            this.functions.get(1).getIdentifier()
                    )
            );

            assertNotNull(fetchIdentifiers);
            assertEquals(2, fetchIdentifiers.size());
            assertTrue(fetchIdentifiers.contains(this.functions.get(0).getIdentifier()));
            assertTrue(fetchIdentifiers.contains(this.functions.get(1).getIdentifier()));
        });
        assertEquals(2, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "functions"));
    }

}