package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.query;

public enum SelectiveProcessCommands {
    SAVE("insert into selection_processes (id, title, description, responsibilities, requirements, additional_infos, status, desired_position) values (?, ?, ?, ?, ?, ?, ?, ?)"),
    COUNT_OPEN("select count(*) from selection_processes sp where sp.status = 'IN_PROGRESS'"),
    FIND_ALL_OPEN("select sp.id, sp.title from selection_processes sp where sp.status = 'IN_PROGRESS' limit ? offset ?"),
    FIND_BY_ID("select sp.id, sp.title, sp.description, sp.responsibilities, sp.requirements, sp.additional_infos, sp.status, sp.desired_position from selection_processes sp where sp.id = ?"),
    FIND_WITH_STEPS_BY_ID("select " +
                                        "sp.id as id_selective_process, " +
                                        "sp.status as status_selective_process, " +
                                        "sps.id_step as id_step, " +
                                        "sps.id_next_step as id_next_step, " +
                                        "sps.limit_time as step_limit_time, " +
                                        "s.type as step_type " +
                                "from selection_processes sp " +
                                    "inner join selection_processes_steps sps " +
                                        "on sp.id = sps.id_selective_process " +
                                    "inner join steps s " +
                                        "on sps.id_step = s.id " +
                                "where sp.id = ?"
    ),
    UPDATE_STATUS("update selection_processes sp set sp.status = ? where sp.id = ?");

    public final String sql;

    SelectiveProcessCommands(String sql) {
        this.sql = sql;
    }
}
