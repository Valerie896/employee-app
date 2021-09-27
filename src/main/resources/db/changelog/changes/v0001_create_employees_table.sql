create table employees
(
    employee_id   bigserial    not null,
    first_name    varchar(200) not null,
    last_name     varchar(200) not null,
    department_id bigint       not null,
    job_title     varchar(200) not null,
    gender        varchar(200) not null,
    date_of_birth date         not null,

    primary key (employee_id)
)