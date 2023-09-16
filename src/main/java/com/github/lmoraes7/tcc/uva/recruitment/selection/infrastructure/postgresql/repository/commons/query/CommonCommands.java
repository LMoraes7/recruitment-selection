package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.query;

public enum CommonCommands {
    SAVE_RECORDS("insert into registered_emails_documents (recorded_email, recorded_document, type_entity) values (?, ?, ?)"),
    FIND_REGISTER_ENTITY_BY_EMAIL("select r.recorded_email, r.recorded_document, r.type_entity from registered_emails_documents r where r.recorded_email = ?"),
    SAVE_PASSWORD_CHANGE_REQUEST("insert into password_change_requests (code, email_entity, type_entity, requested_in, expired_in, is_used) values (?, ?, ?, ?, ?, ?)"),
    FIND_REQUEST_CHANGE_BY_CODE("select pcr.code, pcr.email_entity, pcr.type_entity, pcr.requested_in, pcr.expired_in, pcr.is_used from password_change_requests pcr where pcr.code = ?"),
    CLOSE_BY_CODE("update password_change_requests pcr set pcr.is_used = 'true' where pcr.code = ?");

    public final String sql;

    CommonCommands(final String sql) {
        this.sql = sql;
    }
}
