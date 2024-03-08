create table users
(
    id    bigserial    not null,
    email varchar(128) not null,
    primary key (id),
    unique (email),
    constraint account_email_check check (email ~ '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')
);
create table user_password
(
    user_id  bigint       not null,
    password varchar(128) not null,
    primary key (user_id),
    foreign key (user_id) references users (id) on delete cascade
);
create table authority
(
    id   serial not null,
    name varchar(128),
    primary key (id),
    unique (name)
);

create table user_authority
(
    id           bigserial not null,
    user_id      bigint    not null,
    authority_id int       not null,
    primary key (id),
    foreign key (user_id) references users (id) on delete cascade,
    foreign key (authority_id) references authority (id) on delete cascade,
    unique (user_id, authority_id)
)