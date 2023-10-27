insert into functions (id, name) values ('FUN-987654321', 'FUNC_CREATE_STEP');
insert into functions (id, name) values ('FUN-887654321', 'FUNC_CREATE_QUESTION');
insert into functions (id, name) values ('FUN-787654321', 'FUNC_CREATE_SELECTIVE_PROCESS');
insert into functions (id, name) values ('FUN-687654321', 'FUNC_CLOSE_SELECTIVE_PROCESS');
insert into functions (id, name) values ('FUN-587654321', 'FUNC_CREATE_FEEDBACK');
insert into functions (id, name) values ('FUN-487654321', 'FUNC_RELEASE_STEP');
insert into functions (id, name) values ('FUN-387654321', 'FUNC_CONSULT_STEP_ANSWERS');
insert into functions (id, name) values ('FUN-287654321', 'FUNC_REDEFINE_PASSWORD');
insert into functions (id, name) values ('FUN-187654321', 'FUNC_CREATE_CANDIDACY');
insert into functions (id, name) values ('FUN-977654321', 'FUNC_CONSULT_ALL_CANDIDACY');
insert into functions (id, name) values ('FUN-967654321', 'FUNC_CONSULT_CANDIDACY');
insert into functions (id, name) values ('FUN-957654321', 'FUNC_CLOSE_CANDIDACY');
insert into functions (id, name) values ('FUN-947654321', 'FUNC_EXEC_STEP');
insert into functions (id, name) values ('FUN-937654321', 'FUNC_CREATE_EMPLOYEE');
insert into functions (id, name) values ('FUN-927654321', 'FUNC_CREATE_PROFILE');

insert into profiles (id, name) values ('PRO-987654321', 'ADM_PROFILE');
insert into profiles (id, name) values ('PRO-887654321', 'EMPLOYEE_PROFILE');
insert into profiles (id, name) values ('PRO-787654321', 'CANDIDATE_PROFILE');

insert into profiles_functions (id_profile, id_function) values ('PRO-987654321', 'FUN-987654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-987654321', 'FUN-887654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-987654321', 'FUN-787654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-987654321', 'FUN-687654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-987654321', 'FUN-587654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-987654321', 'FUN-487654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-987654321', 'FUN-387654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-987654321', 'FUN-287654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-987654321', 'FUN-937654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-987654321', 'FUN-927654321');

insert into profiles_functions (id_profile, id_function) values ('PRO-887654321', 'FUN-987654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-887654321', 'FUN-887654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-887654321', 'FUN-787654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-887654321', 'FUN-687654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-887654321', 'FUN-587654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-887654321', 'FUN-487654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-887654321', 'FUN-387654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-887654321', 'FUN-287654321');

insert into profiles_functions (id_profile, id_function) values ('PRO-787654321', 'FUN-287654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-787654321', 'FUN-187654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-787654321', 'FUN-977654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-787654321', 'FUN-967654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-787654321', 'FUN-957654321');
insert into profiles_functions (id_profile, id_function) values ('PRO-787654321', 'FUN-947654321');