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

create table user_authority
(
    id        bigserial    not null,
    user_id   bigint       not null,
    authority varchar(128) not null,
    primary key (id),
    foreign key (user_id) references users (id) on delete cascade,
    unique (user_id, authority)
)