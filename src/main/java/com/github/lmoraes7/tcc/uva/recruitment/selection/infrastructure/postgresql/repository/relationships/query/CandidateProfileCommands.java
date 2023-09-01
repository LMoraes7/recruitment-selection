package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query;

public enum CandidateProfileCommands {
    SAVE("insert into candidates_profiles (id_candidate, id_profile) values (?, ?)");

    public final String sql;

    CandidateProfileCommands(String sql) {
        this.sql = sql;
    }
}
