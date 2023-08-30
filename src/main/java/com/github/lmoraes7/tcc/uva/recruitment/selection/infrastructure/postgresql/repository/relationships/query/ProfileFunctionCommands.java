package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query;

public enum ProfileFunctionCommands {
    SAVE("insert into profiles_functions (id_profile, id_function) values (?, ?)");

    public final String sql;

    ProfileFunctionCommands(String sql) {
        this.sql = sql;
    }
}
