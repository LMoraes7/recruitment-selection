package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query;

public enum SelectiveProcessStepCommands {
    SAVE("insert into selection_processes_steps (id_selective_process, id_step, id_next_step, limit_time) values (?, ?, ?, ?)");

    public final String sql;

    SelectiveProcessStepCommands(String sql) {
        this.sql = sql;
    }
}
