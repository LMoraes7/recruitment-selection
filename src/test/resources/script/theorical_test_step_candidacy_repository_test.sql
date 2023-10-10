insert into questions (id, description, type) values ('QUE-123456781', 'Questao 1', 'MULTIPLE_CHOICE');
insert into questions (id, description, type) values ('QUE-123456782', 'Questao 2', 'MULTIPLE_CHOICE');

insert into questions (id, description, type) values ('QUE-123456783', 'Questao 3', 'DISCURSIVE');
insert into questions (id, description, type) values ('QUE-123456784', 'Questao 4', 'DISCURSIVE');

insert into answers (id, description, correct, id_question) values ('ANS-123456781', 'Resposta 1 da questao 1', 'false', 'QUE-123456781');
insert into answers (id, description, correct, id_question) values ('ANS-123456782', 'Resposta 2 da questao 1', 'false', 'QUE-123456781');
insert into answers (id, description, correct, id_question) values ('ANS-123456783', 'Resposta 3 da questao 1', 'true', 'QUE-123456781');
insert into answers (id, description, correct, id_question) values ('ANS-123456784', 'Resposta 4 da questao 1', 'false', 'QUE-123456781');

insert into answers (id, description, correct, id_question) values ('ANS-123456785', 'Resposta 1 da questao 2', 'false', 'QUE-123456782');
insert into answers (id, description, correct, id_question) values ('ANS-123456786', 'Resposta 2 da questao 2', 'true', 'QUE-123456782');
insert into answers (id, description, correct, id_question) values ('ANS-123456787', 'Resposta 3 da questao 2', 'false', 'QUE-123456782');
insert into answers (id, description, correct, id_question) values ('ANS-123456788', 'Resposta 4 da questao 2', 'false', 'QUE-123456782');

insert into steps(id, title, description, type) values ('STE-123456781', 'Etapa 1', 'Descricao etapa 1', 'THEORETICAL_TEST');
insert into steps(id, title, description, type) values ('STE-123456782', 'Etapa 2', 'Descricao etapa 2', 'THEORETICAL_TEST');

insert into steps_theoretical_tests (id_step, id_question) values ('STE-123456781', 'QUE-123456781');
insert into steps_theoretical_tests (id_step, id_question) values ('STE-123456781', 'QUE-123456782');

insert into steps_theoretical_tests (id_step, id_question) values ('STE-123456782', 'QUE-123456783');
insert into steps_theoretical_tests (id_step, id_question) values ('STE-123456782', 'QUE-123456784');

insert into selection_processes(id, title, description, responsibilities, requirements, additional_infos, status, desired_position) values ('SEL-123456781', 'title', 'description', ARRAY ['responsibilities 1', 'responsibilities 2', 'responsibilities 3'], ARRAY ['requirements 1', 'requirements 2', 'requirements 3'], ARRAY ['additional Infos 1', 'additional Infos 2', 'additional Infos 3'], 'IN_PROGRESS', 'Desired Position');

insert into selection_processes_steps(id_selective_process, id_step, id_next_step, limit_time) values ('SEL-123456781', 'STE-123456781', 'STE-123456782', null);
insert into selection_processes_steps(id_selective_process, id_step, id_next_step, limit_time) values ('SEL-123456781', 'STE-123456782', null, null);

insert into candidates (id, name, cpf, email, phones, adresses, date_of_birth, username, password) values ('CAN-123456781', 'Diego Fernandez Oliveira', '27005990048', 'email_test@email.com.br', '{"ddd":"21","number":"947884078"}', '{"place":"Avenida Atlântica","number":570,"complement":"APT 304","neighborhood":"Copacabana","locality":"Rio de Janeiro","uf":"RJ","cep":"10856894"}', '2023-09-07', 'email_test@email.com.br', 'hhKJ6hui3%8u&2$jgJhjbJugj');

insert into applications (id, status, id_candidate, id_selective_process) values ('APP-123456781', 'IN_PROGRESS', 'CAN-123456781', 'SEL-123456781');

insert into applications_steps (id_application, id_step, id_next_step, status, limit_time, release_date) values ('APP-123456781', 'STE-123456781', 'STE-123456782', 'BLOCKED', null, null);
insert into applications_steps (id_application, id_step, id_next_step, status, limit_time, release_date) values ('APP-123456781', 'STE-123456782', null, 'BLOCKED', null, null);

INSERT INTO applications_steps_theoretical_tests(id_application, id_step, id_question, id_answer, type_question, discursive_answer) VALUES ('APP-123456781', 'STE-123456781', 'QUE-123456781', 'ANS-123456783', 'MULTIPLE_CHOICE', null);
INSERT INTO applications_steps_theoretical_tests(id_application, id_step, id_question, id_answer, type_question, discursive_answer) VALUES ('APP-123456781', 'STE-123456781', 'QUE-123456782', 'ANS-123456788', 'MULTIPLE_CHOICE', null);

INSERT INTO applications_steps_theoretical_tests(id_application, id_step, id_question, id_answer, type_question, discursive_answer) VALUES ('APP-123456781', 'STE-123456782', 'QUE-123456783', null, 'DISCURSIVE', 'Resposta da 1');
INSERT INTO applications_steps_theoretical_tests(id_application, id_step, id_question, id_answer, type_question, discursive_answer) VALUES ('APP-123456781', 'STE-123456782', 'QUE-123456784', null, 'DISCURSIVE', 'Resposta da 2');