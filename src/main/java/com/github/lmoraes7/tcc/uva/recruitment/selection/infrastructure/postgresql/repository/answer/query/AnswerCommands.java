package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.query;

public enum AnswerCommands {
    SAVE("insert into answers (id, description, correct, id_question) values (?, ?, ?, ?)");

    public final String sql;

    AnswerCommands(String sql) {
        this.sql = sql;
    }
}
