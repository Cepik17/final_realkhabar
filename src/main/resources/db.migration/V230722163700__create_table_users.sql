create table if not exists users
(
    id bigserial not null
        constraint pk_users_id primary key,
    full_name varchar(255),
    email varchar(255),
    password varchar(255),
    enabled boolean
)
