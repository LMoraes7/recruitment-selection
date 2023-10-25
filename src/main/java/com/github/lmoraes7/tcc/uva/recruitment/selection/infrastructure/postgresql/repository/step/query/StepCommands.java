package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query;

public enum StepCommands {
    SELECT_IDENTIFIERS_IN("select s.id, s.type from steps s where s.id in (%s)"),
    FIND(
            "select " +
                    "ass.id_step as step_id, " +
                    "ass.status as step_status, " +
                    "ass.limit_time as step_limit_time, " +
                    "ass.release_date as step_release_date " +
                "from applications_steps ass " +
                    "inner join selection_processes_steps sps " +
                        "on ass.id_step = sps.id_step " +
                    "inner join applications a " +
                        "on ass.id_application = a.id " +
                "where ass.id_step = ? and sps.id_selective_process = ? and ass.id_application = ? and a.id_candidate = ?"
    ),
    SAVE("insert into steps (id, title, description, type) values (?, ?, ?, ?)"),
    SAVE_UPLOAD_DATA("insert into steps_upload_files (id_step, description, type) values (?, ?, ?)"),
    FIND_FILES_TYPE_BY_STEP_ID("select suf.type from steps_upload_files suf where suf.id_step = ?"),
    SAVE_THEORICAL_DATA("insert into steps_theoretical_tests (id_step, id_question) values (?, ?)"),
    SAVE_EXECUTION_QUESTION_MULTIPLE_CHOICE(
            "insert into applications_steps_theoretical_tests (id_application, id_step, id_question, id_answer, type_question, discursive_answer) values (?, ?, ?, ?, ?, ?)"
    ),
    SAVE_EXECUTION_UPLOAD_FILES_STEP(
            "insert into applications_steps_upload_files (id_application, id_step, file_name, file, type) values (?, ?, ?, ?, ?)"
    ),
    FIND_QUESTIONS_TO_BE_EXECUTED(
            "select " +
                    "ap.id as candidacy_id, " +
                    "ap.id_selective_process as selective_process_id, " +
                    "ap.id_candidate as candidate_id, " +
                    "ast.id_step as step_id, " +
                    "stt.id_question as question_id, " +
                    "q.description as question_description, " +
                    "q.type as question_type, " +
                    "a.id as answer_id, " +
                    "a.description as answer_description " +
                "from applications ap " +
                    "inner join applications_steps ast " +
                        "on ap.id = ast.id_application " +
                    "inner join steps_theoretical_tests stt " +
                        "on ast.id_step = stt.id_step " +
                    "inner join questions q " +
                        "on stt.id_question = q.id " +
                    "left join answers a " +
                        "on q.id = a.id_question " +
                "where ap.id = ? and ap.id_candidate = ? and ap.id_selective_process = ? and ast.id_step = ?"
    ),
    FIND_FILES_TO_BE_SENT(
            "select " +
                    "ap.id as candidacy_id, " +
                    "ap.id_selective_process as selective_process_id, " +
                    "ap.id_candidate as candidate_id, " +
                    "ast.id_step as step_id, " +
                    "suf.description as file_description, " +
                    "suf.type as file_type " +
                "from applications ap " +
                    "inner join applications_steps ast " +
                        "on ap.id = ast.id_application " +
                    "inner join steps_upload_files suf " +
                        "on ast.id_step = suf.id_step " +
                "where ap.id = ? and ap.id_candidate = ? and ap.id_selective_process = ? and ast.id_step = ?"
    ),
    EXTERNAL_TO_BE_EXECUTED(
            "select " +
                    "ap.id as candidacy_id, " +
                    "ap.id_selective_process as selective_process_id, " +
                    "ap.id_candidate as candidate_id, " +
                    "ast.id_step as step_id, " +
                    "aps.link as external_link, " +
                    "aps.date_and_time as external_date_and_time " +
                "from applications ap " +
                    "inner join applications_steps ast " +
                        "on ap.id = ast.id_application " +
                    "inner join applications_steps_external aps " +
                        "on ast.id_step = aps.id_step " +
                "where ap.id = ? and ap.id_candidate = ? and ap.id_selective_process = ? and ast.id_step = ?"
    ),
    UPDATE_STATUS_STEP_CANDIDACY("update applications_steps ass set ass.status = ? where ass.id_step = ? and ass.id_application = ?"),
    FIND_QUESTIONS_EXECUTEDS(
            "select " +
                    "astt.id_application as application_identifier, " +
                    "astt.id_step as step_identifier, " +
                    "astt.id_question as question_identifier, " +
                    "astt.id_answer as answer_identifier, " +
                    "astt.type_question as question_type, " +
                    "astt.discursive_answer as discursive_answer, " +
                    "q.description as question_description, " +
                    "a.description as answer_description, " +
                    "a.correct as answer_correct " +
                "from applications_steps_theoretical_tests astt " +
                    "inner join questions q " +
                        "on astt.id_question = q.id " +
                    "left join answers a " +
                        "on astt.id_answer = a.id " +
                "where astt.id_application = ? and astt.id_step = ?"
    ),
    FIND_FILES_UPLOADS(
            "select " +
                    "asuf.id_application as application_identifier, " +
                    "asuf.id_step as step_identifier, " +
                    "asuf.file_name as file_name, " +
                    "asuf.file as file, " +
                    "asuf.type as file_type " +
                " from applications_steps_upload_files asuf where asuf.id_application = ? and asuf.id_step = ?"
    ),
    UPDATE_DATA_EXTERNAL("update applications_steps_external ase set ase.link = ?, ase.date_and_time = ? where ase.id_application = ? and ase.id_step = ?");

    public final String sql;

    StepCommands(String sql) {
        this.sql = sql;
    }
}
