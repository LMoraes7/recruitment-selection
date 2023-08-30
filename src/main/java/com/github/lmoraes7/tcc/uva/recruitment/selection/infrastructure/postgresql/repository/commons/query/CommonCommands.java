package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.query;

public enum CommonCommands {
    SAVE_RECORDS("insert into registered_emails_documents (recorded_email, recorded_document) values (?, ?)"),
    SAVE_PASSWORD_CHANGE_REQUEST("insert into password_change_requests (code, email_entity, type_entity, limit_time, is_used) values (?, ?, ?, ?, ?)");

    public final String sql;

    CommonCommands(final String sql) {
        this.sql = sql;
    }
}
