insert into users(id, email)
values (1, 'maximkhvatovv@yandex.ru'),
       (2, 'bloch@gmail.com'),
       (3, 'gosling@sun.com');
SELECT setval('users_id_seq', 3, true);
insert into user_password(user_id, password)
values (1, '{noop}qwerty2001'),
       (2, '{noop}effectivejava'),
       (3, '{noop}justjava');
SELECT setval('users_id_seq', 3, true);

insert into authority(id, name)
values (1, 'ROLE_USER'),
       (2, 'ROLE_DB_USER');
SELECT setval('authority_id_seq', 2, true);

insert into user_authority(id, user_id, authority_id)
values (DEFAULT, 1, 1),
       (DEFAULT, 1, 2),
       (DEFAULT, 2, 1),
       (DEFAULT, 3, 2),
       (DEFAULT, 3, 1);