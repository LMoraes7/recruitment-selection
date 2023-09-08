package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.query;

public enum CandidateCommands {
    SAVE("insert into candidates (id, name, cpf, email, date_of_birth, phones, adresses, username, password) values (?, ?, ?, ?, ?, ?, ?, ?, ?)"),
    CHANGE_PASSWORD_BY_USERNAME("update candidates c set c.password = ? where c.username = ?");

    public final String sql;

    CandidateCommands(String sql) {
        this.sql = sql;
    }
}
