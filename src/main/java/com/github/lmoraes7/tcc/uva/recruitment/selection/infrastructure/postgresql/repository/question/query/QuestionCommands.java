package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.query;

public enum QuestionCommands {
    SAVE("insert into questions (id, description, type) values (?, ?, ?)");

    public final String sql;

    QuestionCommands(String sql) {
        this.sql = sql;
    }
}
