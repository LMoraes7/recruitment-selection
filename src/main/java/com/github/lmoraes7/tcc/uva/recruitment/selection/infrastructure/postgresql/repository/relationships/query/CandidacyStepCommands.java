package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query;

public enum CandidacyStepCommands {
    SAVE("insert into applications_steps (id_application, id_step, id_next_step, status, limit_time, release_date) values (?, ?, ?, ?, ?, ?)");

    public final String sql;

    CandidacyStepCommands(String sql) {
        this.sql = sql;
    }
}
