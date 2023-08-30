package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.function.query.FunctionCommands.SELECT_IDENTIFIERS_IN;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

final class FunctionRepositoryTest {

    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    private final FunctionRepository functionRepository = new FunctionRepository(this.jdbcTemplate);

    private Collection<String> identifiers;

    @BeforeEach
    void setUp() {
        this.identifiers = List.of(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
    }

    @Test
    void when_requested_should_return_a_collection_of_function_identifiers_found() {
        final String inSql = String.join(",", Collections.nCopies(this.identifiers.size(), "?"));

        when(this.jdbcTemplate.queryForList(
                String.format(SELECT_IDENTIFIERS_IN.sql, inSql),
                String.class,
                this.identifiers.toArray()
        )).thenReturn(this.identifiers.stream().toList());

        assertDoesNotThrow(() -> {
            final Collection<String> fetchIdentifiers = this.functionRepository.fetchIdentifiers(this.identifiers);

            assertEquals(this.identifiers, fetchIdentifiers);
        });

        verify(this.jdbcTemplate, only()).queryForList(
                String.format(SELECT_IDENTIFIERS_IN.sql, inSql),
                String.class,
                this.identifiers.toArray()
        );
    }

}