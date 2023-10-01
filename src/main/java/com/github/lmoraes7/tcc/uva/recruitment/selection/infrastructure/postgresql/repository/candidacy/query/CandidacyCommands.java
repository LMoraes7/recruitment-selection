package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.query;

public enum CandidacyCommands {
    SAVE("insert into applications (id, status, id_candidate, id_selective_process) values (?, ?, ?, ?)"),
    FIND_BY_ID(
            "select " +
                            "a.id as application_id, " +
                            "a.status as application_status, " +
                            "sp.id as selective_process_id, " +
                            "sp.title as selective_process_title, " +
                            "ap.id_step as application_step_id, " +
                            "ap.id_next_step as application_step_id_next, " +
                            "ap.status as application_step_status, " +
                            "s.title as step_title, " +
                            "s.type as step_type " +
                "from applications a " +
                    "inner join selection_processes sp " +
                        "on a.id_selective_process = sp.id " +
                    "inner join applications_steps ap " +
                        "on a.id = ap.id_application " +
                    "inner join steps s " +
                        "on ap.id_step = s.id " +
                "where a.id = ? and a.id_candidate = ?"
    ),
    FIND_ALL_BY_CANDIDATE_ID(
            "select " +
                            "a.id, " +
                            "a.status, " +
                            "sp.title " +
                "from applications a " +
                    "inner join selection_processes sp " +
                        "on a.id_selective_process = sp.id " +
                "where a.id_candidate = ? " +
                "limit ? offset ?"
    ),
    COUNT_BY_CANDIDATE_ID("select count(*) from applications a where a.id_candidate = ?"),
    CLOSE_CANDIDACY("update applications a set a.status = 'CLOSED' where a.id = ? and a.id_candidate = ? and a.id_selective_process = ?"),
    CLOSE_STEPS_CANDIDACY("update applications_steps set status = 'BLOCKED' where id_application = ? and status = 'WAITING_FOR_EXECUTION'"),
    CLOSE_CANDIDACY_BY_SELECTIVE_PROCESS("update applications a set a.status = 'CLOSED' where a.id_selective_process = ?"),
    CLOSE_STEPS_CANDIDACY_BY_SELECTIVE_PROCESS(
            "update applications_steps set status = 'BLOCKED' where id_application in (" +
                    "select " +
                        "distinct a.id " +
                    "from applications a " +
                        "inner join applications_steps ass " +
                        "on a.id = ass.id_application " +
                    "where a.id_selective_process = ? " +
                ")"
    );

    public final String sql;

    CandidacyCommands(String sql) {
        this.sql = sql;
    }
}
