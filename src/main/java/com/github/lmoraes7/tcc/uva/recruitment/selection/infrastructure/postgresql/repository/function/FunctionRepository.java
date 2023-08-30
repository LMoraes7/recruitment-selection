package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.function.query.FunctionCommands.SELECT_IDENTIFIERS_IN;

@Repository
public class FunctionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FunctionRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<String> fetchIdentifiers(final Collection<String> identifiers) {
        final String inSql = String.join(",", Collections.nCopies(identifiers.size(), "?"));
        return this.jdbcTemplate.queryForList(
                String.format(SELECT_IDENTIFIERS_IN.sql, inSql),
                String.class,
                identifiers.toArray()
        );
    }

}
