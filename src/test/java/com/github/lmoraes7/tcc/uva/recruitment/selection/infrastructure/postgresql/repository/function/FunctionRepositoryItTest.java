package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.function;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@SpringBootTest
final class FunctionRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FunctionRepository functionRepository;

    @Test
    @Transactional
    @Sql(scripts = {"/script/function_repository_test.sql"})
    void when_requested_should_return_a_collection_of_function_identifiers_found() {
        assertEquals(2, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "functions"));

        final List<String> identifiers = List.of(
                UUID.randomUUID().toString(),
                "FUN-123456789",
                UUID.randomUUID().toString(),
                "FUN-987654321"
        );

        assertDoesNotThrow(() -> {
            final Collection<String> fetchIdentifiers = this.functionRepository.fetchIdentifiers(identifiers);

            assertNotNull(fetchIdentifiers);
            assertEquals(2, fetchIdentifiers.size());
            assertTrue(fetchIdentifiers.contains(identifiers.get(1)));
            assertTrue(fetchIdentifiers.contains(identifiers.get(3)));
        });
        assertEquals(2, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "functions"));
    }

}