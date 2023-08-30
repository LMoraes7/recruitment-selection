package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.function.query;

public enum FunctionCommands {
    SELECT_IDENTIFIERS_IN("select f.id from functions f where f.id in (%s)");

    public final String sql;

    FunctionCommands(String sql) {
        this.sql = sql;
    }
}
