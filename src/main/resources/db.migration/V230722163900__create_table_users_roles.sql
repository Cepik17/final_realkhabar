create table if not exists users_roles
(
    user_id bigint not null,
    roles_id bigint not null,
    primary key (user_id, roles_id),
    foreign key (user_id) references users (id),
    foreign key (roles_id) references roles (id)
)