package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.query;

public enum QuestionCommands {
    SELECT_IDENTIFIERS_IN("select q.id, q.type from questions q where q.id in (%s)"),
    SAVE("insert into questions (id, description, type) values (?, ?, ?)");

    public final String sql;

    QuestionCommands(String sql) {
        this.sql = sql;
    }
}
