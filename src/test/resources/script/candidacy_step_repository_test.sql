insert into steps(id, title, description, type) values ('STE-123456789', 'title', 'description', 'EXTERNAL');
insert into steps(id, title, description, type) values ('STE-987654321', 'title 2', 'description 2', 'UPLOAD_FILES');
insert into steps(id, title, description, type) values ('STE-564326567', 'title 3', 'description 3', 'THEORETICAL_TEST');
insert into steps(id, title, description, type) values ('STE-123456788', 'title 4', 'description', 'EXTERNAL');
insert into steps(id, title, description, type) values ('STE-987654324', 'title 5', 'description 2', 'UPLOAD_FILES');
insert into steps(id, title, description, type) values ('STE-564326562', 'title 6', 'description 3', 'THEORETICAL_TEST');
insert into steps(id, title, description, type) values ('STE-564326563', 'title 7', 'description 3', 'THEORETICAL_TEST');

insert into selection_processes(id, title, description, responsibilities, requirements, additional_infos, status, desired_position) values ('SEL-123456789', 'title', 'description', ARRAY ['responsibilities 1', 'responsibilities 2', 'responsibilities 3'], ARRAY ['requirements 1', 'requirements 2', 'requirements 3'], ARRAY ['additional Infos 1', 'additional Infos 2', 'additional Infos 3'], 'IN_PROGRESS', 'Desired Position');

insert into selection_processes_steps(id_selective_process, id_step, id_next_step, limit_time) values ('SEL-123456789', 'STE-123456789', null, null);
insert into selection_processes_steps(id_selective_process, id_step, id_next_step, limit_time) values ('SEL-123456789', 'STE-987654321', 'STE-564326562', 4564576);
insert into selection_processes_steps(id_selective_process, id_step, id_next_step, limit_time) values ('SEL-123456789', 'STE-564326567', 'STE-123456788', 7687);
insert into selection_processes_steps(id_selective_process, id_step, id_next_step, limit_time) values ('SEL-123456789', 'STE-123456788', 'STE-123456789', null);
insert into selection_processes_steps(id_selective_process, id_step, id_next_step, limit_time) values ('SEL-123456789', 'STE-987654324', 'STE-564326567', 65768);
insert into selection_processes_steps(id_selective_process, id_step, id_next_step, limit_time) values ('SEL-123456789', 'STE-564326562', 'STE-564326563', 798789);
insert into selection_processes_steps(id_selective_process, id_step, id_next_step, limit_time) values ('SEL-123456789', 'STE-564326563', 'STE-987654324', 7876576);

insert into candidates (id, name, cpf, email, phones, adresses, date_of_birth, username, password) values ('CAN-123456789', 'Diego Fernandez Oliveira', '27005990048', 'email_test@email.com.br', '{"ddd":"21","number":"947884078"}', '{"place":"Avenida Atlântica","number":570,"complement":"APT 304","neighborhood":"Copacabana","locality":"Rio de Janeiro","uf":"RJ","cep":"10856894"}', '2023-09-07', 'email_test@email.com.br', 'hhKJ6hui3%8u&2$jgJhjbJugj');

insert into applications(id, status, id_candidate, id_selective_process) values ('APP-123456789', 'IN_PROGRESS', 'CAN-123456789', 'SEL-123456789');

insert into applications_steps(id_application, id_step, id_next_step, status, limit_time, release_date) values ('APP-123456789','STE-123456789', 'STE-987654321', 'EXECUTED', null, null);
insert into applications_steps(id_application, id_step, id_next_step, status, limit_time, release_date) values ('APP-123456789','STE-987654321', 'STE-564326567', 'EXECUTED', null, null);
insert into applications_steps(id_application, id_step, id_next_step, status, limit_time, release_date) values ('APP-123456789','STE-564326567', null, 'EXECUTED', null, null);