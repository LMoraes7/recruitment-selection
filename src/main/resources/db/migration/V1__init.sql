create table functions(
    id varchar(13) not null primary key,
    name varchar(255) not null unique
);

create table profiles(
    id varchar(13) not null primary key,
    name varchar(255) not null unique
);

create table profiles_functions(
    id_profile varchar(13) not null,
    id_function varchar(13) not null,
    constraint fk_profile_function foreign key (id_profile) references profiles (id),
    constraint fk_function_profile foreign key (id_function) references functions (id)
);

create table employees(
	id varchar(13) not null primary key,
	name varchar(255) not null,
	cpf varchar(11) not null unique,
	email varchar(255) not null unique,
	date_of_birth date not null,
	phones text not null,
	adresses text not null,
	username varchar(255) not null unique,
	password text not null
);

create table employees_profiles(
    id_employee varchar(13) not null,
    id_profile varchar(13) not null,
    constraint fk_employee_profile foreign key (id_employee) references employees (id),
    constraint fk_profile_employee foreign key (id_profile) references profiles (id)
);

create table candidates(
	id varchar(13) not null primary key,
    name varchar(255) not null,
    cpf varchar(11) not null unique,
    email varchar(255) not null unique,
    phones text not null,
    adresses text not null,
    date_of_birth date not null,
	username varchar(255) not null unique,
    password text not null
);

create table candidates_profiles(
    id_candidate varchar(13) not null,
    id_profile varchar(13) not null,
    constraint fk_candidate_profile foreign key (id_candidate) references candidates (id),
    constraint fk_profile_candidate foreign key (id_profile) references profiles (id)
);

create table selection_processes(
	id varchar(13) not null primary key,
	title varchar(255) not null,
	description text not null,
	responsibilities text ARRAY not null,
	requirements text ARRAY not null,
    additional_infos text ARRAY null,
    status varchar(100) not null,
	desired_position varchar(255) not null
);

create table selection_processes_employees(
	id_selective_process varchar(13) not null,
	id_employee varchar(13) not null,
	start_date date not null,
	final_date date null,
	constraint fk_selective_process_employee foreign key (id_selective_process) references selection_processes (id),
	constraint fk_employee_selective_process foreign key (id_employee) references employees (id)
);

create table steps(
    id varchar(13) not null primary key,
    title varchar(255) not null,
    description text not null,
    type varchar(100) not null
);

create table selection_processes_steps(
	id_selective_process varchar(13) not null,
	id_step varchar(13) not null,
	id_next_step varchar(13) null,
	limit_time bigint null,
	constraint fk_selective_process_step foreign key (id_selective_process) references selection_processes (id),
	constraint fk_step_selective_process foreign key (id_step) references steps (id),
	constraint fk_next_step_selective_process foreign key (id_next_step) references steps (id)
);

create table applications(
	id varchar(13) not null primary key,
	status varchar(100) not null,
	id_candidate varchar(13) not null,
	id_selective_process varchar(13) not null,
	constraint fk_candidate_application foreign key (id_candidate) references candidates (id),
	constraint fk_selective_process_application foreign key (id_selective_process) references selection_processes (id)
);

create table applications_steps(
    id_application varchar(13) not null,
    id_step varchar(13) not null,
    id_next_step varchar(13) null,
    status varchar(100) not null,
    limit_time bigint null, -- tempo expressado em dias
    release_date date null,
    constraint fk_candidatura_etapa foreign key (id_application) references applications (id),
    constraint fk_etapa_candidatura foreign key (id_step) references steps (id),
    constraint fk_proxima_etapa_candidatura foreign key (id_next_step) references steps (id)
);

create table feedback(
    id varchar(13) not null primary key,
    result varchar(100) not null,
    pointing decimal(3, 1) not null,
    additional_info text null,
    id_application varchar(13) not null,
    id_step varchar(13) not null,
    constraint fk_feedback_application foreign key (id_application) references applications (id),
    constraint fk_feedback_step foreign key (id_step) references steps (id)
);

create table questions(
    id varchar(13) not null primary key,
    description text not null,
    type varchar(100) not null
);

create table answers(
    id varchar(13) not null primary key,
    description text not null,
    correct boolean not null,
    id_question varchar(13) not null,
    constraint fk_question_answer foreign key (id_question) references questions (id)
);

create table steps_upload_files(
    id_step varchar(13) not null,
    description varchar(255) not null,
    type varchar(100) not null, -- Tipo do arquivo a ser enviado
    constraint fk_step_upload_file foreign key (id_step) references steps (id)
);

create table steps_theoretical_tests(
    id_step varchar(13) not null,
    id_question varchar(13) not null,
    constraint fk_step_question foreign key (id_step) references steps (id),
    constraint fk_question_step foreign key (id_question) references questions (id)
);

create table password_change_requests(
    code varchar(13) not null primary key,
    email_entity varchar(255) not null, -- email do funcionario ou do candidato
    type_entity varchar(13) not null, -- se o identificador_entidade pertence a um funcionario ou a um candidato
    requested_in timestamp not null, -- instante que foi solicitada a troca de senha
    expired_in timestamp not null, -- tempo limite para trocar password
    is_used boolean not null -- se o código já foi utilizado
);

create table registered_emails_documents(
    recorded_email varchar(255) not null primary key, -- colocar um index ao invés de chave primária
    recorded_document varchar(11) not null unique,
    type_entity varchar(13) not null
);

-- Essa tabela vai armazenar as respostas do candidato para um etapa de envio de arquivos
create table applications_steps_upload_files(
    id_application varchar(13) not null,
    id_step varchar(13) not null,
    file_name varchar(255) not null,
    file bytea not null,
    type varchar(100) not null,
    constraint fk_application_step_upload_file foreign key (id_application) references applications (id),
    constraint fk_step_upload_file_application foreign key (id_step) references steps (id)
);

-- Essa tabela vai o link e a data para uma etapa externa de um candidato
create table applications_steps_external(
    id_application varchar(13) not null,
    id_step varchar(13) not null,
    link text not null,
    date_and_time timestamp not null,
    constraint fk_application_step_external foreign key (id_application) references applications (id),
    constraint fk_step_external_application foreign key (id_step) references steps (id)
);

-- Essa tabela vai armazenar as respostas do candidato para um etapa de teste teórico
create table applications_steps_theoretical_tests(
    id_application varchar(13) not null,
    id_step varchar(13) not null,
    id_question varchar(13) not null,
    id_answer varchar(13) null,
    type_question varchar(100) not null,
    discursive_answer text null,
    constraint fk_application_step_theoretical_tests foreign key (id_application) references applications (id),
    constraint fk_step_theoretical_tests_application foreign key (id_step) references steps (id),
    constraint fk_application_step_theoretical_tests_question foreign key (id_question) references questions (id),
    constraint fk_step_theoretical_tests_application_answer foreign key (id_answer) references answers (id)
);