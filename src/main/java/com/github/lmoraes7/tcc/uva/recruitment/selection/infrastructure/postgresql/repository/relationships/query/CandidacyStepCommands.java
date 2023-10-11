package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query;

public enum CandidacyStepCommands {
    SAVE("insert into applications_steps (id_application, id_step, id_next_step, status, limit_time, release_date) values (?, ?, ?, ?, ?, ?)"),
    SELECT_STEPS(
            "select " +
                    "ass.id_step as step_identifier, " +
                    "ass.id_next_step as next_step_identifier, " +
                    "ass.status as step_status, " +
                    "s.type as step_type " +
                "from applications_steps ass " +
                    "inner join steps s " +
                        "on ass.id_step = s.id " +
                "where ass.id_application = ? and (ass.id_step = ? or ass.id_next_step = ?)"
    ),
    UPDATE_STATUS("update applications_steps ass set ass.status = ? where ass.id_application = ? and ass.id_step = ?"),
    SELECT_ONE_STEP(
            "select " +
                    "ass.id_step as step_identifier, " +
                    "ass.id_next_step as next_step_identifier, " +
                    "ass.status as step_status, " +
                    "s.type as step_type " +
                "from applications_steps ass " +
                    "inner join steps s " +
                        "on ass.id_step = s.id " +
                "where ass.id_application = ? and ass.id_step = ?"
    );

    public final String sql;

    CandidacyStepCommands(String sql) {
        this.sql = sql;
    }
}
