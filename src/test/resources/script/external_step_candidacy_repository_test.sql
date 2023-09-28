insert into steps(id, title, description, type) values ('STE-123456781', 'Etapa 1', 'Descricao etapa 1', 'EXTERNAL');

insert into selection_processes(id, title, description, responsibilities, requirements, additional_infos, status, desired_position) values ('SEL-123456781', 'title', 'description', ARRAY ['responsibilities 1', 'responsibilities 2', 'responsibilities 3'], ARRAY ['requirements 1', 'requirements 2', 'requirements 3'], ARRAY ['additional Infos 1', 'additional Infos 2', 'additional Infos 3'], 'IN_PROGRESS', 'Desired Position');

insert into selection_processes_steps(id_selective_process, id_step, id_next_step, limit_time) values ('SEL-123456781', 'STE-123456781', null, null);

insert into candidates (id, name, cpf, email, phones, adresses, date_of_birth, username, password) values ('CAN-123456781', 'Diego Fernandez Oliveira', '27005990048', 'email_test@email.com.br', '{"ddd":"21","number":"947884078"}', '{"place":"Avenida Atlântica","number":570,"complement":"APT 304","neighborhood":"Copacabana","locality":"Rio de Janeiro","uf":"RJ","cep":"10856894"}', '2023-09-07', 'email_test@email.com.br', 'hhKJ6hui3%8u&2$jgJhjbJugj');

insert into applications (id, status, id_candidate, id_selective_process) values ('APP-123456781', 'IN_PROGRESS', 'CAN-123456781', 'SEL-123456781');

insert into applications_steps (id_application, id_step, id_next_step, status, limit_time, release_date) values ('APP-123456781', 'STE-123456781', null, 'BLOCKED', null, null);

insert into applications_steps_external (id_application, id_step, link, date_and_time) values ('APP-123456781', 'STE-123456781', 'link', '2023-09-27T21:10:31.4797601');