package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.feedback.query;

public enum FeedbackCommands {
    SAVE("insert into feedback (id, result, pointing, additional_info, id_application, id_step) values (?, ?, ?, ?, ?, ?)");

    public final String sql;

    FeedbackCommands(String sql) {
        this.sql = sql;
    }
}
