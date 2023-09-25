package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.query;

public enum CandidacyCommands {
    SAVE("insert into applications (id, status, id_candidate, id_selective_process) values (?, ?, ?, ?)");

    public final String sql;

    CandidacyCommands(String sql) {
        this.sql = sql;
    }
}
