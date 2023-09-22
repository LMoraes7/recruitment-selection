package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.query;

public enum SelectiveProcessCommands {
    SAVE("insert into selection_processes (id, title, description, responsibilities, requirements, additional_infos, status, desired_position) values (?, ?, ?, ?, ?, ?, ?, ?)"),
    FIND_BY_ID("select sp.id, sp.title, sp.description, sp.responsibilities, sp.requirements, sp.additional_infos, sp.status, sp.desired_position from selection_processes sp where sp.id = ?");

    public final String sql;

    SelectiveProcessCommands(String sql) {
        this.sql = sql;
    }
}
