insert into registered_emails_documents (recorded_email, recorded_document) values ('email_test@email.com.br', '27005990048');

insert into password_change_requests (code, email_entity, type_entity, requested_in, expired_in, is_used) values ('RST-123456789', 'email_test@email.com.br', 'EMP', '2022-01-08 04:05:06', '2023-01-08 04:05:06', false);