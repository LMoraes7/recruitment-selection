package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.query;

public enum CandidateCommands {
    SAVE("insert into candidates (id, name, cpf, email, date_of_birth, phones, adresses, username, password) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

    public final String sql;

    CandidateCommands(String sql) {
        this.sql = sql;
    }
}
