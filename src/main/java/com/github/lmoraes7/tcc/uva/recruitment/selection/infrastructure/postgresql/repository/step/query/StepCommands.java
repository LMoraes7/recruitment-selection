package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query;

public enum StepCommands {
    SAVE("insert into steps (id, title, description, type) values (?, ?, ?, ?)"),
    SAVE_UPLOAD_DATA("insert into steps_upload_files (id_step, description, type) values (?, ?, ?)"),
    SAVE_THEORICAL_DATA("insert into steps_theoretical_tests (id_step, id_question) values (?, ?)");

    public final String sql;

    StepCommands(String sql) {
        this.sql = sql;
    }
}
