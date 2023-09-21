package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.query;

public enum SelectiveProcessCommands {
    SAVE("insert into selection_processes (id, title, description, responsibilities, requirements, additional_infos, status, desired_position) values (?, ?, ?, ?, ?, ?, ?, ?)");

    public final String sql;

    SelectiveProcessCommands(String sql) {
        this.sql = sql;
    }
}
