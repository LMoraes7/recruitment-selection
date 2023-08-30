package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.query;

public enum ProfileCommands {
    COUNT_BY_NAME("select count(*) from profiles p where p.name = ?"),
    SAVE("insert into profiles (id, name) values (?, ?)"),
    SELECT_IDENTIFIERS_IN("select p.id from profiles p where p.id in (%s)");

    public final String sql;

    ProfileCommands(String sql) {
        this.sql = sql;
    }
}
