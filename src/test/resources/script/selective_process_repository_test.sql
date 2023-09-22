insert into steps(id, title, description, type) values ('STE-123456789', 'title', 'description', 'EXTERNAL');
insert into steps(id, title, description, type) values ('STE-987654321', 'title 2', 'description 2', 'UPLOAD_FILES');
insert into steps(id, title, description, type) values ('STE-564326567', 'title 3', 'description 3', 'THEORETICAL_TEST');

insert into selection_processes(id, title, description, responsibilities, requirements, additional_infos, status, desired_position) values ('SEL-123456789', 'title', 'description', ARRAY ['responsibilities 1', 'responsibilities 2', 'responsibilities 3'], ARRAY ['requirements 1', 'requirements 2', 'requirements 3'], ARRAY ['additional Infos 1', 'additional Infos 2', 'additional Infos 3'], 'IN_PROGRESS', 'Desired Position');